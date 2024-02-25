package ru.hse.monitoringagent.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import ru.hse.monitoringagent.model.Metric;
import ru.hse.monitoringagent.service.converter.PrometheusConverter;

import java.io.IOException;
import java.util.List;

public interface MetricMarshaller {
    public byte[] marshalJSON(List<Metric> metrics) throws IOException;
}
