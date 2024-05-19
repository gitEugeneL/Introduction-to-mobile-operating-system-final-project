package com.example.finaltask;
import java.util.Arrays;

public class Maze {
    private static final int[][] baseData = {
            {10, 8, 10, 9},
            {28, 1, 0, 12},
            {12, 10, 9, 13},
            {6, 5, 6, 5}
    };

    public static int[][] getData() {
        return baseData;
    }

    public static int getStart(int[][] data){
        return Arrays.stream(data)
                .flatMapToInt(Arrays::stream)
                .max()
                .orElseThrow(() -> new RuntimeException("array is empty"));
    }

    public static int getFinish(int[][] data){
        return Arrays.stream((data))
                .flatMapToInt(Arrays::stream)
                .min()
                .orElseThrow(() -> new RuntimeException("array is empty"));
    }

    public static String getBinaryValue(int number) {
        return String
                .format("%4s", Integer.toBinaryString(number))
                .replace(' ', '0')
                .substring(Math.max(0, Integer.toBinaryString(number).length() - 4));
    }

    public static int[] findInitPosition(int[][] array, int target) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++)
                if (array[i][j] == target)
                    return new int[]{i, j};
        }
        return null;
    }

    public static int findLeft(int[][] array, int[] position) {
        if (position[1] > 0)
            return array[position[0]][position[1] - 1];
        else
            return -1;
    }

    public static int findRight(int[][] array, int[] position) {
        if (position[1] < array[0].length - 1)
            return array[position[0]][position[1] + 1];
        else
            return -1;
    }

    public static int findDown(int[][] array, int[] position) {
        if (position[0] < array.length - 1)
            return array[position[0] + 1][position[1]];
        else
            return -1;
    }

    public static int findUp(int[][] array, int[] position) {
        if (position[0] > 0)
            return array[position[0] - 1][position[1]];
        else
            return -1;
    }
}
