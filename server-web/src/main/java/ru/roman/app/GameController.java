package ru.roman.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API Controller
 */
@RestController
class GameController {

    @GetMapping("/accumulate")
    public void accumulate() {

    }

    @GetMapping("/calculate")
    public void calculate() {

    }
}
