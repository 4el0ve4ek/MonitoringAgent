package ru.hse.monitoringagent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.monitoringagent.model.Metric;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Integer> {
}
