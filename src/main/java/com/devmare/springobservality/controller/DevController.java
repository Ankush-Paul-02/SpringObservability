package com.devmare.springobservality.controller;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dev")
public class DevController {

    private final ObservationRegistry observationRegistry;

    @GetMapping
    public String getDev() {
        return Observation.createNotStarted(
                "dev",
                observationRegistry
        ).observe(() -> "Dev");
    }
}
