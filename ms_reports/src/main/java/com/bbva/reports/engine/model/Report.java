package com.bbva.reports.engine.model;

import com.bbva.reports.engine.common.model.IdentifiedObject;
import com.bbva.reports.engine.common.utils.EnumType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Report extends IdentifiedObject {

    private String name;
    private String content;
    private List<ReportSource> sources;
    private ReportType type;
    private Date createdAt;

    public Report() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = new Date();
    }

    public String name() {
        return name;
    }

    public String content() {
        return content;
    }

    public ReportType type() {
        return type;
    }

    public List<ReportSource> sources() {
        return sources;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", sources=" + sources +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }

    public enum ReportType {
        /**
         * Tipo de formato HTML. este tipo de reporte debe ser generado mediante
         * algun editor.
         */
        HTML,

        /**
         * Tipo de formato XML. este puede ser generado directamente
         * o tambien desde BIRT Designer..
         */
        XML;

        @JsonCreator
        public static ReportType forValue(String value) {
            ReportType eNum = EnumType.searchEnum(ReportType.class, value);

            if (eNum == null) {
                throw new IllegalArgumentException("Invalid report type: " + value);
            }

            return eNum;
        }
    }
}
