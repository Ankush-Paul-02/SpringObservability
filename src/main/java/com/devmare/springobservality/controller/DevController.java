package com.devmare.springobservality.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DevController {

    private final ObservationRegistry observationRegistry;
    private final SleepService sleepService;

    @GetMapping("/exception")
    public String exception() {
        throw new IllegalArgumentException("This id is invalid");
    }

    @GetMapping("/sleep")
    public Long sleep(@RequestParam Long ms) {
        log.info("Received sleep request for {} milliseconds", ms);
        Long result = this.sleepService.doSleep(ms);
        log.info("Completed sleep for {} milliseconds", result);
        return result;
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<String> handleConflict(IllegalArgumentException ex) {
        log.error("Exception occurred: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("Unexpected exception occurred: {}", ex.getMessage());
        return ResponseEntity.status(500).body("Internal server error");
    }
}

@Service
class SleepService {
    @Timed(value = "do.sleep.method.timed", description = "Time taken to execute sleep method")
    public Long doSleep(Long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ms;
    }
}
