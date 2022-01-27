package com.datastructure;

public class MethodOverloading {

    public String display(String srg) {

        System.out.println("String method invoked");
        return srg;
    }

    public Object display(Object srg) {

        System.out.println("Object method invoked");
        return srg;
    }


    public int print(int a, int b) {

        System.out.println("primitive int :::" + (a + b));
        return a + b;
    }

    public Integer print(Integer a, Integer b) {

        System.out.println("Wrapper class int :::" + (a + b));

        return a + b;
    }

    public void print(float a, float b) {

        System.out.println("float primitive type :::" + (a + b));
    }


    public void print(double a, double b) {

        System.out.println("Double primitive type :::" + (a + b));
    }


    public static void main(String args[]) {

        new MethodOverloading().display(null);

        int result = new MethodOverloading().print(5, 5);
        System.out.println("Result --->" + result);


    }
}
