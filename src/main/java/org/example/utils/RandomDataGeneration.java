package org.example.utils;

import org.example.model.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.sql.Date;

public class RandomDataGeneration {

    private final List<String> employeeSsn;
    private final List<String> bossSSn;
    private final List<String> departmentSsn;
    private final List<String> fakeSsn;
    private final Set<String> fakeSsnSet;
    private final HashMap<String, Employee> listEmployee;

    public RandomDataGeneration(List<String> employeeSsn, List<String> bossSsn, List<String> departmentSsn) {
        this.employeeSsn = employeeSsn;
        this.bossSSn = bossSsn;
        this.departmentSsn = departmentSsn;
        this.fakeSsn = new ArrayList<>();
        this.fakeSsnSet = new HashSet<>();
        this.listEmployee = new HashMap<>();
    }

    public HashMap<String, Employee> createFakeEmployees(int n) {
        final Set<String> existingSsnSet = new HashSet<>(employeeSsn); // SSN που ήδη υπάρχουν
        final HashMap<String, Employee> listEmployee = new HashMap<>((int) (n / 0.75f) + 1);
        final List<String> fakeSsn = new ArrayList<>(n);
        final Set<String> fakeSsnSet = new HashSet<>((int) (n / 0.75f) + 1);

        final LocalDate today = LocalDate.now();
        final LocalDate max = today.minusYears(18);
        final LocalDate min = today.minusYears(65);
        final long days = ChronoUnit.DAYS.between(min, max);

        // Τυχαίο offset για να μη “πέφτουμε” συνέχεια στα ίδια ssn με τα existing
        int start = ThreadLocalRandom.current().nextInt(1_000_000);

        for (int i = 0; i < n; i++) {
            // --- SSN: 111 + 6 digits ---
            int candidate = (start + i) % 1_000_000;
            String ssnString = "111" + String.format("%06d", candidate);

            // Αν υπάρχει conflict με existing ή ήδη-φτιαγμένα, προχώρα λίγο μέχρι να βρεις κενό
            while (existingSsnSet.contains(ssnString) || fakeSsnSet.contains(ssnString)) {
                candidate = (candidate + 1) % 1_000_000;
                ssnString = "111" + String.format("%06d", candidate);
            }
            fakeSsnSet.add(ssnString);
            fakeSsn.add(ssnString);

            int ssn = Integer.parseInt(ssnString);

            // --- Ημερομηνία γέννησης ---
            long r = ThreadLocalRandom.current().nextLong(days + 1);
            LocalDate date = min.plusDays(r);
            Date bdate = Date.valueOf(date);

            // --- Λοιπά ---
            String fname = "Maria";
            String minit = String.valueOf((char) ('A' + ThreadLocalRandom.current().nextInt(26)));
            String lname = "Ok";
            String address = "Athens";
            String sex = ThreadLocalRandom.current().nextBoolean() ? "M" : "F";
            int salary = ThreadLocalRandom.current().nextInt(20_000, 25_000);

            String superSsnString = bossSSn.get(ThreadLocalRandom.current().nextInt(bossSSn.size()));
            int superSsn = Integer.parseInt(superSsnString);

            String dnoString = departmentSsn.get(ThreadLocalRandom.current().nextInt(departmentSsn.size()));
            int dno = Integer.parseInt(dnoString);

            Employee emp = new Employee(fname, minit, lname, ssn, bdate, address, sex, salary, superSsn, dno);
            listEmployee.put(ssnString, emp);
        }

        // Αν θέλεις να κρατήσεις πρόσβαση στη λίστα SSN:
        this.fakeSsn.clear();
        this.fakeSsn.addAll(fakeSsn);

        return listEmployee;
    }


    public List<String> getFakeSsn() {
        return fakeSsn;
    }
}