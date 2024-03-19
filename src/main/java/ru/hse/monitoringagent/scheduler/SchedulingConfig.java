package ru.hse.monitoringagent.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.PeriodicTrigger;
import ru.hse.monitoringagent.repository.SnapshotRepository;
import ru.hse.monitoringagent.service.ConfigGetter;
import ru.hse.monitoringagent.service.collector.MetricCollectors;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {

    @Autowired
    private MetricCollectors metricCollectors;
    @Autowired
    private ConfigGetter configService;

    @Autowired
    private SnapshotRepository snapshotRepository;

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());

        taskRegistrar.addTriggerTask(
                () -> metricCollectors.poll(),
                context -> {
                    Optional<Instant> lastCompletionTime =
                            Optional.ofNullable(context.lastCompletion());

                    return lastCompletionTime
                            .orElseGet(new Date()::toInstant)
                            .plusMillis(configService.get().getSchedulerRate());
                }
        );

        taskRegistrar.addTriggerTask(
                () -> snapshotRepository.saveData(),
                new PeriodicTrigger(Duration.ofSeconds(10))
        );
    }

}
