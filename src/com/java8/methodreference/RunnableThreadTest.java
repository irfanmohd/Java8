package com.java8.methodreference;

public class RunnableThreadTest {

    public static void main(String args[]) {
        /*
         * // Using Lambda expression Runnable rb = () -> { for (int i = 0; i < 5; i++)
         * { System.out.println("Number : " + i + Thread.currentThread()); } };
         */

        //Using Method reference
        RunnableThreadTest rt = new RunnableThreadTest();
        Runnable rb = rt::m1;
        Thread th = new Thread(rb);
        th.start();
    }

    public void m1() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Using Method reference : " + i + Thread.currentThread());
        }
    }
}
