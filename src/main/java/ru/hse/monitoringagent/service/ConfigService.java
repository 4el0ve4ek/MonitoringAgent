package ru.hse.monitoringagent.service;

import org.springframework.stereotype.Service;

@Service
public interface ConfigService extends ConfigGetter {
    void updatePollMetricRate(int newRate);

    void updateSaveRate(int newRate);

    void addMetricURL(String host);

    void removeMetricURL(String host);
}

