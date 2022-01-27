package com.java8.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


/*
 * This is example of java8 stream groupingBy example
 *
 */
public class StudentTest {

    public static void main(String[] args) {
        List<Student> students = Arrays.asList(new Student("Math", "John", "Smith", "Miami", 19),
                new Student("Programming", "Mike", "Miles", "New York", 21),
                new Student("Math", "Michael", "Peterson", "New York", 27),
                new Student("Math", "James", "Robertson", "Miami", 20),
                new Student("Programming", "Kyle", "Miller", "Miami", 21));

        //1st overloaded method
        Map<String, List<Student>> studentsBySubject = students.stream()
                .collect(Collectors.groupingBy(Student::getSubject));

        System.out.println("======Grouping by subject");

        studentsBySubject.forEach((k, v) -> {

            List<Student> lists = v;
            for (Student std : lists) {
                System.out.println(std.getAge());

            }
        });

        //2nd overloaded method

        System.out.println("======filter student object based on name");
        Map<String, List<String>> studentsByCity = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getCity,
                        Collectors.mapping(Student::getName, Collectors.toList())));

        System.out.println(studentsByCity);

        System.out.println("======list of student to theri count");

        Map<Integer, Long> countByAge = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getAge,
                        Collectors.counting()));

        System.out.println(countByAge);

        //3rd overloaded method
        Map<String, List<String>> namesByCity = students.stream()
                .collect(Collectors.groupingBy(
                        Student::getCity,
                        TreeMap::new,
                        Collectors.mapping(Student::getName, Collectors.toList())));

        System.out.println(namesByCity);
        // get a person with the minimum income
        Student min = students.stream()
                .collect(Collectors.minBy(
                        Comparator.comparingInt(Student::getAge)))
                .get();
        System.out.println("Employee with minimum age " + min);

        // get a person with the maximum income
        Student max = students.stream()
                .collect(Collectors.maxBy(
                        Comparator.comparingInt(Student::getAge)))
                .get();
        System.out.println("Employee with maximum age " + max);

        //add one one more stream methods

    }


}
