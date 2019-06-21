package ru.roman.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;

/**
 * REST API Controller
 */
//@RestController
class SimpleController {
    private final static Logger log = LoggerFactory.getLogger(SimpleController.class);

    private final SynchronousQueue<ExchangeData> queue = new SynchronousQueue<>(true);

    @PostMapping(path = "/accumulate", consumes = "text/plain", produces = "text/plain")
    public String accumulate(@RequestBody String val) throws InterruptedException {
        log.trace("Accumulate call with {}", val);

        ExchangeData data = new ExchangeData(Long.valueOf(val));
        queue.put(data);

        return data.getResponse();
    }

    @GetMapping(path = "/calculate", produces = "text/plain")
    public String calculate() {

        List<ExchangeData> list = new ArrayList<>();
        queue.drainTo(list);

        String result = String.valueOf(
                list.stream()
                        .mapToLong(ExchangeData::getRequest)
                        .sum()                             // possible Long overflow!!
        );

        list.forEach(d -> d.setResponse(result));

        log.trace("Calculate call result {}", result);
        return result;
    }

    private static class ExchangeData {
        private final Long request;
        private final CountDownLatch latch = new CountDownLatch(1);
        private String response;

        private ExchangeData(Long request) {
            this.request = request;
        }

        Long getRequest() {
            return request;
        }

        String getResponse() throws InterruptedException {
            latch.await();
            return response;
        }

        void setResponse(String response) {
            this.response = response;
            latch.countDown();
        }
    }
}
