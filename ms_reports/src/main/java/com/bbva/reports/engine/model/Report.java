package com.bbva.reports.engine.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_report")
public class Report implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @Column(nullable = false, length = 40)
    public String name;

    @Column
    public String content;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    public List<ReportSource> sources;

    @Enumerated(EnumType.STRING)
    public FormatType type;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date createdAt;

    public String name() {
        return name;
    }

    public String content() {
        return content;
    }

    public FormatType type() {
        return type;
    }

    public List<ReportSource> sources() {
        return sources;
    }

    public enum FormatType {
        /**
         * Tipo de formato HTML, que puede ser generado mediante
         * algun editor.
         */
        HTML,

        /**
         * Tipo de formato XML. este puede ser generado directamen.
         * o tambien desde BIRT Designer..
         */
        XML;
    }
}
