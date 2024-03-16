package ru.hse.monitoringagent.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.monitoringagent.service.ConfigService;

import java.util.Arrays;

@RestController
@RequestMapping("/config")
public class ConfigController {
    private final ConfigService configService;


    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @PutMapping("/metrics/urls")
    public void addMetricURL(@RequestBody String[] urls) {
        Arrays.stream(urls).forEach(configService::addMetricURL);
    }

    @DeleteMapping("/metrics/urls")
    public void deleteMetricURL(@RequestBody String[] urls) {
        Arrays.stream(urls).forEach(configService::removeMetricURL);
    }

    @PostMapping("/rate")
    public ResponseEntity<?> setRate(@RequestParam(name = "value") int rate) {
        if (rate <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("rate must be positive number");
        }

        configService.updateMetricRate(rate);
        return ResponseEntity.ok().build();
    }

}
