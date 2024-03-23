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
        return configRepository.getCopy();
    }

    @Override
    public void updatePollMetricRate(int newRate) {
        var config = get();
        config.setPollRate(newRate);
        configRepository.update(config);
    }

    public void updateSaveRate(int newSaveRate) {
        var config = get();
        config.setSaveRate(newSaveRate);
        configRepository.update(config);
    }

    @Override
    public void addMetricURL(String host) {
        var config = get();

        var metricHosts = new ArrayList<>(config.getPrometheusURLs());
        metricHosts.add(host);

        config.setPrometheusURLs(metricHosts);

        configRepository.update(config);
    }

    @Override
    public void removeMetricURL(String host) {
        var config = get();

        var metricHosts = new ArrayList<>(config.getPrometheusURLs());
        metricHosts.remove(host);

        config.setPrometheusURLs(metricHosts);

        configRepository.update(config);
    }
}
