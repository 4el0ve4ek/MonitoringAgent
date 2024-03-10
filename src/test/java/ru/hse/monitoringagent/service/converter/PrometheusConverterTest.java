package ru.hse.monitoringagent.service.converter;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import ru.hse.monitoringagent.model.Metric;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PrometheusConverterTest {

    @Test
    void marshal() {
        var testData = List.of(
                new Metric("go_goroutines", "", "", "", 1.1, Date.from(Instant.ofEpochSecond(10000)), Map.of(), 0L),
                new Metric("go_goroutines", "", "", "", 2.2, Date.from(Instant.ofEpochSecond(10000)), Map.of("inp", "\"tag\""), 0L),

                new Metric("go_goroutines", "localhost", "", "", 3.3, Date.from(Instant.ofEpochSecond(10000)), Map.of(), 0L),
                new Metric("go_goroutines", "", "description", "", 4.4, Date.from(Instant.ofEpochSecond(10000)), Map.of(), 0L),
                new Metric("go_goroutines", "", "", "type", 5.5, Date.from(Instant.ofEpochSecond(10000)), Map.of(), 0L)
        );

        var expectedLines =
                "go_goroutines 1.1\n" +
                "\n" +
                "go_goroutines{inp=\"tag\"} 2.2\n" +
                "\n" +
                "go_goroutines{ma_source=localhost} 3.3\n" +
                "\n" +
                "# HELP go_goroutines description\n" +
                "go_goroutines 4.4\n" +
                "\n" +
                "# TYPE go_goroutines type\n" +
                "go_goroutines 5.5\n" +
                "\n";

        var c = new PrometheusConverter();
        assertEquals(expectedLines, c.marshal(testData));
    }

    @Test
    void unmarshal() {
        var testData =
                "go_goroutines 1.1\n" +
                "go_goroutines{inp=\"tag\"} 2.2\n" +
                "go_goroutines{ma_source=localhost} 3.3\n" +
                "# HELP go_goroutines my_description\n" +
                "go_goroutines 4.4\n" +
                "# TYPE go_goroutines my_type\n" +
                "go_goroutines 5.5\n";

        var expectedMetrics = List.of(
                new Metric().setName("go_goroutines").setValue(1.1),
                new Metric().setName("go_goroutines").setValue(2.2).addLabel("inp", "\"tag\""),
                new Metric().setName("go_goroutines").setValue(3.3).addLabel("ma_source", "localhost"),
                new Metric().setName("go_goroutines").setValue(4.4).setDescription("my_description"),
                new Metric().setName("go_goroutines").setValue(5.5).setType("my_type")
        );
        var c = new PrometheusConverter();
        var gotMetrics = c.unmarshal(testData);

        assertEquals(expectedMetrics.size(), gotMetrics.size());
        for (int i = 0; i < expectedMetrics.size(); i++) {
            Metric got = gotMetrics.get(i);
            Metric expected = expectedMetrics.get(i);

            assertEquals(expected.name, got.name, i + "th name is broken");
            assertEquals(expected.value, got.value, 1e-6, i + "th value is broken");
            assertEquals(expected.description, got.description,  i + "th description is broken");
            assertEquals(expected.labels, got.labels, i + "th  labelsis broken");
        }
    }
}