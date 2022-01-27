package com.java8.sortings;

public class Employee {
    private String empName;
    private int empId;

    public Employee(int empId, String empName) {
        this.empId = empId;
        this.empName = empName;
    }

    public String getEmpName() {
        return empName;
    }

    public int getEmpId() {
        return empId;
    }

    @Override
    public String toString() {
        return "Employee [empName=" + empName + ", empId=" + empId + "]";
    }


}
