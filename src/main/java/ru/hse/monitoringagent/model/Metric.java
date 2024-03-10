package ru.hse.monitoringagent.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Entity
@ToString
public class Metric {
    public String name;
    public String source;
    public String description;
    public String type;
    public double value;

    public Date lastUpdateTime;

    @Transient
    public Map<String, String> labels;

    @Id
    public   Long id;

    public Metric setName(String name) {
        this.name = name;
        return this;
    }

    public Metric() {
        labels = new HashMap<>();
        description = "";
        type = "";
        name = "";
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
}
