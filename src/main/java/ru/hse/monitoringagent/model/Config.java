package ru.hse.monitoringagent.model;

import java.util.List;

public class Config {
    private int schedulerRate;
    private List<String> metricURLs;

    public Config clone() {
        Config copiedConfig = new Config();

        copiedConfig.schedulerRate = this.schedulerRate;
        copiedConfig.metricURLs = List.copyOf(this.metricURLs);

        return copiedConfig;
    }
    public int getSchedulerRate() {
        return schedulerRate;
    }

    public void setSchedulerRate(int newRate) {
        schedulerRate = newRate;
    }

    public List<String> getMetricURLs() {
        return metricURLs;
    }

    public void setMetricURLs(List<String> newMetricURLs) {
        metricURLs = newMetricURLs;
    }
}
