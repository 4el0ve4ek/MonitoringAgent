package ru.hse.monitoringagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class MonitoringAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitoringAgentApplication.class, args);
    }

}
