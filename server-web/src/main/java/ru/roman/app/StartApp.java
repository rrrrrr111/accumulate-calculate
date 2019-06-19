package ru.roman.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Web Server starting point
 */
@SpringBootApplication(scanBasePackageClasses = {StartApp.class})
public class StartApp {
    private static final Logger log = LoggerFactory.getLogger(StartApp.class);

    public static void main(String[] args) {
        SpringApplication.run(StartApp.class, args);
    }

    @Bean
    public CommandLineRunner loggingCommandLineRunner(ApplicationContext ctx) {
        return args -> logBaseUrl();
    }

    private void logBaseUrl() {
        log.trace("Tomcat started at http://127.0.0.1:8080");
    }
}
