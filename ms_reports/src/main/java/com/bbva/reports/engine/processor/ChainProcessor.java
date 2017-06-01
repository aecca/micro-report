package com.bbva.reports.engine.processor;

import com.bbva.reports.engine.data.ReportData;
import com.bbva.reports.engine.model.ReportSource;
import com.bbva.reports.engine.model.ReportSourceParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChainProcessor implements IProcessor {

    private List<IProcessor> processors;
    private IProcessor selectProcessor;

    ChainProcessor(IProcessor[] procs) {
        this.processors = new ArrayList<>();
        processors.addAll(Arrays.asList(procs));
    }

    ChainProcessor() {
        this.processors = new ArrayList<>();
    }

    public void addProcessor(IProcessor processor) {
        processors.add(processor);
    }

    @Override
    public ReportData getData(String input, List<ReportSourceParam> args) {
        return selectProcessor.getData(input, args);
    }

    @Override
    public boolean support(ReportSource source) {

        for(IProcessor p: processors) {
            if(p.support(source)) {
                selectProcessor = p;
                return true;
            }
        }

        return false;
    }
}
