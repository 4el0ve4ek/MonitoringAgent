package ru.hse.monitoringagent.service;

import org.springframework.stereotype.Service;

@Service
public interface ConfigService {
    void updateMetricRate(int newRate);
    void addMetricURL(String host);
    void removeMetricURL(String host);
}

