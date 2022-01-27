package com.singleton;

import java.util.HashMap;
import java.util.Iterator;

public final class Immutable {


    private final int id;

    private final String name;

    private final HashMap<String, String> testMap;

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "Immutable [id=" + id + ", name=" + name + ", testMap=" + testMap + "]";
    }


    public String getName() {
        return name;
    }

    /**
     * Accessor function for mutable objects
     */
    public HashMap<String, String> getTestMap() {
        return testMap;
        //return (HashMap<String, String>) testMap.clone();
    }

    /**
     * Constructor performing Deep Copy
     *
     * @param i
     * @param n
     * @param hm
     */

    public Immutable(int i, String n, HashMap<String, String> hm) {
        System.out.println("Performing Deep Copy for Object initialization");
        this.id = i;
        this.name = n;

        this.testMap = hm;
    }


    /**
     * To test the consequences of Shallow Copy and how to avoid it with Deep Copy for creating immutable classes
     *
     * @param args
     */
    public static void main(String[] args) {
        final HashMap<String, String> h1 = new HashMap<String, String>();
        h1.put("1", "first");
        h1.put("2", "second");

        String s = "original";

        int i = 10;

        Immutable ce = new Immutable(i, s, h1);

        System.out.println(ce);

        final int b = 12;

        final int arr1[] = {1, 2, 3, 4, 5};
        int arr2[] = {10, 20, 30, 40, 50};
        //arr2 = arr1;
//	       arr1 = arr2;   
        for (int i1 = 0; i < arr2.length; i++)
            System.out.println(arr2[i1]);
    }


}
	


