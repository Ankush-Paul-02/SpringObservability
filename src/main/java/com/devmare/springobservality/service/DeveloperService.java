package com.devmare.springobservality.service;

import com.devmare.springobservality.entity.Developer;
import com.devmare.springobservality.repository.DeveloperRepository;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeveloperService {

    private final DeveloperRepository developerRepository;

    @Timed(value = "developer.save.method.timed", description = "Time taken to save developer")
    public void saveDeveloper(Developer developer) {
        developerRepository.save(developer);
    }

    @Timed(value = "developer.get.all.method.timed", description = "Time taken to get all developers")
    public List<Developer> getDevelopers() {
        return developerRepository.findAll();
    }

    @Timed(value = "developer.get.method.timed", description = "Time taken to get developer")
    public Developer getDeveloper(Long id) {
        return developerRepository.findById(id).orElse(null);
    }

    @Timed(value = "developer.delete.method.timed", description = "Time taken to delete developer")
    public void deleteDeveloper(Long id) {
        developerRepository.deleteById(id);
    }

    @Timed(value = "developer.update.method.timed", description = "Time taken to update developer")
    public Developer updateDeveloper(Developer developer, Long id) {
        Developer existingDeveloper = developerRepository.findById(id).orElse(null);
        if (existingDeveloper != null) {
            existingDeveloper.setName(developer.getName());
            existingDeveloper.setAge(developer.getAge());
            existingDeveloper.setSkill(developer.getSkill());
            return developerRepository.save(existingDeveloper);
        }
        return null;
    }
}
