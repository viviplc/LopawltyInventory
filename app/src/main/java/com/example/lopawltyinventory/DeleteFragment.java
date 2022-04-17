package com.example.lopawltyinventory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lopawltyinventory.Helpers.CustomDialog;
import com.example.lopawltyinventory.Helpers.DatabaseHelper;

public class DeleteFragment extends Fragment {


    public DeleteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete, container, false);
        setupDeleteButtonClickListener(view); // create and handle the onclick listener for when the insert button is clicked
        return view;
    }

    private void setupDeleteButtonClickListener(View mainView) {
        //instantiate the button from view
        Button DeleteButton = (Button) mainView.findViewById(R.id.DeleteBtn);

        //Event listener
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //instance of the edittext
                EditText DeleteIdInput = (EditText) mainView.findViewById(R.id.etdelete);
                //getting the id from the field
                String ProductInt = DeleteIdInput.getText().toString();
                int value = 0;
                //check that is not empty
                if (!"".equals(ProductInt)) {
                    //parse to integer
                    value = Integer.parseInt(ProductInt);
                }
                //instance of the data base helper
                DatabaseHelper db = new DatabaseHelper(getContext());
                //call the method to delete the product from ths DB and check if the delete is successful
                if (db.DeleteProduct(value)) {
                    //show success dialog
                    CustomDialog.Show(getParentFragmentManager(), "Successfully deleted Record");
                } else {
                    //show failed dialog
                    CustomDialog.Show(getParentFragmentManager(), "Could not delete Record");
                }
            }
        });
    }

}