package org.example;

import org.example.services.CreateEmployeeIndex;
import org.example.services.EmployeeInserter;
import org.example.services.EmployeeQueries;
import org.example.services.ExecutionPlanPrinter;

import static org.example.db.DBUtils.getConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {

    private final Scanner scanner;
    private final EmployeeInserter employeeInserter;
    private final EmployeeQueries employeeQueries;
    private final ExecutionPlanPrinter executionPlanPrinter;
    private final CreateEmployeeIndex createEmployeeIndex;
    private static final Pattern MENU_CHOICE = Pattern.compile("^[0-5]$");


    public Menu() throws SQLException {
        this.scanner = new Scanner(System.in);
        Connection connection = getConnection();
        this.employeeInserter = new EmployeeInserter(connection);
        this.employeeQueries = new EmployeeQueries(connection);
        this.executionPlanPrinter = new ExecutionPlanPrinter(connection);
        this.createEmployeeIndex = new CreateEmployeeIndex(connection);
    }

    public void showMenu() throws SQLException{

        String sql = null;

        while(true){
            System.out.println("\n====== ΕΠΙΛΟΓΕΣ ======");
            System.out.println("1) Εισαγωγή 100.000 υπαλλήλων.");
            System.out.println("2) Προβολή ονομάτων, επωνύμων ,μισθών και τοποθεσία τμήματος.");
            System.out.println("3) Προβολή πλάνου εκτέλεσης.");
            System.out.println("4) Δημιουργία ευρετηρίου.");
            System.out.println("0) Έξοδός.");
            String choice = doCheck();
            switch (choice){
                case "1":
                    employeeInserter.insertEmployee();
                    break;
                case "2":
                    employeeQueries.fetchEmployeeData();
                    sql = employeeQueries.getSqlQuery();
                    break;
                case "3":
                    executionPlanPrinter.printExplain(sql);
                    break;
                case "4":
                    createEmployeeIndex.createIndex("EMPLOYEE", "DNO", "idx_employee_dno");
                    employeeQueries.fetchEmployeeData();
                    sql = employeeQueries.getSqlQuery();
                    executionPlanPrinter.printExplain(sql);
                    createEmployeeIndex.deleteIndex("idx_employee_dno");
                    break;
                case "0":
                    return;
            }

        }
    }

    private String doCheck(){
        while(true){
            System.out.print("Επέλεξε από 0 εώς 4: ");
            String info = scanner.nextLine().trim();

            if(info.isBlank()){
                System.out.println("Δεν μπορεί να είναι κενό.");
                continue;
            }
            if(MENU_CHOICE.matcher(info).matches()){
                return info;
            }
            System.out.println("Λάθος εισαγωγή! Δεκτές τιμές από 0 εώς 5!");
        }
    }


}
