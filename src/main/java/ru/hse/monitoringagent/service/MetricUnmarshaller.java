package ru.hse.monitoringagent.service;

import org.springframework.stereotype.Service;
import ru.hse.monitoringagent.model.Metric;

import java.util.List;

public interface MetricUnmarshaller {
    public List<Metric> unmarshalJSON(byte[] data) throws Exception;
}
