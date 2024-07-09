package com.devmare.springobservality.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/dev")
public class DevController {

    private final ObservationRegistry observationRegistry;
    private final SleepService sleepService;

    @GetMapping("/sleep")
    public Long sleep(
            @RequestParam Long ms
    ) {
        //        return this.doSleepTimer.record(() -> this.doSleep(ms));
        return sleepService.doSleep(ms);
    }

    @GetMapping
    public String getDev() {
        return Observation.createNotStarted(
                "dev",
                observationRegistry
        ).observe(() -> "Dev");
    }
}

@Service
class SleepService {

    @Timed(value = "do.sleep.method.timed")
    public Long doSleep(Long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ms;
    }
}

