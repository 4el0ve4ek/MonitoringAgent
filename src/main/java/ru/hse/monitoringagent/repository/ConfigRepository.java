package ru.hse.monitoringagent.repository;

import org.springframework.stereotype.Repository;
import ru.hse.monitoringagent.model.Config;

@Repository
public class ConfigRepository {

    private Config config;

    public void update(Config newConfig) {
        config = newConfig;
    }

    public Config get() {
        return config;
    }
}
