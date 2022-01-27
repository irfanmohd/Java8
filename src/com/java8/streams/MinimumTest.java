package com.java8.streams;

import java.util.Arrays;
import java.util.List;

public class MinimumTest {

    public static void main(String args[]) {
        List<Integer> list = Arrays.asList(7, 8, 1, 3, 2, -3, 78);

        Integer count = list.stream().min((i1, i2) -> i1.compareTo(i2)).get();
        System.out.println(count);
    }
}
