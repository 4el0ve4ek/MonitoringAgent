package ru.hse.monitoringagent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.monitoringagent.model.Config;
import ru.hse.monitoringagent.repository.ConfigRepository;
import ru.hse.monitoringagent.service.ConfigGetter;
import ru.hse.monitoringagent.service.ConfigService;

import java.util.ArrayList;

@Service
public class ConfigServiceImpl implements ConfigService, ConfigGetter {
    @Autowired
    private ConfigRepository configRepository;


    @Override
    public Config get() {
        return configRepository.get();
    }

    @Override
    public void updateMetricRate(int newRate) {
        var config = get();

        configRepository.update(new Config(newRate, config.getPrometheusURLs()));
    }

    @Override
    public void addMetricURL(String host) {
        var config = get();

        var metricHosts = new ArrayList<>(config.getPrometheusURLs());
        metricHosts.add(host);

        configRepository.update(new Config(config.getSchedulerRate(), metricHosts));
    }

    @Override
    public void removeMetricURL(String host) {
        var config = get();

        var metricHosts = new ArrayList<>(config.getPrometheusURLs());
        metricHosts.remove(host);

        configRepository.update(new Config(config.getSchedulerRate(), metricHosts));
    }
}
