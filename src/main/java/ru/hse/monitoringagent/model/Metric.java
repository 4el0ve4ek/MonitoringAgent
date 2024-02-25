package ru.hse.monitoringagent.model;


import jakarta.persistence.Entity;

@Entity
public class Metric {
    public String Name;
    public String Value;
}
