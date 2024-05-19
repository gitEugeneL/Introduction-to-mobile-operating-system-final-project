package com.example.finaltask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MazeGenerator {
    public static int SIZE = 5;
    private static final int START = 28;
    private static final int FINISH = 0;

    private static final int[][] DIRECTIONS = {
            {1, 0}, // down
            {-1, 0}, // up
            {0, 1}, // right
            {0, -1} // left
    };

    public static int[][] generateArray() {
        int[][] array = new int[SIZE][SIZE];
        Random rand = new Random();

        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= START; i++) {
            if (i != 16)
                numbers.add(i);
        }
        Collections.shuffle(numbers);

        int posStartRow = rand.nextInt(SIZE);
        int posStartCol = rand.nextInt(SIZE);
        int posFinishRow = rand.nextInt(SIZE);
        int posFinishCol = rand.nextInt(SIZE);

        while (posStartRow == posFinishRow && posStartCol == posFinishCol) {
            posFinishRow = rand.nextInt(SIZE);
            posFinishCol = rand.nextInt(SIZE);
        }

        array[posStartRow][posStartCol] = START;
        array[posFinishRow][posFinishCol] = FINISH;

        int index = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if ((i != posStartRow || j != posStartCol) && (i != posFinishRow || j != posFinishCol)) {
                    array[i][j] = numbers.get(index % numbers.size());
                    index++;
                }
            }
        }

        while (!isPathValid(array, posStartRow, posStartCol, new boolean[SIZE][SIZE])) {
            Collections.shuffle(numbers);
            index = 0;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if ((i != posStartRow || j != posStartCol) && (i != posFinishRow || j != posFinishCol)) {
                        array[i][j] = numbers.get(index % numbers.size());
                        index++;
                    }
                }
            }
        }
        return array;
    }

    private static boolean isPathValid(int[][] array, int row, int col, boolean[][] visited) {
        if (array[row][col] == 0)
            return true;

        visited[row][col] = true;
        int moves = determineNextMove(array[row][col]);

        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            if (isValidMove(newRow, newCol, moves, direction) && !visited[newRow][newCol]) {
                if (isPathValid(array, newRow, newCol, visited)) {
                    return true;
                }
            }
        }
        visited[row][col] = false;
        return false;
    }

    private static boolean isValidMove(int newRow, int newCol, int moves, int[] direction) {
        if (newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE)
            return false;
        if (direction[0] == 1 && (moves & 0b1000) != 0)
            return true;  // down
        if (direction[0] == -1 && (moves & 0b0100) != 0)
            return true; // up
        if (direction[1] == 1 && (moves & 0b0010) != 0)
            return true;  // right
        if (direction[1] == -1 && (moves & 0b0001) != 0)
            return true; // left
        return false;
    }

    private static int determineNextMove(int number) {
        return number & 0b1111;
    }
}
