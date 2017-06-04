package com.bbva.reports.engine.datasource;

import com.bbva.reports.engine.data.ReportData;
import com.bbva.reports.engine.model.ReportSourceParam;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;

public class SpringSQLDataSource extends SQLDataSource {

    public SpringSQLDataSource(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ReportData getData(String query, List<ReportSourceParam> args) {
        NamedParameterJdbcTemplate jdb = new NamedParameterJdbcTemplate(dataSource);
        MapSqlParameterSource paramSource = new MapSqlParameterSource();

        for (ReportSourceParam arg : args) {
            if(arg.type() == null) {
                continue;
            }
            switch (arg.type()) {
                case INT:
                    paramSource.addValue(arg.name(), Integer.valueOf(arg.value()));
                    break;
                case BOOL:
                    paramSource.addValue(arg.name(), Boolean.valueOf(arg.value()));
                    break;
                case LONG:
                    paramSource.addValue(arg.name(), Long.valueOf(arg.value()));
                    break;
                case FLOAT:
                    paramSource.addValue(arg.name(), Float.valueOf(arg.value()));
                    break;
                case DOUBLE:
                    paramSource.addValue(arg.name(), Double.valueOf(arg.value()));
                    break;
                case DATE:
                    java.sql.Date sqlDate = new java.sql.Date((Date.valueOf(arg.value())).getTime());
                    paramSource.addValue(arg.name() + 1, sqlDate);
                    break;
                default:
                    paramSource.addValue(arg.name(), arg.value());
            }
        }

        return ReportData.fromList(jdb.queryForList(query, paramSource));
    }
}
