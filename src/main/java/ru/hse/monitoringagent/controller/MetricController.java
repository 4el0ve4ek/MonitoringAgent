package ru.hse.monitoringagent.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.monitoringagent.model.Metric;
import ru.hse.monitoringagent.service.MetricService;
import ru.hse.monitoringagent.service.collector.MetricCollectors;
import ru.hse.monitoringagent.service.converter.PrometheusConverter;

import java.util.List;

@RestController()
@RequestMapping("/metrics")
public class MetricController {

    private final MetricService metricService;
    private final MetricCollectors metricCollectors;
    private final PrometheusConverter prometheusMarshaller;
    private final Logger logger = LoggerFactory.getLogger(MetricController.class);

    public MetricController(MetricService metricService, MetricCollectors metricCollectors, PrometheusConverter prometheusMarshaller) {
        this.metricService = metricService;
        this.metricCollectors = metricCollectors;
        this.prometheusMarshaller = prometheusMarshaller;
    }

    @GetMapping("/prometheus")
    public String getterPrometheusMetric(@RequestParam(required = false) boolean force) {
        List<Metric> metrics = force ? metricCollectors.collect() : metricService.getAll();

        return prometheusMarshaller.marshal(metrics);
    }
}
