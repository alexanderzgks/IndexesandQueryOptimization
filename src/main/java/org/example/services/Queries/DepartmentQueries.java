package org.example.services.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/*
 *  Κλάση με Queries που αφορούν τον Department.
 */
public class DepartmentQueries {

    private final Connection connection;

    public DepartmentQueries(Connection connection){
        this.connection = connection;
    }

    /*
     * Χρησιμοποιώ τη μέθοδο αυτή για να πάρω τα ssn τον υπευθύνων.
     */
    public List<String> fetchTheSsnOfEveryBossDepartment() throws SQLException {
        String sql = "SELECT mgrssn FROM department";
        List<String> ssn = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ssn.add(rs.getString("mgrssn"));
            }
        }
        return ssn;
    }


    /*
     * Χρησιμοποιώ τη μέθοδο αυτή για να πάρω το dnumber κάθε τμήματος.
     */
    public List<String> fetchTheDnumberOfEveryDepartment() throws SQLException{
        String sql = "SELECT dnumber FROM department";
        List<String> dnumber = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            while (rs.next()){
                dnumber.add(rs.getString("dnumber"));
            }
        }
        return dnumber;
    }

}
