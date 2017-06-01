package com.bbva.reports.engine.template;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public interface ITemplateEngine {

    public String renderString(String content, Map<String, Object> data);
    public String renderFile(File file, Map<String, Object> data) throws FileNotFoundException;
}
