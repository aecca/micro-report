package com.bbva.reports.engine.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportData extends ArrayList<Map<String, Object>>{

    public static ReportData fromList(List<Map<String, Object>> list) {

        ReportData obj = new ReportData();
        obj.addAll(list);

        return obj;
    }
}
