package ru.hse.monitoringagent.service.converter;

import org.junit.jupiter.api.Test;
import ru.hse.monitoringagent.model.Metric;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrometheusConverterTest {

    @Test
    void marshal() {
        var testData = List.of(
                new Metric().setName("go_goroutines").setValue(1.1),
                new Metric().setName("go_goroutines").setValue(2.2).addLabel("inp", "\"tag\""),
                new Metric().setName("go_goroutines").setValue(3.3).setSource("localhost"),
                new Metric().setName("go_goroutines").setValue(4.4).setDescription("description"),
                new Metric().setName("go_goroutines").setValue(5.5).setType("type")
        );

        var expectedLines =
                """
                        go_goroutines 1.1
                        go_goroutines{inp="tag"} 2.2
                        go_goroutines{agent_source=localhost} 3.3
                        # HELP go_goroutines description
                        go_goroutines 4.4
                        # TYPE go_goroutines type
                        go_goroutines 5.5
                        """;

        var c = new PrometheusConverter();
        assertEquals(expectedLines, c.marshal(testData));
    }

    @Test
    void unmarshal() {
        var testData =
                """
                        go_goroutines 1.1
                        go_goroutines{inp="tag"} 2.2
                        go_goroutines{agent_source=localhost} 3.3
                        # HELP go_goroutines my_description
                        go_goroutines 4.4
                        # TYPE go_goroutines my_type
                        go_goroutines 5.5
                        """;

        var expectedMetrics = List.of(
                new Metric().setName("go_goroutines").setValue(1.1),
                new Metric().setName("go_goroutines").setValue(2.2).addLabel("inp", "\"tag\""),
                new Metric().setName("go_goroutines").setValue(3.3).setSource("localhost"),
                new Metric().setName("go_goroutines").setValue(4.4).setDescription("my_description"),
                new Metric().setName("go_goroutines").setValue(5.5).setType("my_type")
        );
        var c = new PrometheusConverter();
        var gotMetrics = c.unmarshal(testData);

        assertEquals(expectedMetrics.size(), gotMetrics.size());
        for (int i = 0; i < expectedMetrics.size(); i++) {
            Metric got = gotMetrics.get(i);
            Metric expected = expectedMetrics.get(i);

            assertEquals(expected.getName(), got.getName(), i + "th name is broken");
            assertEquals(expected.getValue(), got.getValue(), 1e-6, i + "th value is broken");
            assertEquals(expected.getDescription(), got.getDescription(), i + "th description is broken");
            assertEquals(expected.getLabels(), got.getLabels(), i + "th  labelsis broken");
        }
    }
}