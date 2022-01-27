package com.java8.streams;

import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;

import com.java8.sortings.Employee;

public class Maptest {

    public static void main(String args[]) {

        List<Person> persons = Arrays.asList(
                new Person("e1", "l1", 10),
                new Person("e2", "l1", 20),
                new Person("e3", "l2", 30),
                new Person("e4", "l2", 40));

        List<Employee> stream = persons.stream().
                filter(p -> p.getAge() > 20).
                map(p -> new Employee(100, p.getName())).
                collect(Collectors.toList());

        System.out.println(stream);
    }
}
