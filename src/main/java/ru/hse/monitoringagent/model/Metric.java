package ru.hse.monitoringagent.model;



import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Metric {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    public Long id;
    @Basic(optional = false)
    @Column(nullable = false)
    public String name;
    @Basic(optional = true)
    @Column(nullable = true)
    public String source;
    @Basic(optional = true)
    @Column(nullable = true)
    public Float value;

    @Temporal(TemporalType.TIMESTAMP)
    public Date lastUpdateTime;

//    TODO: public Map<String, String> Tags;

}
