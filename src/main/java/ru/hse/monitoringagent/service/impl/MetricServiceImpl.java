package ru.hse.monitoringagent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.hse.monitoringagent.model.Metric;
import ru.hse.monitoringagent.repository.MetricRepository;
import ru.hse.monitoringagent.service.MetricService;

import java.util.List;

@Service
public class MetricServiceImpl implements MetricService {

    @Autowired
    private MetricRepository metricRepository;

    @Override
    public List<Metric> getAll() {
        return metricRepository.findAll();
    }

    @Override
    public void update(List<Metric> metrics) {
        for(var metric: metrics) {
            metricRepository.removeMetricByNameAndSource(metric.name, metric.source);
            metricRepository.save(metric);
        }
    }
}
