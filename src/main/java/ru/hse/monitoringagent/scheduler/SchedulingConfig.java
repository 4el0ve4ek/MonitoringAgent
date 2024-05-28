package ru.hse.monitoringagent.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import ru.hse.monitoringagent.model.Config;
import ru.hse.monitoringagent.repository.SnapshotRepository;
import ru.hse.monitoringagent.service.ConfigGetter;
import ru.hse.monitoringagent.service.collector.MetricCollectors;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;

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
                new ConfigurablePeriodicTrigger(Config::getPollRate)

        );

        taskRegistrar.addTriggerTask(
                () -> snapshotRepository.saveDataByScheduler(),
                new ConfigurablePeriodicTrigger(Config::getSaveRate)
        );
    }

    private class ConfigurablePeriodicTrigger implements Trigger {

        private final Function<Config, Integer> configRateProvider;

        private ConfigurablePeriodicTrigger(Function<Config, Integer> configRateProvider) {
            this.configRateProvider = configRateProvider;
        }

        @Override
        public Instant nextExecution(TriggerContext triggerContext) {
            Optional<Instant> lastCompletionTime =
                    Optional.ofNullable(triggerContext.lastCompletion());

            return lastCompletionTime
                    .orElseGet(new Date()::toInstant)
                    .plusMillis(configRateProvider.apply(configService.get()));
        }
    }

}
