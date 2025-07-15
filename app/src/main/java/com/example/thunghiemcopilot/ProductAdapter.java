package com.example.thunghiemcopilot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_PRODUCT = 1;
    private List<Product> productList;
    private Context context;

    public interface ProductActionListener {
        void onDeleteProduct(Product product, int position);
        void onEditProduct(Product product, int position);
        void onDetailProduct(Product product, int position);
    }

    private ProductActionListener actionListener;

    public void setProductActionListener(ProductActionListener listener) {
        this.actionListener = listener;
    }

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getItemViewType(int position) {
        if (productList == null || productList.isEmpty()) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_PRODUCT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new EmptyViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
            return new ProductViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyViewHolder) {
            ((EmptyViewHolder) holder).textView.setText("Không có sản phẩm");
        } else if (holder instanceof ProductViewHolder) {
            Product product = productList.get(position);
            ProductViewHolder productHolder = (ProductViewHolder) holder;
            productHolder.textViewProductName.setText(product.getName());
            productHolder.imageViewProduct.setImageResource(R.drawable.ic_product);
            productHolder.buttonDelete.setOnClickListener(v -> {
                if (actionListener != null) {
                    actionListener.onDeleteProduct(product, position);
                }
            });
            productHolder.buttonEdit.setOnClickListener(v -> {
                if (actionListener != null) {
                    actionListener.onEditProduct(product, position);
                }
            });
            // Thêm sự kiện click vào item để xem chi tiết
            productHolder.itemView.setOnClickListener(v -> {
                if (actionListener != null) {
                    actionListener.onDetailProduct(product, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (productList == null || productList.isEmpty()) {
            return 1;
        } else {
            return productList.size();
        }
    }

    // Add this method to allow updating the product list
    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProduct;
        TextView textViewProductName;
        Button buttonEdit, buttonDelete;
        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
