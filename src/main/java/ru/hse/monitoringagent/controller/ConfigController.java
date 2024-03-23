package ru.hse.monitoringagent.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.monitoringagent.service.ConfigService;

@RestController
@RequestMapping("/config")
public class ConfigController {
    private final ConfigService configService;


    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @PutMapping("/metrics/url")
    public void addMetricURL(@RequestParam(name = "value") String url) {
        configService.addMetricURL(url);
    }

    @DeleteMapping("/metrics/url")
    public void deleteMetricURL(@RequestParam(name = "value") String url) {
        configService.removeMetricURL(url);
    }

    @PostMapping("/poll/rate")
    public ResponseEntity<?> setPollRate(@RequestParam(name = "value") int rate) {
        if (rate <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("rate must be positive number");
        }

        configService.updatePollMetricRate(rate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save/rate")
    public ResponseEntity<?> setSaveRate(@RequestParam(name = "value") int rate) {
        if (rate <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("rate must be positive number");
        }

        configService.updateSaveRate(rate);
        return ResponseEntity.ok().build();
    }

}
