package com.bbva.reports.engine.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportCollection extends JpaRepository<Report, Integer> {
}
