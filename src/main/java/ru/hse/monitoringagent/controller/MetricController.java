package ru.hse.monitoringagent.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Метрики", description = "Ручки для получения метрик хранимых внутри агента")
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
    @Operation(summary = "Получить метрики в формате prometheus", description = "Возвращает метрики в формате прометеус")
    public String getterPrometheusMetric(
            @RequestParam(required = false)
            @Parameter(description = "Нужно ли получить метриики напрямую из эндпоинтов")
            boolean force
    ) {
        List<Metric> metrics = force ? metricCollectors.collect() : metricService.getAll();

        return prometheusMarshaller.marshal(metrics);
    }
}
