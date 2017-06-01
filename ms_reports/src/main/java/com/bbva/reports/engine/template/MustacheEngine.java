package com.bbva.reports.engine.template;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.*;
import java.util.Map;

public class MustacheEngine implements ITemplateEngine {

    private MustacheFactory engine;

    public MustacheEngine() {
        this.engine = new DefaultMustacheFactory();
    }

    @Override
    public String renderString(String content, Map<String, Object> data) {

        StringWriter memWriter = new StringWriter();
        Mustache mustache = engine.compile(new StringReader(content), "document me!");
        mustache.execute(memWriter, data);

        return memWriter.toString();
    }

    @Override
    public String renderFile(File file, Map<String, Object> data) throws FileNotFoundException {
        StringWriter memWriter = new StringWriter();
        Mustache mustache = engine.compile(new FileReader(file), "document me!");
        mustache.execute(memWriter, data);

        return memWriter.toString();
    }
}
