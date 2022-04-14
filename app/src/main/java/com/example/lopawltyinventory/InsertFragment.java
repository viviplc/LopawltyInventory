package com.example.lopawltyinventory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.lopawltyinventory.Helpers.CustomDialog;
import com.example.lopawltyinventory.Helpers.DatabaseHelper;
import com.example.lopawltyinventory.Models.Product;

import java.util.HashMap;

public class InsertFragment extends Fragment {

    //hashmap to store mapping of category labels to category internal codes
    HashMap<String, String> categoryMapper = new HashMap<String, String>();

     public InsertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert, container, false); //set the view by inflating from layout to view
        setupCategoryDropdownMenu(view); // populate the category items dropdown menu
        setupInsertButtonClickListener(view); // create and handle the onclick listener for when the insert button is clicked
        return view;
    }

    private void setupInsertButtonClickListener(View mainView){
        Button insertButton = (Button) mainView.findViewById(R.id.insertButton); //reference to the insertButton
        insertButton.setOnClickListener(new View.OnClickListener() { //setting the onclick listener for the submit button

            @Override
            public void onClick(View view) {

                //references to the editTexts and textView
                EditText productNameEditText = (EditText) mainView.findViewById(R.id.productNameEditText);
                EditText productPriceEditText = (EditText) mainView.findViewById(R.id.productPriceEditText);
                EditText productDescriptionEditText = (EditText) mainView.findViewById(R.id.productDescriptionEditText);
                AutoCompleteTextView productCategoryTextView = (AutoCompleteTextView) mainView.findViewById(R.id.productCategoryAutoCompleteTextView);
                EditText productQuantityEditText = (EditText) mainView.findViewById(R.id.productQuantityEditText);
                EditText addressEditText = (EditText) mainView.findViewById(R.id.productAddressEditText);
                EditText postalCodeEditText = (EditText) mainView.findViewById(R.id.productPostalCodeEditText);

                //get the string value of each of the above edit texts and textviews
                String productName = productNameEditText.getText().toString();
                String productPriceString = productPriceEditText.getText().toString();
                String productDescription = productDescriptionEditText.getText().toString();
                String productCategoryLabelString = productCategoryTextView.getText().toString();
                String productCategory = categoryMapper.get(productCategoryLabelString); // get the category internal code from the label string using the hashmap
                String productQuantityString = productQuantityEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String postalCode = postalCodeEditText.getText().toString();

                //boolean that represents true if all the fields are filled based on length and values or else it is false
                Boolean isDataValidated = (productName.length() > 0) && (productPriceString.length() > 0) && (productDescription.length() > 0) && (productCategory.length() > 0) && (productCategory.length() > 0) && (productQuantityString.length() > 0) && (address.length() > 0) && (postalCode.length() > 0);
                try {
                    if (isDataValidated) {
                        Double productPrice = Double.parseDouble(productPriceString);
                        int productQuantity = Integer.parseInt(productQuantityString);
                        Product newProduct = new Product(); //creating a new object of Product, empty constructor as I am setting the data using the setters

                        //setting the fields of the grade to the values from the form with the correct formats
                        newProduct.setId(0); //setting id to 0 as we will be auto incrementing
                        newProduct.setName(productName);
                        newProduct.setPrice(productPrice);
                        newProduct.setDescription(productDescription);
                        newProduct.setCategory(productCategory);
                        newProduct.setQuantityInStock(productQuantity);
                        newProduct.setAddress(address);
                        newProduct.setPostalCode(postalCode);

                        //getting the database helper to run the insert query method
                        DatabaseHelper db = new DatabaseHelper(getContext());

                        //call the insert grade method which will attempt to run the insert query and return true if successful else false
                        if (db.InsertProduct(newProduct)) {
                            CustomDialog.Show(getParentFragmentManager(), getString(R.string.successAddedRecord)); //display success message

                            //empty the textviews and edit texts if the insert is successful
                            productNameEditText.setText("");
                            productNameEditText.setText("");
                            productPriceEditText.setText("");
                            productDescriptionEditText.setText("");
                            productCategoryTextView.setText("");
                            productQuantityEditText.setText("");
                            addressEditText.setText("");
                            postalCodeEditText.setText("");

                        } else {
                            CustomDialog.Show(getParentFragmentManager(), getString(R.string.errorDb)); //display error message
                        }
                    } else {
                        CustomDialog.Show(getParentFragmentManager(), getString(R.string.errorFieldError)); //display error message
                    }
                } catch(Exception e) {
                    CustomDialog.Show(getParentFragmentManager(), getString(R.string.errorFieldError)); //display error message
                }
            }
        });
    }

    //user defined function to populate the dropdown menu with the category items
    private void setupCategoryDropdownMenu(View view){

         //get the category label texts from the string.xml resource file
        String dogCategoryLabel = getString(R.string.dogCategoryLabel);
        String catCategoryLabel = getString(R.string.catCategoryLabel);
        String fishCategoryLabel = getString(R.string.fishCategoryLabel);
        String birdCategoryLabel = getString(R.string.birdCategoryLabel);

        //add the category labels as keys and the corresponding internal category code as a value
        categoryMapper.put(dogCategoryLabel, "dogs");
        categoryMapper.put(catCategoryLabel, "cats");
        categoryMapper.put(fishCategoryLabel, "fish");
        categoryMapper.put(birdCategoryLabel, "birds");

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.productCategoryAutoCompleteTextView); //get ref to autocompletetextview
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.menu_item, R.id.menuItemTextView, categoryMapper.keySet().toArray(new String[0])); //create array adapter with categories key (label) array
        autoCompleteTextView.setAdapter(adapter); //set the adapter of the autocompletetextview to adapter
    }


}