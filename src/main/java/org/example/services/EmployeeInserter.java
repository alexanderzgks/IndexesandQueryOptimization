package org.example.services;
import org.example.model.Employee;
import org.example.services.Queries.DepartmentQueries;
import org.example.utils.RandomDataGeneration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeeInserter {

    private final Connection connection;
    private final List<String> existedSsn;
    private final List<String> fakeEmployeeSsn;
    private final List<String> bossSsn;
    private final List<String> departmentNumber;
    private final HashMap<String, Employee> employeeHashMap;
    private final EmployeeQueries employeeQueries;
    private final DepartmentQueries departmentQueries;

    public EmployeeInserter(Connection connection) throws SQLException{
        this.connection = connection;
        this.employeeQueries = new EmployeeQueries(connection);
        this.departmentQueries = new DepartmentQueries(connection);
        this.existedSsn = getEmployeeSsn();
        this.bossSsn = getBossSsn();
        this.departmentNumber = getDepartmentNumber();
        this.fakeEmployeeSsn = new ArrayList<>();
        this.employeeHashMap = new HashMap<>();
    }

    public void insertEmployee() throws SQLException{
        createFakeEmployees();
        String sql = "INSERT INTO employee(fname, minit, lname, ssn, bdate, address, sex, salary, superssn, dno) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            for(String fakeEmployee : fakeEmployeeSsn){
                Employee e = employeeHashMap.get(fakeEmployee);
                stmt.setString(1, e.getFname());
                stmt.setString(2, e.getMinit());
                stmt.setString(3, e.getLname());
                stmt.setInt(4, e.getSsn());
                stmt.setDate(5, e.getBdate());
                stmt.setString(6, e.getAddress());
                stmt.setString(7, e.getSex());
                stmt.setInt(8, e.getSalary());
                stmt.setInt(9, e.getSuperSsn());
                stmt.setInt(10, e.getDno());

                stmt.executeUpdate();
            }
        }
    }

    private void createFakeEmployees(){
        RandomDataGeneration randomDataGeneration = new RandomDataGeneration(existedSsn, bossSsn, departmentNumber);
        this.employeeHashMap.putAll(randomDataGeneration.createFakeEmployees(100_000));
        this.fakeEmployeeSsn.addAll(randomDataGeneration.getFakeSsn());
    }

    private List<String> getEmployeeSsn() throws SQLException{
        return employeeQueries.fetchAllEmployeesSsn();
    }

    private List<String> getBossSsn() throws SQLException{
        return departmentQueries.fetchTheSsnOfEveryBossDepartment();
    }

    private List<String> getDepartmentNumber() throws SQLException{
        return departmentQueries.fetchTheDnumberOfEveryDepartment();
    }
}
