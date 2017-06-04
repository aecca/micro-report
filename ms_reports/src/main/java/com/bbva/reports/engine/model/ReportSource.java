package com.bbva.reports.engine.model;

import com.bbva.reports.engine.common.utils.EnumType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ReportSource {

    private String name;
    private String content;
    private Type type;
    private boolean collection = false;


    public static ReportSource create(
            String name,
            String content,
            Type sourceType,
            List<ReportSourceParam> params) {

        ReportSource repSource = new ReportSource();
        repSource.name = name;
        repSource.content = content;
        repSource.type = sourceType;
        repSource.params = params;

        return repSource;
    }

    private List<ReportSourceParam> params;

    public String name() {
        return name;
    }

    public String content() {
        return content;
    }

    public List<ReportSourceParam> params() {
        return params;
    }

    public Type type() {
        return type;
    }

    public boolean isCollection() { return collection; }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReportSource{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", params=" + params +
                '}';
    }

    public enum Type {
        SQL,
        VIEW,
        JSON;

        @JsonCreator
        public static Type forValue(String value) {

            Type eNum = EnumType.searchEnum(Type.class, value);

            if (eNum == null) {
                throw new IllegalArgumentException("Invalid source type: " + value);
            }

            return eNum;
        }
    }
}
