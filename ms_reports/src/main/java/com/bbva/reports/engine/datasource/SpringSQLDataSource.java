package com.bbva.reports.engine.datasource;

import com.bbva.reports.engine.common.utils.UtilTypes;
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

        System.out.println("QUERY : " + query);
        for (ReportSourceParam arg : args) {

            String value = arg.value();

            if (UtilTypes.isNumeric(value)) {
                paramSource.addValue(arg.name(), Integer.valueOf(value));
            } else if (UtilTypes.isBoolean(value)) {
                paramSource.addValue(arg.name(), Boolean.valueOf(value));
            } else if (UtilTypes.isDate(value)) {
                java.sql.Date sqlDate = new java.sql.Date((Date.valueOf(value)).getTime());
                paramSource.addValue(arg.name(), sqlDate);
            } else {
                paramSource.addValue(arg.name(), value);
            }
        }

        return ReportData.fromList(jdb.queryForList(query, paramSource));
    }
}
