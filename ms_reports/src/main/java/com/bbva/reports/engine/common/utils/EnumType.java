package com.bbva.reports.engine.common.utils;

/**
 * Util for Enums types
 *
 * See {@linktourl https://stackoverflow.com/a/28333189}
 */
public class EnumType {

    public static <T extends Enum<?>> T searchEnum(Class<T> enumeration,
                                                       String search) {
        for (T each : enumeration.getEnumConstants()) {
            if (each.name().compareToIgnoreCase(search) == 0) {
                return each;
            }
        }

        return null;
    }
}
