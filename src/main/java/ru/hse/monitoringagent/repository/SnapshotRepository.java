package ru.hse.monitoringagent.repository;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public class SnapshotRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(ConfigRepository.class);

    public void saveDataByScheduler() {
        logger.info("save data to file by scheduler");
        var now = Instant.now();

        try {
            jdbcTemplate.execute("SCRIPT TO 'lagging-data.sql'");

            var diff = Instant.now().toEpochMilli() - now.toEpochMilli();
            logger.info("saving data takes " + (double) (diff) / 1000. + "s");
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
    }

    @PreDestroy
    public void saveDataOnMetricSave() {
        logger.info("save data to file on metric save");
        var now = Instant.now();

        try {
            jdbcTemplate.execute("SCRIPT TO 'realtime-data.sql'");

            var diff = Instant.now().toEpochMilli() - now.toEpochMilli();
            logger.info("saving data takes " + (double) (diff) / 1000. + "s");
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
    }
}
