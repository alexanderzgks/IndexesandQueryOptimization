package org.example.model;


import java.sql.Date;

public class Employee {

    private String fname;
    private String minit;
    private String lname;
    private int ssn;
    private Date bdate;
    private String address;
    private String sex;
    private int salary;
    private int superSsn;
    private int dno;

    public Employee(String fname, String minit, String lname, int ssn, Date bdate,
                    String address, String sex, int salary, int superSsn, int dno){
        this.fname = fname;
        this.minit = minit;
        this.lname = lname;
        this.ssn = ssn;
        this.bdate = bdate;
        this.address = address;
        this.sex = sex;
        this.salary = salary;
        this.superSsn = superSsn;
        this.dno = dno;
    }

    public String getFname(){ return fname; }
    public String getMinit(){ return minit; }
    public String getLname(){ return lname; }
    public int getSsn(){ return ssn; }
    public Date getBdate(){ return bdate; }
    public String getAddress(){ return address; }
    public String getSex(){ return sex; }
    public int getSalary(){ return salary; }
    public int getSuperSsn(){ return superSsn; }
    public int getDno(){ return dno; }

}
