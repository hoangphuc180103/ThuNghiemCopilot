package com.example.thunghiemcopilot;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private ProductAdapter productAdapter;
    private ProductDatabaseHelper dbHelper;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Set navigation icon (3 gạch)
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ProductListActivity.this, toolbar);
                popupMenu.getMenuInflater().inflate(R.menu.menu_product_list, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.menu_profile) {
                            startActivity(new Intent(ProductListActivity.this, ProfileActivity.class));
                            return true;
                        } else if (id == R.id.menu_product) {
                            startActivity(new Intent(ProductListActivity.this, ProductListActivity.class));
                            return true;
                        } else if (id == R.id.menu_logout) {
                            Intent intent = new Intent(ProductListActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        dbHelper = new ProductDatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(this, dbHelper.getAllProducts());
        productAdapter.setProductActionListener(new ProductAdapter.ProductActionListener() {
            @Override
            public void onDeleteProduct(Product product, int position) {
                new AlertDialog.Builder(ProductListActivity.this)
                        .setTitle("Xóa sản phẩm")
                        .setMessage("Bạn có chắc muốn xóa sản phẩm này?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            dbHelper.deleteProduct(product.getId());
                            List<Product> products = dbHelper.getAllProducts();
                            productAdapter.setProductList(products);
                            Toast.makeText(ProductListActivity.this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }

            @Override
            public void onEditProduct(Product product, int position) {
                showEditProductDialog(product);
            }

            @Override
            public void onDetailProduct(Product product, int position) {
                showDetailProductDialog(product);
            }
        });
        recyclerView.setAdapter(productAdapter);

        Button buttonAddProduct = findViewById(R.id.buttonAddProduct);
        buttonAddProduct.setOnClickListener(v -> {
            startActivity(new Intent(ProductListActivity.this, AddProductActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Product> products = dbHelper.getAllProducts();
        productAdapter.setProductList(products);
    }

    private void showEditProductDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chỉnh sửa sản phẩm");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText inputName = new EditText(this);
        inputName.setHint("Tên sản phẩm");
        inputName.setText(product.getName());
        layout.addView(inputName);
        final EditText inputTitle = new EditText(this);
        inputTitle.setHint("Tiêu đề");
        inputTitle.setText(product.getTitle());
        layout.addView(inputTitle);
        final EditText inputDescription = new EditText(this);
        inputDescription.setHint("Mô tả");
        inputDescription.setText(product.getDescription());
        layout.addView(inputDescription);
        final EditText inputStatus = new EditText(this);
        inputStatus.setHint("Trạng thái");
        inputStatus.setText(product.getStatus());
        layout.addView(inputStatus);
        final EditText inputPrice = new EditText(this);
        inputPrice.setHint("Giá");
        inputPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        inputPrice.setText(String.valueOf(product.getPrice()));
        layout.addView(inputPrice);
        builder.setView(layout);
        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            product.setName(inputName.getText().toString());
            product.setTitle(inputTitle.getText().toString());
            product.setDescription(inputDescription.getText().toString());
            product.setStatus(inputStatus.getText().toString());
            try {
                product.setPrice(Double.parseDouble(inputPrice.getText().toString()));
            } catch (Exception e) {
                product.setPrice(0);
            }
            dbHelper.updateProduct(product);
            productAdapter.setProductList(dbHelper.getAllProducts());
            Toast.makeText(this, "Đã cập nhật sản phẩm", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void showDetailProductDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chi tiết sản phẩm");
        String message = "Tên: " + product.getName()
                + "\nTiêu đề: " + product.getTitle()
                + "\nMô tả: " + product.getDescription()
                + "\nTrạng thái: " + product.getStatus()
                + "\nGiá: " + product.getPrice();
        builder.setMessage(message);
        builder.setPositiveButton("Đóng", null);
        builder.show();
    }
}
