package ru.hse.monitoringagent.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Repository;
import ru.hse.monitoringagent.model.Config;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
@EnableConfigurationProperties({Config.class})
public class ConfigRepository {
    private final static String fileToSave = "./save.yaml";

    private final Logger logger = LoggerFactory.getLogger(ConfigRepository.class);
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private Config config;

    public ConfigRepository(Config defaultConfig) {
        config = new Config(defaultConfig.getSchedulerRate(), defaultConfig.getPrometheusURLs());

    }

    @PostConstruct
    private void init() {
        var file = new File(fileToSave);
        var mapper = new ObjectMapper(new YAMLFactory());

        if (file.exists()) {
            try {
                config = mapper.readValue(file, Config.class);
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }

    }

    @PreDestroy
    private void destroy() {
        var mapper = new ObjectMapper(new YAMLFactory());
        var file = new File(fileToSave);

        try {
            mapper.writeValue(file, config);
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }


    public synchronized void update(Config newConfig) {
        lock.writeLock().lock();
        try {
            config = newConfig;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public synchronized Config get() {
        lock.readLock().lock();
        try {
            return config;
        } finally {
            lock.readLock().unlock();
        }
    }
}
