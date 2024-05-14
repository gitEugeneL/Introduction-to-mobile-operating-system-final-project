package com.example.finaltask;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Maze {
    private static final int[] data = { 10, 8, 10, 9, 28, 1, 0, 12, 12, 10, 9, 13, 6, 5, 6, 5};

    public static int[] getData() {
        Set<Integer> uniqueNumbers = new HashSet<>();
        for (int num : data)
            uniqueNumbers.add(num);

        int[] sortedData = new int[uniqueNumbers.size()];
        int i = 0;
        for (int num : uniqueNumbers)
            sortedData[i++] = num;

        Arrays.sort(sortedData);
        return sortedData;
    }
}
