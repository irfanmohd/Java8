package com.java8.sortings;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArrayListSort {

    public static void main(String[] args) {
        Employee emp1 = new Employee(123, "B");
        Employee emp2 = new Employee(231, "C");
        Employee emp3 = new Employee(231, "A");

        List<Employee> empList = new ArrayList<Employee>();
        empList.add(emp1);
        empList.add(emp2);
        empList.add(emp3);

        //  empList.sort((o1, o2) -> o2.getEmpName().compareTo(o1.getEmpName()));

        //Using method refernece
        empList.sort(Comparator.comparing(Employee::getEmpName));

        System.out.println("Sorted List" + empList);

        List lists = null;
        lists.forEach(s -> System.out.println(s));

    }

}
