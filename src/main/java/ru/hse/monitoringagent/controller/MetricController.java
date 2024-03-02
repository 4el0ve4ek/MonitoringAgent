package ru.hse.monitoringagent.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.hse.monitoringagent.service.MetricMarshaller;
import ru.hse.monitoringagent.service.MetricService;

import java.io.IOException;

@Controller
public class MetricController {

    @Autowired
    private MetricService metricService;
    @Autowired
    private MetricMarshaller prometheusMarshaller;

    private final Logger logger = LoggerFactory.getLogger(MetricController.class);

    @RequestMapping(method= RequestMethod.GET, path = "/metrics/prometheus")
    public String getterPrometheusMetric() {
        var metrics = metricService.getAll();

        try {
            return prometheusMarshaller.marshal(metrics);
        } catch (IOException e) {
            logger.error(e.toString());
            return null;
        }
    }
}
