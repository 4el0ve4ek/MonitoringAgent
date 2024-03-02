package ru.hse.monitoringagent.service;

import ru.hse.monitoringagent.model.Metric;

import java.util.List;

public interface MetricUnmarshaller {
    public List<Metric> unmarshal(String data) throws Exception;
}
