package ru.hse.monitoringagent.repository;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SnapshotRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(ConfigRepository.class);

    @PreDestroy
    public void saveData() {
        logger.info("save data to file");
        
        try {
            jdbcTemplate.execute("SCRIPT TO 'dump.sql'");
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
    }
}
