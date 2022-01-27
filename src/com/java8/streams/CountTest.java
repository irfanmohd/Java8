package com.java8.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class CountTest {

    public static void main(String args[]) {

        /*
         * List<String> lists =
         * Arrays.asList("Babr","akbar","salim","Tainur","Jhangir");
         *
         * long count = lists.stream().filter(e->e.length()>=6).count();
         * System.out.println("name having length > 6 i :"+count);
         */


        long count = Stream.of("how", "to", "do", "in", "java").count();
        System.out.printf("There are %d elements in the stream %n", count);

        count = IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).count();
        System.out.printf("There are %d elements in the stream %n", count);

        count = LongStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).filter(i -> i % 2 == 0).count();
        System.out.printf("There are %d elements in the stream %n", count);
    }

}
