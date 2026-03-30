package org.example.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExecutionPlanPrinter {

    private final Connection connection;

    public ExecutionPlanPrinter(Connection connection){
        this.connection = connection;
    }


    public void printExplain(String query) throws SQLException {

        System.out.println("\n\nExecution Plan");

        if(query == null){
            System.out.println("SQL QUERY IS NULL.");
            return;
        }

        String explainSql   = "EXPLAIN PLAN FOR " + query;
        String planQuerySql = "SELECT PLAN_TABLE_OUTPUT FROM TABLE(DBMS_XPLAN.DISPLAY())";

        try (PreparedStatement explainStmt = connection.prepareStatement(explainSql)) {
            explainStmt.executeUpdate();
        }

        try (PreparedStatement planStmt = connection.prepareStatement(planQuerySql);
             ResultSet rs = planStmt.executeQuery()) {

            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        }
    }
}
