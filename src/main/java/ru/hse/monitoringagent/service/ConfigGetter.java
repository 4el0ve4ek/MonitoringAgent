package ru.hse.monitoringagent.service;

import org.springframework.stereotype.Service;
import ru.hse.monitoringagent.model.Config;

@Service
public interface ConfigGetter {
    public Config get();
}
