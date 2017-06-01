package com.bbva.reports.engine.common.utils;

import org.apache.commons.lang.NumberUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class UtilTypes {

    public static boolean isNumeric(String object) {
        return NumberUtils.isNumber(object);
    }

    public static boolean isBoolean(String bool) {
        return "true".equals(bool) || "false".equals(bool);
    }

    public static boolean isDate(String date) {
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
