package com.jaav8.functionalinterface;

import java.util.function.Function;

public class Function1 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        //Example 1
        Function<String, Integer> f1 = s -> s.length();

        System.out.println(f1.apply("irfan"));

        //Example 2
        String name = "Prokarma softech";
        Function<String, String> f2 = s -> s.replaceAll(" ", "");

        System.out.println(f2.apply(name));


        //Example 3
        String str = "Durga software solutions";
        Function<String, Integer> f3 = s -> s.length() - s.replaceAll(" ", "").length();


        System.out.println("No of spaces in given string" + f3.apply(str));

        //then and compose methods

        Function<Integer, Integer> f4 = i -> i + i;
        Function<Integer, Integer> f5 = i -> i * i * i;
        //System.out.println("Then m "+f4.andThen(f5).apply(2));
        System.out.println("Then m " + f4.compose(f5).apply(2));
    }

}
