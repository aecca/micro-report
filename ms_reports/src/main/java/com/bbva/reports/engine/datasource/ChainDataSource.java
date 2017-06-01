package com.bbva.reports.engine.datasource;

import com.bbva.reports.engine.data.ReportData;
import com.bbva.reports.engine.model.ReportSource;
import com.bbva.reports.engine.model.ReportSourceParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChainDataSource implements IDataSource {

    private List<IDataSource> processors;
    private IDataSource selectProcessor;

    ChainDataSource(IDataSource[] procs) {
        this.processors = new ArrayList<>();
        processors.addAll(Arrays.asList(procs));
    }

    ChainDataSource() {
        this.processors = new ArrayList<>();
    }

    public void addProcessor(IDataSource processor) {
        processors.add(processor);
    }

    @Override
    public ReportData getData(String input, List<ReportSourceParam> args) {
        return selectProcessor.getData(input, args);
    }

    @Override
    public boolean support(ReportSource source) {

        for(IDataSource p: processors) {
            if(p.support(source)) {
                selectProcessor = p;
                return true;
            }
        }

        return false;
    }
}
