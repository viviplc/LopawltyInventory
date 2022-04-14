package com.example.lopawltyinventory.Helpers;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.lopawltyinventory.R;

/*
 * Below code is taken by Dunumalage Fernando's Assignment 2 Code for the CustomDialog class
 * */

public class CustomDialog extends DialogFragment {
    private String message; // class variable to hold the message in the dialog

    //constructor to set the message for the dialog
    public CustomDialog(String message) {
        this.message = message;
    }

    //overriding the onCreateDialog for custom dialog functionality
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder((getActivity())); // creating a builder instance

        //setting the message of the builder as well as the positive button text
        builder.setMessage(this.message).setPositiveButton(getText(R.string.positive_dialog_button_text), (dialog, id) -> {
            //nothing needed here
        });

//        builder.setNegativeButton(getText(R.string.negative_dialog_button_text), (dialog, id) -> {
//            //nothing needed here
//        });

        return builder.create(); // returning the created builder
    }

    //static method to show a custom dialog
    public static void Show(FragmentManager manager, String message){
        CustomDialog dialog = new CustomDialog(message);
        dialog.show(manager, "test");
    }

    //getter and setter methods for private message property of class
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}