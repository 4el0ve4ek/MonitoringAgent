package ru.hse.monitoringagent.service.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.hse.monitoringagent.model.Metric;
import ru.hse.monitoringagent.service.MetricMarshaller;
import ru.hse.monitoringagent.service.MetricUnmarshaller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class PrometheusConverter implements MetricMarshaller, MetricUnmarshaller {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] marshalJSON(List<Metric> metrics) throws IOException {
        return objectMapper.writeValueAsBytes(metrics);
    }

    @Override
    public List<Metric> unmarshalJSON(byte[] data) throws IOException {
        return Arrays.asList(objectMapper.readValue(data, Metric[].class));
    }
}
