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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
    //List to populate from the query without filter
    private List<Product> productsShown = new ArrayList<>();
    //Adapter to set the content on productList
    private ProductListAdapter productListAdapter;
    //DataBaseHelper definition
    DatabaseHelper dataBaseHelper;

    //String Array for search filter
    private ArrayList<String> nameList;
    //Autocomplete textfield definition
    private AutoCompleteTextView search;
    //Clear button definition
    private Button clearBtn;

    public ListFragment() {
        // Required empty public constructor
    }

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        //Autocomplete search field instantiation
        search = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteSearch);
        nameList = new ArrayList<>();
        //Clear button instantiation
        clearBtn = (Button) view.findViewById(R.id.clearBtn);

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

                    nameList.add(product.getId() +"- "+ product.getName());

                } while (cursor.moveToNext()); //will run until the end of the records saved on DB Table Products
            }

            productsShown.addAll(productsList);
            //close cursor
            cursor.close();
            //close data base helper
            dataBaseHelper.close();

            ArrayAdapter<String> searchAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line,nameList);
            search.setAdapter(searchAdapter);

            //Filter Search option enable
            search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    //Hide keyboard
                    InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);

                    //Clear the list to be shown
                    productsShown.clear();
                    String item = adapterView.getItemAtPosition(position).toString();
                    //Set the selected item on the list to display
                    Product prdSelected = findProduct(item);
                    productsShown.add(prdSelected);
                    //update the view everytime
                    productListAdapter.notifyDataSetChanged();
                }

                private Product findProduct(String item) {
                    String[] parts= item.split("-");
                    int index = Integer.parseInt(parts[0]);
                    return productsList.get(index-1);
                }
            });

            //Method to clean the filter and update the view
            clearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Clean the autocomplete textfield
                    search.setText("");
                    //Clear the list to be shown
                    productsShown.clear();
                    //update the view with the original content on the db
                    productsShown.addAll(productsList);
                    //update the view everytime
                    productListAdapter.notifyDataSetChanged();
                }
            });


            bindProductsAdapter();

            return view;

        }
        else { //No content on DB
            Toast.makeText(getContext(), "No records available", Toast.LENGTH_SHORT).show();
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
        productListAdapter = new ProductListAdapter (productsShown, getContext());
        //link the recyclerview to the adapter
        recyclerView.setAdapter(productListAdapter);
        //Notify the adapter of the update with the studentList
        productListAdapter.notifyDataSetChanged();
    }
}