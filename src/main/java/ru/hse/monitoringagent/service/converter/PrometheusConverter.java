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

        var metricsStream = metrics.stream().sorted(Comparator
                .comparing(Metric::getSource)
                .thenComparing(Metric::getName)
        );
        for (var metric : metricsStream.toList()) {
            appendMetric(buf, metric);
        }

        return buf.toString();
    }

    private void appendMetric(StringBuffer buf, Metric metric) {
        if (!metric.getDescription().isEmpty()) {
            buf.append(helpPrefix);
            buf.append(metric.getName());
            buf.append(" ");
            buf.append(metric.getDescription());
            buf.append("\n");
        }

        // tmp unused
        if (!metric.getType().isEmpty()) {
            buf.append(typePrefix);
            buf.append(metric.getName());
            buf.append(" ");
            buf.append(metric.getType());
            buf.append("\n");
        }

        buf.append(metric.getName());
        if (!metric.getLabels().isEmpty() || !metric.getSource().isEmpty()) {
            var labels = new TreeMap<>(Map.copyOf(metric.getLabels()));
            if (!metric.getSource().isEmpty()) {
                labels.put("agent_source", metric.getSource());
            }
            buf.append("{");
            for (var entry : labels.entrySet()) {
                buf.append(entry.getKey());
                buf.append("=");
                buf.append(entry.getValue());
                buf.append(",");
            }
            buf.append("}");
        }
        buf.append(" ");
        buf.append(metric.getValue());
        buf.append("\n");
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

        Metric metric = new Metric()
                .setName(metricName)
                .setValue(value)
                .setDescription(trimPrefix(description, metricName + " "))
                .setType(trimPrefix(type, metricName + " "))
                .setLabels(labels);

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
