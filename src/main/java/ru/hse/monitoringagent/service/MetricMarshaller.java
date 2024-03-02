package ru.hse.monitoringagent.service;

import ru.hse.monitoringagent.model.Metric;

import java.io.IOException;
import java.util.List;

public interface MetricMarshaller {
    public String marshal(List<Metric> metrics) throws IOException;
}
