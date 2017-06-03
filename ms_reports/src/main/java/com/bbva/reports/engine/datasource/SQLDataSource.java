package com.bbva.reports.engine.datasource;

import com.bbva.reports.engine.common.sql.ConnectionProvider;
import com.bbva.reports.engine.data.ReportData;
import com.bbva.reports.engine.model.ReportSource;
import com.bbva.reports.engine.model.ReportSourceParam;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLDataSource implements IDataSource {

    protected DataSource dataSource;

    protected SQLDataSource(DataSource dataSource) {
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
        return  source.type() == ReportSource.Type.SQL ||
                source.type() == ReportSource.Type.VIEW;
    }

    private void setStatementArguments(
            PreparedStatement aPreparedStatement,
            List<ReportSourceParam> arguments)
            throws SQLException {

        int idx = 0;
        for (ReportSourceParam param : arguments) {
            switch (param.type()) {
                case INT:
                    aPreparedStatement.setInt(idx + 1, Integer.valueOf(param.value()));
                    break;
                case BOOL:
                    aPreparedStatement.setBoolean(idx + 1, Boolean.valueOf(param.value()));
                    break;
                case LONG:
                    aPreparedStatement.setLong(idx + 1, Long.valueOf(param.value()));
                    break;
                case FLOAT:
                    aPreparedStatement.setFloat(idx + 1, Float.valueOf(param.value()));
                    break;
                case DOUBLE:
                    aPreparedStatement.setDouble(idx + 1, Double.valueOf(param.value()));
                    break;
                case DATE:
                    java.sql.Date sqlDate = new java.sql.Date((Date.valueOf(param.value())).getTime());
                    aPreparedStatement.setDate(idx + 1, sqlDate);
                    break;
                default:
                    aPreparedStatement.setString(idx + 1, param.value());
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
