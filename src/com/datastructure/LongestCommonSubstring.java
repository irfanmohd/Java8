package com.datastructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LongestCommonSubstring {
    static public void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter String 1: ");
        String s1 = br.readLine();
        System.out.println("Enter String 2: ");
        String s2 = br.readLine();
        printLongestCommonSubstring(s1, s2);
        br.close();
    }

    private static void printLongestCommonSubstring(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int max = 0;
        int pos = 0;

        int[][] array = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    System.out.println(" value of i " + i);
                    System.out.println("equal" + " value of j " + j);
                    if (i == 0 || j == 0) {
                        System.out.println("if");
                        array[i][j] = 1;
                    } else {
                        array[i][j] = array[i - 1][j - 1] + 1;
                        System.out.println(array[i][j]);
                    }
                    if (max < array[i][j]) {
                        max = array[i][j];
                        pos = i + 1;
                        System.out.println("POS::::" + pos);
                    }
                }
            }
        }
        if (pos > 0) {
            System.out.println("Longest common substring between \"" + s1 + "\""
                    + " and \"" + s2 + "\" is: " + s1.substring(pos - max, pos));
        } else {
            System.out.println("There is no common substring between \"" + s1
                    + "\" and \"" + s2 + "\"");
        }
    }
}