package com.java8.sortings;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortList {


    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(7, 8, 1, 3, 2, 0, 78);

	        /* 
			List<String> sortedList = list.stream()
				.sorted(Comparator.naturalOrder())
				.collect(Collectors.toList());
				
	        List<String> sortedList = list.stream()
				.sorted((o1,o2)-> o1.compareTo(o2))
				.collect(Collectors.toList());
			*/

        List<Integer> sortedList = list.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        sortedList.forEach(System.out::println);
    }
}
