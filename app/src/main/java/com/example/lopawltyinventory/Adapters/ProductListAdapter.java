package com.example.lopawltyinventory.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lopawltyinventory.Models.Product;
import com.example.lopawltyinventory.ProductDetailActivity;
import com.example.lopawltyinventory.R;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    //List to populate
    private List<Product> productLst;

    //Context definitions
    Resources resources;
    String packageName;


    //Constructor
    public ProductListAdapter(List<Product> productLst, Context context) {
        super();
        //Set resources and package name from context
        resources = context.getResources();
        packageName = context.getPackageName();
        //bring content of products on the adapter
        this.productLst = productLst;
    }

    //Inner class ViewHolder to display multiple fields per row
    class ViewHolder extends RecyclerView.ViewHolder {
        //Attributes definition for inner view
        public TextView prodId, prodName, prodQty;
        public ImageView prodImg;

        // constructor
        public ViewHolder(@NonNull View itemView) {
            //Recycler.ViewHolder constructor call
            super(itemView);
            //set attributes on the view
            prodId = (TextView) itemView.findViewById(R.id.txtProductId);
            prodName = (TextView) itemView.findViewById(R.id.txtProductName);
            prodQty = (TextView) itemView.findViewById(R.id.txtProductQty);
            prodImg = (ImageView) itemView.findViewById(R.id.productImg);
            setupSeeMoreButtonOnClickHandler(itemView);
        }

        // method to setup the see more button click handler for every element in the product list
        private void setupSeeMoreButtonOnClickHandler(View mainView){
            Button seeMoreButton = (Button) mainView.findViewById(R.id.seeMoreButton);
            seeMoreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition(); //get the position of the adapter
                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION) { // if the position exists
                        Product selectedProduct = productLst.get(pos); // get the product object at that index
                        startProductDetailIntent(mainView, selectedProduct); // start the intent from the current view
                    }
                }
            });
        }

        //method to start an intent to the product detail view and send a product object to that view
        private void startProductDetailIntent(View mainView, Product product){
            Intent intent = new Intent(mainView.getContext(), ProductDetailActivity.class); // create an intent
            intent.putExtra("PRODUCT", product); // put the product object into the intent to send it to the product detail view
            mainView.getContext().startActivity(intent); // start the intent
        }

    }

    //Method to show the viewHolder (list row)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating view on the view list fragment for each row
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_record, parent, false);
        //Linking view holder to the view inflated
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    //Method to relate a full record from student list to the view holder fields
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //identify the product record to print on view
        Product product = productLst.get(position);
        //Set product record data on each field per row
        ((ViewHolder) holder).prodId.setText(Integer.toString(product.getId()));
        ((ViewHolder) holder).prodName.setText(product.getName());
        ((ViewHolder) holder).prodQty.setText(Integer.toString(product.getQuantityInStock()));

        //Set the image dynamically
        String uri = "@drawable/"+product.getCategory();
        int imageResource = resources.getIdentifier(uri, null, packageName);
        Drawable res = resources.getDrawable(imageResource);
        ((ViewHolder) holder).prodImg.setImageDrawable(res);
    }

    //Method to return the size of the student list to display
    @Override
    public int getItemCount() {
        return productLst.size();
    }
}
