package ru.hse.monitoringagent.model;

import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "monitoringagent")
@ToString
public class Config {
    private int pollRate = 15000;
    private int saveRate = 10000;
    private List<String> prometheusURLs = List.of();

    public Config() {
        super();
    }

    public Config(Config config) {
        this.pollRate = config.pollRate;
        this.saveRate = config.saveRate;
        this.prometheusURLs = config.getPrometheusURLs() == null ? List.of() : List.copyOf(config.getPrometheusURLs());
    }

    public int getPollRate() {
        return pollRate;
    }

    public int getSaveRate() {
        return saveRate;
    }

    public List<String> getPrometheusURLs() {
        return prometheusURLs;
    }


    public void setSaveRate(int saveRate) {
        this.saveRate = saveRate;
    }

    public void setPollRate(int pollRate) {
        this.pollRate = pollRate;
    }

    public void setPrometheusURLs(List<String> prometheusURLs) {
        this.prometheusURLs = List.copyOf(prometheusURLs);
    }
}


