package com.java8.lamdaexpressions;

public class LamdaInteraceTest2 {

    public static void main(String args[]) {

        LamdaInterace2 lx = (a, b) -> {
            return a + b;

        };

        System.out.println(lx.add(10, 20));
    }

}
