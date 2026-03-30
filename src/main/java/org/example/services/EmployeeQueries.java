package org.example.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeQueries {

    private final Connection connection;

    private final String sql = "select e.fname, e.lname, e.salary, dl.dlocation from employee e join dept_locations dl on e.dno = dl.dnumber";

    public EmployeeQueries(Connection connection){
        this.connection = connection;
    }

    public void fetchEmployeeData() throws SQLException {
        try(PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql)){
            System.out.println();
            printHead();
            while(rs.next()){
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String salary = rs.getString("salary");
                String dept_location = rs.getString("dlocation");
                printLoopInfo(fname, lname, salary, dept_location);
            }
        }
    }

    public String getSqlQuery(){
        return sql;
    }

    public List<String> fetchAllEmployeesSsn() throws SQLException {
        String sql = "SELECT ssn FROM employee";
        List<String> ssn = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                ssn.add(rs.getString("ssn"));
            }
        }
        return ssn;
    }


    private void printHead(){
        System.out.println();
        System.out.println("ΠΙΝΑΚΑΣ");
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("| %-15s | %-15s | %-10.10s | %-20s|%n",
                "First Name", "Last Name", "Salary", "Department Location");
        System.out.println("-----------------------------------------------------------------------");
    }


    private void printLoopInfo(String fname, String lname, String salary, String dept_location){
        System.out.printf("| %-15s | %-15s | %-10.10s | %-20s|%n",fname, lname, salary, dept_location);
        System.out.println("-----------------------------------------------------------------------");
    }


}
