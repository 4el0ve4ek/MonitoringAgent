package ru.hse.monitoringagent.service.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.monitoringagent.model.Metric;
import ru.hse.monitoringagent.service.MetricService;

import java.util.Arrays;
import java.util.List;

@Service
public class MetricCollectors {
    @Autowired
    private MetricCollector[] metricCollectors;

    @Autowired
    private MetricService metricService;
    private final Logger logger = LoggerFactory.getLogger(MetricCollectors.class);

    public void poll() {
        logger.info("start polling");

        metricService.update(collect());
    }

    public List<Metric> collect() {
        return Arrays.stream(metricCollectors)
                .map(MetricCollector::collect)
                .flatMap(List::stream)
                .toList();
    }
}
