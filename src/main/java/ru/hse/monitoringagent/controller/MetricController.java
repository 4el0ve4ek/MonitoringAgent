package ru.hse.monitoringagent.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.monitoringagent.service.MetricService;
import ru.hse.monitoringagent.service.converter.PrometheusConverter;

@RestController()
@RequestMapping("/metrics")
public class MetricController {

    private final MetricService metricService;
    private final PrometheusConverter prometheusMarshaller;
    private final Logger logger = LoggerFactory.getLogger(MetricController.class);

    public MetricController(MetricService metricService, PrometheusConverter prometheusMarshaller) {
        this.metricService = metricService;
        this.prometheusMarshaller = prometheusMarshaller;
    }

    @GetMapping("/prometheus")
    public String getterPrometheusMetric() {
        var metrics = metricService.getAll();

        return prometheusMarshaller.marshal(metrics);
    }
}
