package ru.hse.monitoringagent.scheduler;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.hse.monitoringagent.service.ConfigGetter;
import ru.hse.monitoringagent.service.MetricService;
import ru.hse.monitoringagent.service.MetricUnmarshaller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class MetricCollector {

    @Autowired
    private MetricService metricService;

    @Autowired
    private ConfigGetter configGetter;

    @Autowired
    private MetricUnmarshaller metricUnmarshaller;

    private final HttpClient client = HttpClient.newBuilder().build();

    private final Logger logger = LoggerFactory.getLogger(MetricCollector.class);

    @SneakyThrows
    @Scheduled(fixedRateString = "${metrics.scheduler.rate}")
    public void collectMetrics() {
        var metricsURLs = configGetter.get().getMetricURLs();

        for(String sourceURL: metricsURLs) {
            var request = HttpRequest
                    .newBuilder(URI.create(sourceURL))
                    .GET()
                    .build();


            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                logger.error("non 200 status code request");
                return;
            }


            var metrics = metricUnmarshaller.unmarshal(response.body());
            for(var metric : metrics){
                metric.source = sourceURL;
            }

            metricService.update(metrics);
        }
    }


}
