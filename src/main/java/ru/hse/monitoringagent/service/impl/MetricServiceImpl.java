package ru.hse.monitoringagent.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.monitoringagent.model.Metric;
import ru.hse.monitoringagent.repository.MetricRepository;
import ru.hse.monitoringagent.service.MetricService;

import java.util.List;

@Service
public class MetricServiceImpl implements MetricService {

    private final Logger logger = LoggerFactory.getLogger(MetricServiceImpl.class);

    @Autowired
    private MetricRepository metricRepository;

    @Override
    public List<Metric> getAll() {
        return metricRepository.findAll();
    }

    @Override
    public void update(List<Metric> metrics) {
        for (var metric : metrics) {
            metric.calcID();

            logger.info("update metric " + metric.name + " " + metric.source);

            metricRepository.save(metric);
        }
    }
}
