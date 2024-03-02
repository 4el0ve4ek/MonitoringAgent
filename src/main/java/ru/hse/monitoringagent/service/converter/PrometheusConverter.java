package ru.hse.monitoringagent.service.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.hse.monitoringagent.model.Metric;
import ru.hse.monitoringagent.service.MetricMarshaller;
import ru.hse.monitoringagent.service.MetricUnmarshaller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Component
public class PrometheusConverter implements MetricMarshaller, MetricUnmarshaller {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String marshal(List<Metric> metrics) throws IOException {
        // TODO:https://prometheus.io/docs/instrumenting/exposition_formats/
        return ""; // objectMapper.writeValueAsString(metrics);
    }

    @Override
    public List<Metric> unmarshal(String data) throws IOException {
        // TODO:https://prometheus.io/docs/instrumenting/exposition_formats/
        return Arrays.asList(objectMapper.readValue(data, Metric[].class));
    }
}
