package com.example.finaltask;

import static com.example.finaltask.Maze.*;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int[][] data;
    private String binCurrent;
    private int[] position;
    private int start, finish, current, left, right, up, down;
    Button buttonTop, buttonBottom, buttonLeft, buttonRight;
    TextView mainText;

    private void update() {
        assert position != null;
        current = data[position[0]][position[1]];
        binCurrent = Maze.getBinaryValue(current);
        left = Maze.findLeft(data, position);
        right = Maze.findRight(data, position);
        up = Maze.findUp(data, position);
        down = Maze.findDown(data, position);
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {
        mainText.setText(current + "\n" + binCurrent);
        buttonLeft.setText(left != -1 ? left + "\n" + getBinaryValue(left) : "wall");
        buttonRight.setText(right != -1 ? right + "\n" + getBinaryValue(right) : "wall");
        buttonBottom.setText(down != -1 ? down + "\n" + getBinaryValue(down) : "wall");
        buttonTop.setText(up != -1 ? up + "\n" + getBinaryValue(up) : "wall");

        buttonLeft.setBackgroundColor(Color.BLUE);
        buttonRight.setBackgroundColor(Color.BLUE);
        buttonTop.setBackgroundColor(Color.BLUE);
        buttonBottom.setBackgroundColor(Color.BLUE);

        if (binCurrent.charAt(3) != '1' || left == -1)
            buttonLeft.setBackgroundColor(Color.GRAY);

        if (binCurrent.charAt(2) != '1' || right == -1)
            buttonRight.setBackgroundColor(Color.GRAY);

        if (binCurrent.charAt(1) != '1' || up == -1)
            buttonTop.setBackgroundColor(Color.GRAY);

        if (binCurrent.charAt(0) != '1' || down == -1)
            buttonBottom.setBackgroundColor(Color.GRAY);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonTop = findViewById(R.id.buttonTop);
        buttonBottom = findViewById(R.id.buttonBottom);
        buttonLeft = findViewById(R.id.buttonLeft);
        buttonRight = findViewById(R.id.buttonRight);
        mainText = findViewById(R.id.textViewCenter);

        data = getData();
        start = getStart(data);
        finish = getFinish(data);

        current = start;
        position = Maze.findInitPosition(data, current);

        update();
        updateUI();

        buttonLeft.setOnClickListener(v -> {
            if (binCurrent.charAt(3) != '1') {
                // todo you lost
                return;
            }

            if (position[1] > 0) {
                position[1]--;
                update();
                updateUI();
            }
        });

        buttonRight.setOnClickListener(v -> {
            if (binCurrent.charAt(2) != '1') {
                // todo you lost
                return;
            }

            if (position[1] < data.length - 1) {
                position[1]++;
                update();
                updateUI();
            }
        });

        buttonTop.setOnClickListener(v -> {
            if (binCurrent.charAt(1) != '1') {
                // todo you lost
                return;
            }

            if (position[0] > 0) {
                position[0]--;
                update();
                updateUI();
            }
        });

        buttonBottom.setOnClickListener(v -> {
            if (binCurrent.charAt(0) != '1') {
                // todo you lost
                return;
            }

            if (position[0] < data.length - 1) {
                position[0]++;
                update();
                updateUI();
            }
        });
    }
}