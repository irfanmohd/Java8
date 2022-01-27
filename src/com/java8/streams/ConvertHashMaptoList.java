package com.java8.streams;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ConvertHashMaptoList {

    public static void main(String[] args) {

        HashMap<Integer, String> customerIdNameMap = new HashMap<Integer, String>();
        // Putting key-values pairs in HashMap
        customerIdNameMap.put(1001, "Arman");
        customerIdNameMap.put(1002, "Javin");
        customerIdNameMap.put(1003, "Mat");
        customerIdNameMap.put(1004, "Joe");

        // Java 8
        // Convert keys to ArrayList
        List<Integer> customerIdList = customerIdNameMap.keySet()
                .stream()
                .collect(Collectors.toList());
        System.out.println("customerIds: " + customerIdList);

        // Convert values to ArrayList
        List<String> customerNames = customerIdNameMap.values()
                .stream()
                .collect(Collectors.toList());
        System.out.println("Customer Names: " + customerNames);

        // Convert entry objects to ArrayList
        List<Entry<Integer, String>> entryCustomerList = customerIdNameMap.entrySet()
                .stream()
                .collect(Collectors.toList());
        System.out.println("Customer ID and Names: " + entryCustomerList);
    }

}
