package com.example.finaltask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    boolean resultGame;
    int stepsCount;
    TextView resultText;
    Button startAgainButton;

    private void restartGame() {
        Intent intent = new Intent(this, HelloActivity.class);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();

        stepsCount = intent.getIntExtra("steps", -1);
        resultGame = intent.getBooleanExtra("result", false);

        resultText = findViewById(R.id.resultText);
        startAgainButton = findViewById(R.id.startAgainButton);

        if (!resultGame) {
            resultText.setText("You lost! \n" + "steps count: " + stepsCount);
            resultText.setTextColor(Color.RED);
        } else {
            resultText.setText("You win! \n" + "steps count: " + stepsCount);
            resultText.setTextColor(Color.GREEN);
        }

        startAgainButton.setOnClickListener(v -> restartGame());
    }
}