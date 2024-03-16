package ru.hse.monitoringagent.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "monitoringagent")
public class Config {
    private int schedulerRate = 15000;
    private List<String> prometheus = List.of();


    public Config(int schedulerRate, List<String> prometheus) {
        this.schedulerRate = schedulerRate;

        if (prometheus == null) {
            this.prometheus = List.of();
        } else {
            this.prometheus = List.copyOf(prometheus);
        }
    }

    public int getSchedulerRate() {
        return schedulerRate;
    }

    public List<String> getPrometheusURLs() {
        return prometheus;
    }
}


