package com.example.finaltask;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class HelloActivity extends AppCompatActivity {

    Button buttonBasicStart, buttonGeneratedStart;
    EditText inputSizeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hello);

        buttonBasicStart = findViewById(R.id.buttonStartBasic);
        buttonGeneratedStart = findViewById(R.id.buttonStartGenerated);
        inputSizeText = findViewById(R.id.gameSizeInput);

        Intent intent = new Intent(this, MainActivity.class);

        buttonBasicStart.setOnClickListener(v -> {
            startActivity(intent);
        });

        buttonGeneratedStart.setOnClickListener(v -> {
            String inputResult = inputSizeText.getText().toString();
            int size;
            if (!inputResult.isEmpty())
                size = Integer.parseInt(inputResult);
            else
                size = 5;

            intent.putExtra("size", size);
            intent.putExtra("generated", true);
            startActivity(intent);
        });
    }
}