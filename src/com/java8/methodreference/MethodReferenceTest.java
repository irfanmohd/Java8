package com.java8.methodreference;

public class MethodReferenceTest {

    public static void main(String args[]) {

        Addition addition = new Addition();
        //using  lambda expressions
        Add a1 = (a, b) -> {
            return add(a, b);
        };

        System.out.println(" Using lambda expresson::" + a1.add(10, 10));

        Add a2 = addition::add;
        a2.add(20, 20);

    }

    public static int add(int a, int b) {
        return a + b;
    }

}
