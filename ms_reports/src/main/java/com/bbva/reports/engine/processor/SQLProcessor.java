package com.bbva.reports.engine.processor;

import com.bbva.reports.engine.common.sql.ConnectionProvider;
import com.bbva.reports.engine.common.utils.UtilTypes;
import com.bbva.reports.engine.data.ReportData;
import com.bbva.reports.engine.model.ReportSource;
import com.bbva.reports.engine.model.ReportSourceParam;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLProcessor implements IProcessor {

    protected DataSource dataSource;

    protected SQLProcessor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ReportData getData(String query, List<ReportSourceParam> args) {
        Connection connection = ConnectionProvider.connection(this.dataSource);
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;

        ReportData items = new ReportData();

        try {
            System.out.println("Args lenght: " + args.size());
            System.out.println("Query : " + query);
            System.out.println("Args : ");
            for (Object reqParam : args) {
                System.out.println("Args :: ----> " + reqParam);
            }
            selectStatement = connection.prepareStatement(query);
            this.setStatementArguments(selectStatement, args);
            resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                ResultSetMetaData md = resultSet.getMetaData();
                int columns = md.getColumnCount();

                Map<String, Object> row = new HashMap<>(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), resultSet.getObject(i));
                }

                items.add(row);
            }


        } catch (Exception e) {
            throw new IllegalStateException("Cannot query: " + query, e);
        } finally {
            this.close(selectStatement, resultSet);
        }

        return items;
    }

    @Override
    public boolean support(ReportSource source) {
        String sourceType = source.type().name();
        return sourceType.contains("SQL") || sourceType.contains("VIEW");
    }

    private void setStatementArguments(
            PreparedStatement aPreparedStatement,
            List<ReportSourceParam> arguments)
            throws SQLException {

        int idx = 0;
        for (ReportSourceParam param : arguments) {

            String value = param.value();

            if (UtilTypes.isNumeric(value)) {
                aPreparedStatement.setInt(idx + 1, Integer.valueOf(value));
            } else if (UtilTypes.isBoolean(value)) {
                aPreparedStatement.setBoolean(idx + 1, Boolean.valueOf(value));
            } else if (UtilTypes.isDate(value)) {
                java.sql.Date sqlDate = new java.sql.Date((Date.valueOf(value)).getTime());
                aPreparedStatement.setDate(idx + 1, sqlDate);
            } else {
                // @todo Add More type or aggregate types to engine
                aPreparedStatement.setString(idx + 1, value);
            }
            idx++;
        }
    }

    private void close(Statement aStatement, ResultSet aResult) {
        if (aStatement != null) {
            try {
                aStatement.close();
            } catch (Exception e) {
                // ignore
            }
        }
        if (aResult != null) {
            try {
                aResult.close();
            } catch (Exception e) {
                // ignore
            }
        }

        ConnectionProvider.closeConnection();
    }
}
