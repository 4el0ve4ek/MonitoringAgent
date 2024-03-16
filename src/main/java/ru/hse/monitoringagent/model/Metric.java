package ru.hse.monitoringagent.model;


import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Entity
@ToString
public class Metric {

    @Id
    @Column(name = "id", nullable = false)
    private String generatedCompoundID;

    public String name;
    public String source;
    @ElementCollection
    public Map<String, String> labels;

    public String type;

    public double value;
    public String description;
    public Date lastUpdateTime;

    public Metric() {
        labels = new HashMap<>();
        description = "";
        type = "";
        source = "";
        name = "";
    }

    public Metric setName(String name) {
        this.name = name;
        return this;
    }

    public Metric setSource(String source) {
        this.source = source;
        return this;
    }

    public Metric setDescription(String description) {
        this.description = description;
        return this;
    }

    public Metric setType(String type) {
        this.type = type;
        return this;
    }

    public Metric setValue(double value) {
        this.value = value;
        return this;
    }

    public Metric addLabel(String key, String value) {
        this.labels.put(key, value);
        return this;
    }

    public void calcID() {
        String compoundLabels = labels.
                entrySet().
                stream().
                map(e -> e.getKey() + "," + e.getValue() + ";").
                collect(Collectors.joining());

        generatedCompoundID = name + "#" + source + "#" + compoundLabels;
    }
}
