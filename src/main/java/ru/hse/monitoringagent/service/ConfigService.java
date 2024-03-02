package ru.hse.monitoringagent.service;

import org.springframework.stereotype.Service;

@Service
public interface ConfigService {
    public void updateMetricRate(int newRate);
    public void addMetricURL(String host);
    public void removeMetricURL(String host);
}

