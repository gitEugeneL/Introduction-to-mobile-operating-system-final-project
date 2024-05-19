package com.example.finaltask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private int[][] data;
    private int[] position;
    private String binCurrent;
    private boolean gameResult;
    private int current, left, right, up, down, stepsCount;

    Button buttonTop, buttonBottom, buttonLeft, buttonRight, buttonRestart;
    TextView mainText, stepsText;
    RelativeLayout mainLayout;

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
        createMap();

        mainText.setText(current + "\n" + binCurrent);
        stepsText.setText("steps: " + stepsCount);
        buttonLeft.setText(left != -1 ? left + "\n" + Maze.getBinaryValue(left) : "wall");
        buttonRight.setText(right != -1 ? right + "\n" + Maze.getBinaryValue(right) : "wall");
        buttonBottom.setText(down != -1 ? down + "\n" + Maze.getBinaryValue(down) : "wall");
        buttonTop.setText(up != -1 ? up + "\n" + Maze.getBinaryValue(up) : "wall");

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
        Intent intent = getIntent();

        boolean generatedGame = intent.getBooleanExtra("generated", false);
        int gameSize = intent.getIntExtra("size", -1);

        mainLayout = findViewById(R.id.relativeLayout);
        buttonRestart = findViewById(R.id.buttonRestart);
        buttonTop = findViewById(R.id.buttonTop);
        buttonBottom = findViewById(R.id.buttonBottom);
        buttonLeft = findViewById(R.id.buttonLeft);
        buttonRight = findViewById(R.id.buttonRight);
        mainText = findViewById(R.id.textViewCenter);
        stepsText = findViewById(R.id.steps);

        if (generatedGame) {
            if (gameSize < 2 || gameSize > 7)
                MazeGenerator.SIZE = 5;
            else
                MazeGenerator.SIZE = gameSize;

            data = MazeGenerator.generateArray();
        }
        else {
            data = Maze.getData();
        }

        current = Maze.getStart(data);
        position = Maze.findInitPosition(data, current);

        update();
        updateUI();

        buttonRestart.setOnClickListener(v -> {
            Intent restartIntent = new Intent(this, HelloActivity.class);
            startActivity(restartIntent);
        });

        buttonLeft.setOnClickListener(v -> {
            if (binCurrent.charAt(3) != '1') {
                gameResult = false;
                finishGame();
            }

            if (position[1] > 0) {
                position[1]--;
                stepsCount++;
                update();
                updateUI();
            }
            isWin();
        });

        buttonRight.setOnClickListener(v -> {
            if (binCurrent.charAt(2) != '1') {
                gameResult = false;
                finishGame();
            }

            if (position[1] < data.length - 1) {
                position[1]++;
                stepsCount++;
                update();
                updateUI();
            }
            isWin();
        });

        buttonTop.setOnClickListener(v -> {
            if (binCurrent.charAt(1) != '1') {
                gameResult = false;
                finishGame();
            }

            if (position[0] > 0) {
                position[0]--;
                stepsCount++;
                update();
                updateUI();
            }
            isWin();
        });

        buttonBottom.setOnClickListener(v -> {
            if (binCurrent.charAt(0) != '1') {
                gameResult = false;
                finishGame();
            }

            if (position[0] < data.length - 1) {
                position[0]++;
                stepsCount++;
                update();
                updateUI();
            }
            isWin();
        });
    }

    private void finishGame() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("steps", stepsCount);
        intent.putExtra("result", gameResult);
        startActivity(intent);
    }

    private void isWin() {
        if (Objects.equals(binCurrent, "0000")) {
            gameResult = true;
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("steps", stepsCount);
            intent.putExtra("result", true);
            startActivity(intent);
        }
    }

    private void createMap() {
        LinearLayout mapBlock = new LinearLayout(this);
        mapBlock.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams mainLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        mainLayoutParams.addRule(RelativeLayout.BELOW, mainText.getId());
        mainLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mainLayoutParams.setMargins(0, 120, 0, 0);
        mapBlock.setLayoutParams(mainLayoutParams);

        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);

        for (int i = 0; i < data.length; i++) {
            LinearLayout rowLayout = new LinearLayout(this);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            for (int j = 0; j < data[i].length; j++) {
                TextView textView = new TextView(this);
                textView.setWidth(150);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(16);
                textView.setPadding(8, 8, 12, 12);
                textView.setText(String.valueOf(data[i][j]));
                textView.setBackgroundColor(Color.WHITE);

                if (i == position[0] && j == position[1])
                    textView.setBackgroundColor(Color.RED);

                rowLayout.addView(textView);
            }
            mapBlock.addView(rowLayout);
        }
        relativeLayout.addView(mapBlock);
    }
}