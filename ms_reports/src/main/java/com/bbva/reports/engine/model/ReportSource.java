package com.bbva.reports.engine.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_report_source")
public class ReportSource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @Column
    public String name;

    @Column
    public String content;

    @Column
    public boolean collection;

    @OneToOne
    @JoinColumn(name="report_source_type_id")
    private ReportSourceType type;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="report_source_id")
    public List<ReportSourceParam> params;

    public String name() {
        return name;
    }
    public String content() {
        return content;
    }
    public List<ReportSourceParam> params() {
        return params;
    }

    public boolean isCollection() {
        return collection;
    }

    public ReportSourceType type() {
        return type;
    }
}
