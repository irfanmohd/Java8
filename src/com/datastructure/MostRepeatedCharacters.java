package com.datastructure;

import java.util.ArrayList;
import java.util.Arrays;

public class MostRepeatedCharacters {

    public static void main(String[] args) {
        // TODO Auto-generated method stub


        String input = "applllpplle";
        int count = 0;
        int max = 0;
        char result = ' ';
        char arr[] = input.toCharArray();
        for (int i = 0; i < input.length(); i++) {
            for (int j = i + 1; j < input.length(); j++) {
                if (arr[i] == arr[j]) {
                    count++;
                }
                if (count > max) {
                    max = count;
                    result = arr[i];
                }
            }
        }
        System.out.println("result ---->" + result);


        ArrayList<Integer> numberList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        numberList.forEach(p -> {
                    //System.out.println(p);
                    //Do more work
                }
        );

    }

}
