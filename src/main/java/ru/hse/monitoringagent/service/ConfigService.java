package ru.hse.monitoringagent.service;

import org.springframework.stereotype.Service;
import ru.hse.monitoringagent.model.Config;
import ru.hse.monitoringagent.model.Metric;

import java.util.List;

@Service
public interface ConfigService {
    public void updateMetricRate(int newRate);
    public void addMetricHost(String host);
    public void removeMetricHost(String host);
}

