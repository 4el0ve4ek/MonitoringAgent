package ru.hse.monitoringagent.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hse.monitoringagent.model.Metric;
import ru.hse.monitoringagent.service.MetricService;
import ru.hse.monitoringagent.service.MetricUnmarshaller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.function.Supplier;

public class MetricCollector {

    private final MetricService metricService;
    private final Supplier<List<String>> urlGetter;
    private final MetricUnmarshaller metricUnmarshaller;

    private final HttpClient client = HttpClient.newBuilder().build();

    private final Logger logger = LoggerFactory.getLogger(MetricCollector.class);

    public MetricCollector(MetricService metricService, Supplier<List<String>> urlGetter, MetricUnmarshaller metricUnmarshaller) {
        this.metricService = metricService;
        this.urlGetter = urlGetter;
        this.metricUnmarshaller = metricUnmarshaller;
    }

    //    @Scheduled(fixedRateString = "${metrics.scheduler.rate}") // FIXME: it would be cool to use it from runtime variable
    public void collectMetrics() {
        var metricsURLs = urlGetter.get();

        for (String sourceURL : metricsURLs) {
            String rawMetrics = "";
            try {
                rawMetrics = doRequest(sourceURL);
            } catch (Exception e) {
                logger.error(e.toString());
                continue;
            }

            List<Metric> metrics;
            try {
                metrics = metricUnmarshaller.unmarshal(rawMetrics);
            } catch (Exception e) {
                logger.error(e.toString());
                continue;
            }

            for (var metric : metrics) {
                metric.source = sourceURL;
            }

            metricService.update(metrics);
        }
    }

    private String doRequest(String url) throws Exception {
        var request = HttpRequest
                .newBuilder(URI.create(url))
                .GET()
                .build();


        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new Exception("non 200 status code returned, but " + response.statusCode());
        }


        return response.body();
    }

}
