package com.example.thunghiemcopilot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewPassword = findViewById(R.id.textViewPassword);
        Button buttonBack = findViewById(R.id.buttonBack);

        // Hiển thị email và password tĩnh
        textViewEmail.setText("Email: user@example.com");
        textViewPassword.setText("Password: 123456");

        buttonBack.setOnClickListener(v -> finish());
    }
}

