package ru.hse.monitoringagent.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hse.monitoringagent.service.ConfigGetter;
import ru.hse.monitoringagent.service.MetricService;
import ru.hse.monitoringagent.service.converter.PrometheusConverter;

@Configuration
public class MetricCollectorConfig {
    private final MetricService metricService;
    private final ConfigGetter configGetter;
    private final PrometheusConverter prometheusConverter;

    public MetricCollectorConfig(MetricService metricService, ConfigGetter configGetter, PrometheusConverter prometheusConverter) {
        this.metricService = metricService;
        this.configGetter = configGetter;
        this.prometheusConverter = prometheusConverter;
    }

    @Bean
    public MetricCollector prometheusCollector() {
        return new MetricCollector(metricService, () -> configGetter.get().getPrometheusURLs(), prometheusConverter);
    }
}
