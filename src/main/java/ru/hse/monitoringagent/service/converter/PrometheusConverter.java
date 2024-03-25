package ru.hse.monitoringagent.service.converter;

import org.springframework.stereotype.Component;
import ru.hse.monitoringagent.model.Metric;
import ru.hse.monitoringagent.service.MetricMarshaller;
import ru.hse.monitoringagent.service.MetricUnmarshaller;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class PrometheusConverter implements MetricMarshaller, MetricUnmarshaller {

    private static final String helpPrefix = "# HELP ";
    private static final String typePrefix = "# TYPE ";
    private static final String labelSourcePrefix = "agent_source";

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

        buf.append("# EOF");

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
                var labelName = labelSourcePrefix;
                // to prevent overriding
                if (labels.containsKey(labelName)) {
                    labelName = labelName + "_" + new Random().nextLong();
                }

                labels.put(labelName, metric.getSource());
            }
            buf.append("{");

            var labelsMarshalled = labels.entrySet()
                    .stream()
                    .map(entry ->
                            entry.getKey()
                                    + "=\""
                                    + entry.getValue()
                                    + "\""
                    )
                    .collect(Collectors.joining(","));

            buf.append(labelsMarshalled);
            buf.append("}");
        }
        buf.append(" ");
        buf.append(trimSuffix(Double.toString(metric.getValue()).toLowerCase(), ".0"));

        var collectTime = metric.getCollectTime();
        if (collectTime > 0) {
            buf.append(" ");
            buf.append(collectTime);
        }

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
        double value = robustParseDouble(parts[1]);

        long collectTimestamp = 0;
        if (parts.length > 2) {
            collectTimestamp = robustParseLong(parts[2]);
        }

        Metric metric = new Metric()
                .setName(metricName)
                .setValue(value)
                .setDescription(trimPrefix(description, metricName + " "))
                .setType(trimPrefix(type, metricName + " "))
                .setLabels(labels)
                .setCollectTime(collectTimestamp);

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
            labels.put(pairValues[0], trim(pairValues[1], "\""));
        }

        return labels;
    }

    private double robustParseDouble(String rawValue) {
        try {
            return Double.parseDouble(rawValue);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private long robustParseLong(String rawValue) {
        try {
            return Long.parseLong(rawValue);
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

    private String trimSuffix(String origin, String prefix) {
        if (origin.endsWith(prefix)) {
            return origin.substring(0, origin.length() - prefix.length());
        }

        return origin;
    }

    private String trim(String origin, String pattern) {
        return trimSuffix(trimPrefix(origin, pattern), pattern);
    }
}
