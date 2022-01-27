package com.singleton;

public class Singleton implements Cloneable {


    private static Singleton instance = new Singleton();

    private static Singleton getInstance() {
        return instance;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Singleton obj = Singleton.getInstance();
        System.out.println(obj);
        System.out.println(obj.clone());
    }

}
