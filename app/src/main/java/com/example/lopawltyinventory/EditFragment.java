package com.example.lopawltyinventory;

import android.database.Cursor;
import android.opengl.Visibility;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.lopawltyinventory.Helpers.CustomDialog;
import com.example.lopawltyinventory.Helpers.DatabaseHelper;
import com.example.lopawltyinventory.Models.Product;

import java.util.HashMap;
import java.util.Map;

public class EditFragment extends Fragment {

    private ScrollView editFormScrollView; // ref to edit form scrollView
    private HashMap<String, String> categoryMapper = new HashMap<String, String>();  //hashmap to store mapping of category labels to category internal codes

    //references to the editTexts and textView
    private EditText productIdEditText;
    private EditText productNameEditText;
    private EditText productPriceEditText;
    private EditText productDescriptionEditText;
    private AutoCompleteTextView productCategoryTextView;
    private EditText productQuantityEditText;
    private EditText addressEditText;
    private EditText postalCodeEditText;

    private int currentProductIdBeingEdited = 0; // this is the id of the product the user is editing, by default it is 0

    public EditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);  // Inflate the layout for this fragment
        setupViewReferences(view); // set the edit texts / text views references to the class variables
        setupCategoryDropdownMenu(view); // populate the category items dropdown menu
        setupFindButtonClickListener(view); //setup the find button onclick listener
        setupEditButtonClickListener(view); //setup the edit button onclick listener
        editFormScrollView.setVisibility(View.GONE); // hide the scroll view in the beginning, we want to show this only when user has entered a vallid product id
        return view;
    }

    //user defined function to set the references of each of the form elements such as edit texts
    private void setupViewReferences(View mainView){

        //set the references to the editTexts. scrollviews, and textView
        productIdEditText = (EditText) mainView.findViewById(R.id.productIdEditText);
        productNameEditText = (EditText) mainView.findViewById(R.id.productNameEditText);
        productPriceEditText = (EditText) mainView.findViewById(R.id.productPriceEditText);
        productDescriptionEditText = (EditText) mainView.findViewById(R.id.productDescriptionEditText);
        productCategoryTextView = (AutoCompleteTextView) mainView.findViewById(R.id.productCategoryAutoCompleteTextView);
        productQuantityEditText = (EditText) mainView.findViewById(R.id.productQuantityEditText);
        addressEditText = (EditText) mainView.findViewById(R.id.productAddressEditText);
        postalCodeEditText = (EditText) mainView.findViewById(R.id.productPostalCodeEditText);
        editFormScrollView = (ScrollView) mainView.findViewById(R.id.editProductFormScrollView);
    }

    //method to setup the onclick listener for the find button
    private void setupFindButtonClickListener(View mainView){
        Button findButton = (Button) mainView.findViewById(R.id.editProductFindButton); //reference to the updateButton
        findButton.setOnClickListener(new View.OnClickListener() { //setting the onclick listener for the update button

            @Override
            public void onClick(View view) {

                //get the string value of each of the above edit texts and textviews
                String productIdString = productIdEditText.getText().toString();

                //boolean that represents true if all the fields are filled based on length and values or else it is false
                Boolean isDataValidated = (productIdString.length() > 0);
                    if (isDataValidated) {
                        int productId = Integer.parseInt(productIdString);
                        //getting the database helper to run the insert query method
                        DatabaseHelper db = new DatabaseHelper(getContext());

                        Cursor findProductByIdCursor = db.searchDataById(productId);
                        //call the insert grade method which will attempt to run the insert query and return true if successful else false
                        if (findProductByIdCursor.getCount() > 0) {
                            //empty the textviews and edit texts if the insert is successful
                            setProductFromCursor(findProductByIdCursor); // set the product form data in text view from data from db
                            currentProductIdBeingEdited = productId; // set the current id edited
                            editFormScrollView.setVisibility(View.VISIBLE); // show the scroll view as we have data in it now
                            editFormScrollView.fullScroll(ScrollView.FOCUS_UP); //focus scrolling to the top of scrollview

                        } else {
                            CustomDialog.Show(getParentFragmentManager(), getString(R.string.errorNoProductWithId)); //display error message
                            if(currentProductIdBeingEdited != 0) {
                                productIdEditText.setText(String.valueOf(currentProductIdBeingEdited)); // revert the id in the edit text back to prev
                            }
                        }
                    } else {
                        CustomDialog.Show(getParentFragmentManager(), getString(R.string.errorFieldError)); //display error message
                        if(currentProductIdBeingEdited != 0) {
                            productIdEditText.setText(String.valueOf(currentProductIdBeingEdited)); // revert the id in the edit text back to prev
                        }
                    }
            }
        });
    }

    // method to set each of the textviews and the category to the product data from the cursor from db
    private void setProductFromCursor(Cursor cursor){
        if (cursor.moveToFirst()){
            productNameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL2)));
            productQuantityEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL3)));
            productPriceEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL4)));
            String productCategory = getProductCategoryLabelFromCode(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL5)));
            productCategoryTextView.setText(productCategory, false);
            productDescriptionEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL6)));
            addressEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL7)));
            postalCodeEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL8)));
        }
    }

    //method to get the category label from the internal product category string
    private String getProductCategoryLabelFromCode(String code){
        for (Map.Entry<String, String> entry : categoryMapper.entrySet()) {
            if (entry.getValue().equals(code)) {
                return entry.getKey();

            }
        }
        return "";
    }


    //method to setup the onclick listener for the edit button
    private void setupEditButtonClickListener(View mainView){
        Button updateButton = (Button) mainView.findViewById(R.id.updateButton); //reference to the updateButton
        updateButton.setOnClickListener(new View.OnClickListener() { //setting the onclick listener for the update button

            @Override
            public void onClick(View view) {

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
                        newProduct.setId(currentProductIdBeingEdited);
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
                        if (db.UpdateProduct(newProduct)) {
                            CustomDialog.Show(getParentFragmentManager(), getString(R.string.successEditedRecord)); //display success message

                            //empty the textviews and edit texts if the insert is successful
                            productIdEditText.setText("");
                            productNameEditText.setText("");
                            productNameEditText.setText("");
                            productPriceEditText.setText("");
                            productDescriptionEditText.setText("");
                            productCategoryTextView.setText("");
                            productQuantityEditText.setText("");
                            addressEditText.setText("");
                            postalCodeEditText.setText("");

                            currentProductIdBeingEdited = 0; //revert the edited id back to 0
                            editFormScrollView.setVisibility(View.GONE); // hide the scroll view again, we want to show this only when user has entered a new valid product id


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

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.menu_item, R.id.menuItemTextView, categoryMapper.keySet().toArray(new String[0])); //create array adapter with categories key (label) array
        productCategoryTextView.setAdapter(adapter); //set the adapter of the autocompletetextview to adapter
    }
}