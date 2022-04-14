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
    private void setupDeleteButtonClickListener(View mainView){
        Button DeleteButton = (Button) mainView.findViewById(R.id.DeleteBtn);
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText DeleteIdInput = (EditText) mainView.findViewById(R.id.etdelete);
                String ProductInt = DeleteIdInput.getText().toString();
                int value=0;
                if(!"".equals(ProductInt)){
                    value=Integer.parseInt(ProductInt);
                }
                Button DeleteButton = (Button) mainView.findViewById(R.id.DeleteBtn);
                DatabaseHelper db = new DatabaseHelper(getContext());
   if(db.DeleteProduct(value))
   {
       CustomDialog.Show(getParentFragmentManager(), "Successfully deleted Record" );
   }
   else{
       CustomDialog.Show(getParentFragmentManager(), "Could not delete Record");
   }
            }
        });
    }

}