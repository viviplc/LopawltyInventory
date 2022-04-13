package com.example.lopawltyinventory;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lopawltyinventory.Adapters.ProductListAdapter;
import com.example.lopawltyinventory.Helpers.DatabaseHelper;
import com.example.lopawltyinventory.Models.Product;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    //recycler view definition
    private RecyclerView recyclerView;
    //List to populate the recycler view with the records of products
    private List<Product> productsList = new ArrayList<>();
    //Adapter to set the content on productList
    private ProductListAdapter productListAdapter;
    //DataBaseHelper definition
    DatabaseHelper dataBaseHelper;


    public ListFragment() {
        // Required empty public constructor
    }

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        //recycler view instantiation
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //Instance of DataBaseHelper
        dataBaseHelper = new DatabaseHelper(getActivity());
        //Cursor definition to get the student table content
        Cursor cursor = dataBaseHelper.viewData();
        if(cursor!=null && cursor.getCount() > 0)
        {
            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();
                    //Set student object with the info from the cursor, record from DB
                    product.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                    product.setName(cursor.getString(cursor.getColumnIndex("Name")));
                    product.setQuantityInStock(cursor.getInt(cursor.getColumnIndex("QuantityInStock")));
                    product.setPrice(cursor.getDouble(cursor.getColumnIndex("Price")));
                    product.setCategory(cursor.getString(cursor.getColumnIndex("Category")));
                    product.setDescription(cursor.getString(cursor.getColumnIndex("Description")));
                    product.setAddress(cursor.getString(cursor.getColumnIndex("Address")));
                    product.setPostalCode(cursor.getString(cursor.getColumnIndex("PostalCode")));

                    //add the product to the list of products
                    productsList.add(product);
                } while (cursor.moveToNext()); //will run until the end of the records saved on DB Table Products
            }
            //close cursor
            cursor.close();
            //close data base helper
            dataBaseHelper.close();

            //Call method to link student list with the studentListAdapter
            bindProductsAdapter();

            

            //Return the view with the students list linked to the recycle view
            return view;
        }
        else { //No content on DB
            Toast.makeText(getContext(), "No records available", Toast.LENGTH_SHORT).show();

            //code to test the recycler view
            Product product = new Product(1, "Product Test", 50, 34.5, "cats", "Product to test", "Address to test", "ASD 456");
            Product product2 = new Product(2, "Product Test 2", 20, 34.5, "dogs", "Product to test", "Address to test", "ASD 456");
            Product product3 = new Product(3, "Product Test 3", 500, 34.5, "fish", "Product to test", "Address to test", "ASD 456");
            Product product4 = new Product(4, "Product Test 4", 203, 34.5, "birds", "Product to test", "Address to test", "ASD 456");
            productsList.add(product);
            productsList.add(product2);
            productsList.add(product3);
            productsList.add(product4);
            bindProductsAdapter();

            return view;
        }
    }

    //method to link adapter which link the viewholder on the recycler view with the list of products
    private void bindProductsAdapter() {
        //manager of the layout for the recycle view which hold the rows of the list
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        //link the layout to the recycleview
        recyclerView.setLayoutManager(layoutManager);
        //instance of the ProductListAdapter sending the list of product and the context of this fragment
        productListAdapter = new ProductListAdapter (productsList, getContext());
        //link the recyclerview to the adapter
        recyclerView.setAdapter(productListAdapter);
        //Notify the adapter of the update with the studentList
        productListAdapter.notifyDataSetChanged();
    }
}