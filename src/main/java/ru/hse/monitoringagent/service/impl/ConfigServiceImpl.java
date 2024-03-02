package ru.hse.monitoringagent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.monitoringagent.model.Config;
import ru.hse.monitoringagent.repository.ConfigRepository;
import ru.hse.monitoringagent.service.ConfigGetter;
import ru.hse.monitoringagent.service.ConfigService;

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
        config.setSchedulerRate(newRate);
        configRepository.update(config);
    }

    @Override
    public void addMetricURL(String host) {
        var config = get();

        var metricHosts = config.getMetricURLs();
        metricHosts.add(host);

        config.setMetricURLs(metricHosts);
        configRepository.update(config);
    }

    @Override
    public void removeMetricURL(String host) {
        var config = get();

        var metricHosts = config.getMetricURLs();
        metricHosts.remove(host);

        config.setMetricURLs(metricHosts);
        configRepository.update(config);
    }
}
