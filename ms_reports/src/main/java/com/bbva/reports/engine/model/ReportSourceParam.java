package com.bbva.reports.engine.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_report_source_param")
public class ReportSourceParam implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @Column
    public String name;

    @Column
    public String value;

    @Column
    public Boolean required;

    public String name() {
        return name;
    }
    public String value() { return value; };

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean isRequired() {
        return required;
    }

    @Override
    public String toString() {
        return "ReportSourceParam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", required=" + required +
                '}';
    }
}
