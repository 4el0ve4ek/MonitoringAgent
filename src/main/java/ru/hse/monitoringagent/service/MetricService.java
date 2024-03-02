package ru.hse.monitoringagent.service;

import org.springframework.stereotype.Service;
import ru.hse.monitoringagent.model.Metric;

import java.util.List;

@Service
public interface MetricService {
    public List<Metric> getAll();

    void update(List<Metric> metrics);
}
