package com.increment.operator;

class IncrementOperatorTest {
    public static void main(String[] args) {
        int a = 5;
        int b = 10 + (++a);

        System.out.println("Pre increment");
        System.out.println("a====>" + a);
        System.out.println("b====>" + b);

        b = 10 + (a++);

        System.out.println("Post increment");
        System.out.println("a====>" + a);
        System.out.println("b====>" + b);

    }
}
