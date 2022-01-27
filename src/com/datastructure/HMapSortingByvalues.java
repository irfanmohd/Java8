package com.datastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class HMapSortingByvalues {

    public static void main(String[] args) {// let's create a map with Java releases and their code names
        HashMap<String, Employee> codenames = new HashMap<String, Employee>();

        Employee e1 = new Employee(1, "aaa");
        Employee e2 = new Employee(1, "zzz");
        Employee e3 = new Employee(1, "ccc");
	        
	       /* codenames.put("JDK 1.1.4", "Sparkler");
	        codenames.put("J2SE 1.2", "Playground");
	        codenames.put("J2SE 1.3", "Kestrel");
	        codenames.put("J2SE 1.4", "Merlin");
	        codenames.put("J2SE 5.0", "Tiger");
	        codenames.put("Java SE 6", "Mustang");
	        codenames.put("Java SE 7", "Dolphin");*/

        codenames.put("JDK 1.1.4", e1);
        codenames.put("J2SE 1.2", e2);
        codenames.put("J2SE 1.3", e3);


        System.out.println("HashMap before sorting, random order ");
        Set<Entry<String, Employee>> entries = codenames.entrySet();

        for (Entry<String, Employee> entry : entries) {
            System.out.println(entry.getKey() + " ==> " + entry.getValue());
        }

        // Now let's sort HashMap by keys first
        // all you need to do is create a TreeMap with mappings of HashMap
        // TreeMap keeps all entries in sorted order
        TreeMap<String, Employee> sorted = new TreeMap<>(codenames);
        Set<Entry<String, Employee>> mappings = sorted.entrySet();

        System.out.println("HashMap after sorting by keys in ascending order ");
        for (Entry<String, Employee> mapping : mappings) {
            System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
        }


        // Now let's sort the HashMap by values
        // there is no direct way to sort HashMap by values but you
        // can do this by writing your own comparator, which takes
        // Map.Entry object and arrange them in order increasing
        // or decreasing by values.

        Comparator<Entry<String, Employee>> valueComparator = new Comparator<Entry<String, Employee>>() {

            @Override
            public int compare(Entry<String, Employee> e1, Entry<String, Employee> e2) {
                String v1 = e1.getValue().getName();
                String v2 = e2.getValue().getName();
                return v1.compareTo(v2);
            }
        };

        // Sort method needs a List, so let's first convert Set to List in Java
        List<Entry<String, Employee>> listOfEntries = new ArrayList<Entry<String, Employee>>(entries);

        // sorting HashMap by values using comparator
        Collections.sort(listOfEntries, valueComparator);

        LinkedHashMap<String, Employee> sortedByValue = new LinkedHashMap<String, Employee>(listOfEntries.size());

        // copying entries from List to Map
        for (Entry<String, Employee> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }

        System.out.println("HashMap after sorting entries by values  ");
        Set<Entry<String, Employee>> entrySetSortedByValue = sortedByValue.entrySet();
		entrySetSortedByValue.forEach(k-> System.out.println(k));

        for (Entry<String, Employee> mapping : entrySetSortedByValue) {
            System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
        }
    }


}
