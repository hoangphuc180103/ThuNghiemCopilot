package com.example.thunghiemcopilot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextTitle = findViewById(R.id.editTextTitle);
        EditText editTextDescription = findViewById(R.id.editTextDescription);
        EditText editTextStatus = findViewById(R.id.editTextStatus);
        EditText editTextPrice = findViewById(R.id.editTextPrice);
        Button buttonSave = findViewById(R.id.buttonSaveProduct);
        Button buttonBack = findViewById(R.id.buttonBack);

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String title = editTextTitle.getText().toString().trim();
            String description = editTextDescription.getText().toString().trim();
            String status = editTextStatus.getText().toString().trim();
            String priceStr = editTextPrice.getText().toString().trim();
            if (name.isEmpty() || title.isEmpty() || description.isEmpty() || status.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show();
                return;
            }
            Product product = new Product(name, title, description, status, price);
            ProductDatabaseHelper dbHelper = new ProductDatabaseHelper(this);
            dbHelper.addProduct(product);
            Toast.makeText(this, "Product saved!", Toast.LENGTH_SHORT).show();
            finish();
        });

        buttonBack.setOnClickListener(v -> finish());
    }
}


