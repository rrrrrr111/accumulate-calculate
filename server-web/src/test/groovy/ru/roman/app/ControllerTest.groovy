package ru.roman.app

import groovy.transform.CompileStatic
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

import static javax.ws.rs.core.MediaType.TEXT_PLAIN

@Test
@CompileStatic
class ControllerTest {
    private Client client = ClientBuilder.newClient()
    private ExecutorService executor = Executors.newCachedThreadPool()

    @BeforeClass
    def before() throws IOException {
        StartApp.main()
    }

    @Test
    void test() {

        accumulate(1, 6)
        accumulate(2, 6)
        accumulate(3, 6)

        sleep(1_000)

        def res = calculate()
        assert res == 6
    }

    private def accumulate(Long num, Long expected) {
        executor.submit {
            def res = client.target("http://127.0.0.1:8080/accumulate")
                    .request().post(Entity.entity(num.toString(), TEXT_PLAIN))
                    .readEntity(String.class)

            println "Accumulate result: $res"
            assert res == expected
        }
    }

    private Long calculate() {
        client.target("http://127.0.0.1:8080/calculate")
                .request().get()
                .readEntity(String.class) as Long
    }
}
