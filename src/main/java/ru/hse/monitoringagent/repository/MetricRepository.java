package ru.hse.monitoringagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.monitoringagent.model.Metric;

import java.util.List;

@Repository
public interface MetricRepository extends JpaRepository<Metric, String> {
    void removeMetricByNameAndSource(String name, String source);
}
