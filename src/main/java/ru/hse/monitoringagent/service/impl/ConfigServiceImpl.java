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
        var oldConfig = get();
        var newConfig = oldConfig.cloneWithSchedulerRate(newRate);
        configRepository.update(newConfig);
    }

    @Override
    public void addMetricHost(String host) {
        var oldConfig = get();

        var metricHosts = oldConfig.getMetricHosts();
        metricHosts.add(host);

        var newConfig = oldConfig.cloneWithMetricHosts(metricHosts);
        configRepository.update(newConfig);
    }

    @Override
    public void removeMetricHost(String host) {
        var oldConfig = get();

        var metricHosts = oldConfig.getMetricHosts();
        metricHosts.remove(host);

        var newConfig = oldConfig.cloneWithMetricHosts(metricHosts);
        configRepository.update(newConfig);
    }
}
