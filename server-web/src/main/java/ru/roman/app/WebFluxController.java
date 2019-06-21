package ru.roman.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.atomic.LongAdder;

@RestController
public class WebFluxController {

    private DirectProcessor<String> calculateEventProcessor = DirectProcessor.create();
    private FluxSink<String> calculateEventSink = calculateEventProcessor.sink();
    private LongAdder sum = new LongAdder();

    @PostMapping(name = "/accumulate", consumes = "text/plain", produces = "text/plain")
    public Mono<String> accumulateValue(@RequestBody String value) {
        return Mono
                .just(Long.parseLong(value))
                .publishOn(Schedulers.single())
                .doOnNext(sum::add)                                      // possible race condition
                .flatMap(v -> Mono.from(calculateEventProcessor));
    }

    @GetMapping("/calculate")
    public Mono<String> calculateSum() {
        return Mono
                .fromCallable(() -> Long.valueOf(sum.sumThenReset()).toString())
                .doOnNext(t -> calculateEventSink.next(t));
    }
}
