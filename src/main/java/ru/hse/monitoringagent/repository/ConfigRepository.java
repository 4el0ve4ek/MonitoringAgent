package ru.hse.monitoringagent.repository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TagInspector;
import ru.hse.monitoringagent.model.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
@EnableConfigurationProperties({Config.class})
public class ConfigRepository {
    private final static String persistentFile = "./save.yaml";

    private final Logger logger = LoggerFactory.getLogger(ConfigRepository.class);
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private Config config;

    public ConfigRepository(Config defaultConfig) {
        config = new Config(defaultConfig.getSchedulerRate(), defaultConfig.getPrometheusURLs());

    }

    @PostConstruct
    private void init() {
        var file = new File(persistentFile);
        var loaderoptions = new LoaderOptions();
        TagInspector taginspector =
                tag -> tag.getClassName().equals(Config.class.getName());
        loaderoptions.setTagInspector(taginspector);
        Yaml yaml = new Yaml(new Constructor(Config.class, loaderoptions));

        lock.writeLock().lock();
        if (file.exists()) {
            try (var fis = new FileInputStream(file)) {
                config = yaml.loadAs(fis, Config.class);
            } catch (IOException e) {
                logger.error(e.toString());
            }
        }
        lock.writeLock().unlock();

        logger.info(config.toString());
    }


    @PreDestroy
    private void destroy() {
        var file = new File(persistentFile);
        var yaml = new Yaml();

        try (var fw = new FileWriter(file)) {
            yaml.dump(config, fw);
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
