package com.java8.sortings;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class SortMap {

    public static void main(String[] args) {


        Map<Integer, String> map = new HashMap<>();
        map.put(15, "Mahesh");
        map.put(10, "Suresh");
        map.put(30, "Nilesh");

        System.out.println("Sort by KEYS:::::::");
        Map<Integer, String> sortedByPrice = map.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, String>comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        sortedByPrice.forEach((k, v) -> System.out.println(k + ":" + v));


        System.out.println("\n\n");

        System.out.println("Sort by Values:::::::");
        Map<Integer, String> sortedValue = map.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, String>comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        sortedValue.forEach((k, v) -> System.out.println(k + ":" + v));


        System.out.println("\n\n");

        System.out.println("Sort by custom  Values:::::::");

        Employee emp1 = new Employee(423, "uohn Doe");
        Employee emp2 = new Employee(231, "Te Lobo");
        Employee emp3 = new Employee(221, "Dave Mathias");


        Map<Integer, Employee> custom = new HashMap<>();
        custom.put(15, emp1);
        custom.put(10, emp2);
        custom.put(30, emp3);
        SortMap sortMap = new SortMap();

        Map<Integer, Employee> sortedByEmp = custom.entrySet()
                .stream()
                .sorted(Entry.comparingByValue(sortMap::compare))
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));


        sortedByEmp.forEach((k, v) -> System.out.println(k + ":" + v));
    }

    public int compare(Employee e1, Employee e2) {
        return e1.getEmpId() - e2.getEmpId();


    }

}
