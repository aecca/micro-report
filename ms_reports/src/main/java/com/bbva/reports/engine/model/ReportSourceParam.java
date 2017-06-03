package com.bbva.reports.engine.model;

import com.bbva.reports.engine.common.utils.EnumType;
import com.fasterxml.jackson.annotation.JsonCreator;

public class ReportSourceParam {

    private String name;
    private String value;
    private Boolean required = false;
    private Type type;

    public static ReportSourceParam create(String name, String value, Type type, boolean isRequired) {

        ReportSourceParam repSourceParam = new ReportSourceParam();
        repSourceParam.name = name;
        repSourceParam.value = value;
        repSourceParam.type  = type;
        repSourceParam.required = isRequired;

        return repSourceParam;
    }

    public String name() {
        return name;
    }

    public String value() {
        return value;
    }

    public Boolean isRequired() {
        return required;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ReportSourceParam{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", required=" + required +
                ", type=" + type +
                '}';
    }

    public enum Type {
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        BOOL,
        DATE,
        STRING;

        @JsonCreator
        public static Type forValue(String value) {

            if(value == null) {
                // Por defecto, en caso no se especifique el tipo de variable,
                // se asume que es del tipo String.
                return Type.STRING;
            }

            Type eNum = EnumType.searchEnum(Type.class, value);

            if (eNum == null) {
                throw new IllegalArgumentException("Invalid param type: " + value);
            }

            return eNum;
        }
    }
}
