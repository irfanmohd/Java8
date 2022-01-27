package com.jaav8.functionalinterface;

import java.util.function.Consumer;

public class Consumer1 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        String names[] = {"sunny", "kajal", "katrina"};

        Consumer<String> consumer = c -> {
            System.out.println(c);
        };

        for (String s : names) {
            consumer.accept(s);
        }

    }

}
