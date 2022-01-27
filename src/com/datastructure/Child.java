package com.datastructure;

public class Child extends Parent {

    public Child get() {

        return this;
    }

    void message() {
        System.out.println("welcome to covariant return type");
    }

    public static void main(String args[]) {
        new Child().get().message();
    }

}
