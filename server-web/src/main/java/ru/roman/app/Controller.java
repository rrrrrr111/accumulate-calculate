package ru.roman.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API Controller
 */
@RestController
class Controller {
    private final static Logger log = LoggerFactory.getLogger(Controller.class);

    @PostMapping(path = "/accumulate", consumes = "text/plain", produces = "text/plain")
    public String accumulate(Long val) {
        log.trace("Accumulate call with {}", val);

        return String.valueOf(0L);
    }

    @GetMapping(path = "/calculate", produces = "text/plain")
    public String calculate() {
        log.trace("Calculate call ");

        return String.valueOf(0L);
    }
}
