package com.java8.optional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OptionalMain {

    public static void main(String args[]) {

        Optional<String> optional = Optional.empty();
        System.out.println(optional);

        Student std = new Student(1, "irfan", 23);
        Optional<Student> op = Optional.ofNullable(std);
        System.out.println(optional.isPresent());
        if (op.isPresent()) {
            String name = op.get().getName();
            System.out.println(name);
        }

        // To avoid null pointer exception while iterating list.
        List list1 = new ArrayList<String>();
        if (Optional.ofNullable(list1).isPresent()) {
            list1.forEach(e -> System.out.println(e));
        }

        List<Integer> list = Arrays.asList(12, 38, 49, 76, 50, 378);
        Optional<Integer> optionalList = list.stream().filter(e -> e > 378).findFirst();
        if (optionalList.isPresent()) {
            System.out.println("#########" + optionalList.get());
        }


    }
}
