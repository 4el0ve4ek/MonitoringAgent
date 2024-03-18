package ru.hse.monitoringagent.model;


import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Entity
@ToString
public class Metric implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String generatedCompoundID;

    private String name;
    private String source;
    @ElementCollection
    private Map<String, String> labels;

    private String type;

    private double value;
    private String description;
    private Date lastUpdateTime;

    public Metric() {
        labels = new HashMap<>();
        description = "";
        type = "";
        source = "";
        name = "";
    }


    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public String getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public String getDescription() {
        return description;
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

    public Metric setLabels(Map<String, String> labels) {
        this.labels = labels;
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
