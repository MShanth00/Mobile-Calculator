package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentNumber = "";
    private String firstOperand = "";
    private String secondOperand = "";
    private String operator = "";
    private boolean isOperatorSet = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the display TextView
        display = findViewById(R.id.display);

        // Set up click listeners for buttons
        setNumberButtonListeners();
        setOperatorButtonListeners();
        setSpecialButtonListeners();
    }

    private void setNumberButtonListeners() {
        int[] numberButtonIds = {
                R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3,
                R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
                R.id.btn_8, R.id.btn_9, R.id.btn_dot
        };

        View.OnClickListener listener = v -> {
            Button button = (Button) v;
            String value = button.getText().toString();

            if (isOperatorSet) {
                secondOperand += value;
            } else {
                firstOperand += value;
            }

            currentNumber += value;
            display.setText(currentNumber);
        };

        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorButtonListeners() {
        int[] operatorButtonIds = {
                R.id.btn_add, R.id.btn_subtract,
                R.id.btn_multiply, R.id.btn_divide
        };

        View.OnClickListener listener = v -> {
            Button button = (Button) v;
            String value = button.getText().toString();

            if (!isOperatorSet && !firstOperand.isEmpty()) {
                operator = value;
                isOperatorSet = true;
                currentNumber += " " + value + " ";
                display.setText(currentNumber);
            }
        };

        for (int id : operatorButtonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setSpecialButtonListeners() {
        findViewById(R.id.btn_clear).setOnClickListener(v -> clearCalculator());

        findViewById(R.id.btn_equals).setOnClickListener(v -> {
            if (isOperatorSet && !secondOperand.isEmpty()) {
                double result = calculateResult();
                display.setText(String.valueOf(result));
                resetCalculator(result);
            }
        });
    }

    private double calculateResult() {
        double num1 = Double.parseDouble(firstOperand);
        double num2 = Double.parseDouble(secondOperand);
        double result = 0.0;

        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "−":
                result = num1 - num2;
                break;
            case "×":
                result = num1 * num2;
                break;
            case "÷":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    display.setText("Error: Division by zero");
                }
                break;
        }
        return result;
    }

    private void clearCalculator() {
        currentNumber = "";
        firstOperand = "";
        secondOperand = "";
        operator = "";
        isOperatorSet = false;
        display.setText("0");
    }

    private void resetCalculator(double result) {
        currentNumber = String.valueOf(result);
        firstOperand = String.valueOf(result);
        secondOperand = "";
        operator = "";
        isOperatorSet = false;
    }

}