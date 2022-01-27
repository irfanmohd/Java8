package com.jaav8.functionalinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Predicate1 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        String names[] = {"sunny", "kajal", "katrina"};

        Predicate<String> predicate = s -> s.charAt(0) == 'k';

        for (String s : names) {
            if (predicate.test(s)) {
                System.out.println(s);
            }
        }


        //checking null and non empty
        String name2[] = {"", null, "ajay"};
        Predicate<String> predicateTwo = s -> s != null && s.length() != 0;
        List<String> lists = new ArrayList<>();

        for (String s : name2) {
            if (predicateTwo.test(s)) {
                lists.add(s);
            }
        }

        System.out.println("list size### " + lists.size());


        Predicate<String> namePredicate = Predicate.isEqual("kajal");
        for (String s : names) {
            if (namePredicate.test(s)) {
                System.out.println("Equals#### " + s);
            }
        }
    }

}
