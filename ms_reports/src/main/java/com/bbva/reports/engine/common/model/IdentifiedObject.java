package com.bbva.reports.engine.common.model;

import java.io.Serializable;

/**
 * @author aecca
 */
public class IdentifiedObject implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
