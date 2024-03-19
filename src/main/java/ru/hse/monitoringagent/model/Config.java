package ru.hse.monitoringagent.model;

import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "monitoringagent")
@ToString
public class Config {
    private int schedulerRate = 15000;
    private List<String> prometheusURLs = List.of();

    public Config() {
        super();
    }

    public Config(int schedulerRate, List<String> prometheus) {
        this.schedulerRate = schedulerRate;

        if (prometheus == null) {
            this.prometheusURLs = List.of();
        } else {
            this.prometheusURLs = List.copyOf(prometheus);
        }
    }

    public int getSchedulerRate() {
        return schedulerRate;
    }

    public List<String> getPrometheusURLs() {
        return prometheusURLs;
    }

    public void setSchedulerRate(int schedulerRate) {
        this.schedulerRate = schedulerRate;
    }

    public void setPrometheusURLs(List<String> prometheusURLs) {
        this.prometheusURLs = prometheusURLs;
    }
}


