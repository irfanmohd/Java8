package com.datastructure;

public class MostRepeatedInArrays {

    public static void main(String[] args) {

        int[] obj = {3, 2, 3, 8, 7, 8, 9};
        int count = 0;
        int result = 0;
        System.out.println("length-->" + obj.length);
        for (int i = 0; i < obj.length; i++) {
            count = 1;
            for (int j = i + 1; j < obj.length; j++) {

                if (obj[i] == obj[j] && obj[i] != 0) {
                    System.out.println("i-->" + obj[i]);
                    System.out.println("j-->" + obj[j]);
                    count++;
                    obj[j] = 0;
                }

            }
            if (count >= 2) {
                System.out.println(result);
                result++;
            }
        }
        System.out.println("result ---->" + result);
    }

}