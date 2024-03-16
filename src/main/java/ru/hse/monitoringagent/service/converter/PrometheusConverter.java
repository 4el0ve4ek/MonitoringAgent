package ru.hse.monitoringagent.service.converter;

import org.springframework.stereotype.Component;
import ru.hse.monitoringagent.model.Metric;
import ru.hse.monitoringagent.service.MetricMarshaller;
import ru.hse.monitoringagent.service.MetricUnmarshaller;

import java.util.*;


@Component
public class PrometheusConverter implements MetricMarshaller, MetricUnmarshaller {

    private final String helpPrefix = "# HELP ";
    private final String typePrefix = "# TYPE ";

    @Override
    public String marshal(List<Metric> metrics) {
        var buf = new StringBuffer();
        for (var metric : metrics) {
            appendMetric(buf, metric);
        }

        return buf.toString();
    }

    private void appendMetric(StringBuffer buf, Metric metric) {
        if (!metric.description.isEmpty()) {
            buf.append(helpPrefix);
            buf.append(metric.name);
            buf.append(" ");
            buf.append(metric.description);
            buf.append("\n");
        }

        // tmp unused
        if (!metric.type.isEmpty()) {
            buf.append(typePrefix);
            buf.append(metric.name);
            buf.append(" ");
            buf.append(metric.type);
            buf.append("\n");
        }

        buf.append(metric.name);
        if (!metric.labels.isEmpty() || !metric.source.isEmpty()) {
            var labels = new TreeMap<>(Map.copyOf(metric.labels));
            if (!metric.source.isEmpty()) {
                labels.put("ma_source", metric.source);
            }
            buf.append("{");
            for (var entry : labels.entrySet()) {
                buf.append(entry.getKey());
                buf.append("=");
                buf.append(entry.getValue());
            }
            buf.append("}");
        }
        buf.append(" ");
        buf.append(metric.value);
        buf.append("\n\n");
    }

    @Override
    public List<Metric> unmarshal(String data) {
        StringTokenizer linesTokens = new StringTokenizer(data, "\n");

        List<Metric> res = new ArrayList<>();
        while (linesTokens.hasMoreTokens()) {
            String line = linesTokens.nextToken().trim();
            ArrayList<String> rawMetric = new ArrayList<>(4);
            while (isSpecialLine(line)) {
                if (!linesTokens.hasMoreTokens()) {
                    break;
                }
                rawMetric.add(line);
                line = linesTokens.nextToken();
            }
            rawMetric.add(line);

            parseMetric(rawMetric).ifPresent(res::add);
        }

        return res;
    }


    private boolean isSpecialLine(String line) {
        return line.startsWith(helpPrefix) || line.startsWith(typePrefix);
    }

    private Optional<Metric> parseMetric(List<String> lines) {
        String description = findSpecialByPrefix(lines, helpPrefix);
        String type = findSpecialByPrefix(lines, typePrefix);

        lines = lines.stream().filter(val -> !val.startsWith("#")).toList();
        if (lines.isEmpty()) {
            return Optional.empty();
        }
        String line = lines.get(0);
        int labelStart = line.indexOf("{");
        int labelEnd = line.lastIndexOf("}");
        Map<String, String> labels = parseLabels(line, labelStart, labelEnd);

        if (labelStart != -1) {
            line = line.substring(0, labelStart) + line.substring(labelEnd + 1);
        }

        var parts = line.split(" ", 3);
        String metricName = parts[0];
        float value = parseValue(parts[1]);
//        String amendTimestamp = parts[2];

        Metric metric = new Metric();
        metric.name = metricName;
        metric.value = value;
        metric.description = trimPrefix(description, metricName + " ");
        metric.type = trimPrefix(type, metricName + " ");
        metric.labels = labels;

        return Optional.of(metric);
    }

    private Map<String, String> parseLabels(String raw, int start, int end) {
        if (start == -1 || end == -1 || start > end) {
            return Map.of();
        }

        HashMap<String, String> labels = new HashMap<>();
        StringTokenizer labelPairs = new StringTokenizer(raw.substring(start + 1, end), ",");
        while (labelPairs.hasMoreTokens()) {
            String pair = labelPairs.nextToken();
            String[] pairValues = pair.split("=", 2);
            labels.put(pairValues[0], pairValues[1]);
        }

        return labels;
    }

    private float parseValue(String rawValue) {
        try {
            return Float.parseFloat(rawValue);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private String findSpecialByPrefix(List<String> lines, String prefix) {
        for (var line : lines) {
            if (line.startsWith(prefix)) {
                return line.substring(prefix.length());
            }
        }

        return "";
    }

    private String trimPrefix(String origin, String prefix) {
        if (origin.startsWith(prefix)) {
            return origin.substring(prefix.length());
        }

        return origin;
    }
}
