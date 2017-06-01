package com.bbva.reports.engine.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportSourceCollection extends JpaRepository<ReportSource, Long> {
}
