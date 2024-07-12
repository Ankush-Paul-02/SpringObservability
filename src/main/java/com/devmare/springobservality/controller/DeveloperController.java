package com.devmare.springobservality.controller;

import com.devmare.springobservality.entity.Developer;
import com.devmare.springobservality.service.DeveloperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/developer")
@RequiredArgsConstructor
public class DeveloperController {

    private final DeveloperService developerService;

    @PostMapping("/save")
    public ResponseEntity<String> saveDeveloper(
            @RequestBody Developer developer
    ) {
        developerService.saveDeveloper(developer);
        return ResponseEntity.ok(
                "Developer saved successfully"
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<Developer>> getDevelopers() {
        return ResponseEntity.ok(
                developerService.getDevelopers()
        );
    }
}
