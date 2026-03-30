package org.example.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateEmployeeIndex {

    private Connection connection;

    public CreateEmployeeIndex(Connection connection){
        this.connection = connection;
    }

    public void createIndex(String tableName, String columnName, String indexName) throws SQLException{
        String createIndexQuery = "CREATE INDEX " + indexName + " ON " + tableName + "(" + columnName + ")";
        try(Statement stmt = connection.createStatement()){
            stmt.executeUpdate(createIndexQuery);
            System.out.println("Index created successfully on " + columnName);
        }
    }

    public void deleteIndex(String indexName)
            throws SQLException {
        String dropIndexQuery = "DROP INDEX "+indexName;
        try (Statement stmt = connection.createStatement()){
            stmt.executeUpdate(dropIndexQuery);
            System.out.println("Index " + indexName + " dropped successfully");
        }
    }
}
