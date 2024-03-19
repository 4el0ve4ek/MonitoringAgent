package ru.hse.monitoringagent.service.collector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hse.monitoringagent.service.ConfigGetter;
import ru.hse.monitoringagent.service.converter.PrometheusConverter;

@Configuration
public class MetricCollectorConfig {
    private final ConfigGetter configGetter;
    private final PrometheusConverter prometheusConverter;

    public MetricCollectorConfig(ConfigGetter configGetter, PrometheusConverter prometheusConverter) {
        this.configGetter = configGetter;
        this.prometheusConverter = prometheusConverter;
    }

    @Bean
    public MetricCollector prometheusCollector() {
        return new MetricCollector(() -> configGetter.get().getPrometheusURLs(), prometheusConverter);
    }
}
