package com.example.lopawltyinventory;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lopawltyinventory.Models.Product;

public class ProductDetailActivity extends AppCompatActivity {
    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        product = (Product) getIntent().getSerializableExtra("PRODUCT");
        setTestProductTextView();
    }

    private void setTestProductTextView() {
        TextView textView = (TextView) findViewById(R.id.productInfoTest);
        textView.setText(product.toString());
    }

}