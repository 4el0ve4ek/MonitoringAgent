package ru.hse.monitoringagent.model;

import java.util.List;

public class Config {
    private int schedulerRate;
    private List<String> metricHosts;

    public Config clone() {
        Config copiedConfig = new Config();

        copiedConfig.schedulerRate = this.schedulerRate;
        copiedConfig.metricHosts = List.copyOf(this.metricHosts);

        return copiedConfig;
    }
    public int getSchedulerRate() {
        return schedulerRate;
    }

    public List<String> getMetricHosts() {
        return metricHosts;
    }


    public Config cloneWithSchedulerRate(int schedulerRate) {
        Config clonnedConfig = this.clone();
        clonnedConfig.schedulerRate = schedulerRate;

        return clonnedConfig;
    }

    public Config cloneWithMetricHosts(List<String> metricHosts) {
        Config clonnedConfig = this.clone();
        clonnedConfig.metricHosts = metricHosts;

        return clonnedConfig;
    }
}
