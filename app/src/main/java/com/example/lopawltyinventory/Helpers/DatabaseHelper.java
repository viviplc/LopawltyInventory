package com.example.lopawltyinventory.Helpers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.room.Delete;

import com.example.lopawltyinventory.Models.Product;
import com.example.lopawltyinventory.R;

/*
* Below code is inspired by Dunumalage Fernando's Assignment 2 Code for the DatabaseHelper class
* */

public class DatabaseHelper extends SQLiteOpenHelper {

    // setting database variables and column variables
    public static final String dbName = "LopawltyInventory";
    public static final int version = 1;
    public static final String TABLE_NAME = "Products";
    public static final String COL1="Id";
    public static final String COL2="Name";
    public static final String COL3="QuantityInStock";
    public static final String COL4="Price";
    public static final String COL5="Category";
    public static final String COL6="Description";
    public static final String COL7="Address";
    public static final String COL8="PostalCode";

    //create table and drop table sql query strings
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                                        COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        COL2 + " TEXT NOT NULL, " +
                                        COL3 + " INTEGER NOT NULL, " +
                                        COL4 + " DECIMAL NOT NULL, " +
                                        COL5 + " TEXT NOT NULL, " +
                                        COL6 + " TEXT NOT NULL," +
                                        COL7 + " TEXT NOT NULL," +
                                        COL8 + " TEXT NOT NULL);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    //constructor, this method will be called once an instance of this class is created
    public DatabaseHelper(@Nullable Context context) {
        super(context, dbName, null, version); //calling the super constructor which will create the database
    }

    //method used to run the SQL query that actually creates the table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE); //run SQL to create the table
    }

    //the insert product method will be used to insert a new row to the Products table with using a Product object passed as a parameter of the method
    public boolean InsertProduct(Product product){
        SQLiteDatabase db = this.getWritableDatabase(); //get the instance of the sqlite database in writable format

        ContentValues contentValues = new ContentValues(); //get an instance of ContentValues to insert the values for columns

        //putting the actual data from the product object to each of the specific columns
        contentValues.put(COL2, product.getName());
        contentValues.put(COL3, product.getQuantityInStock());
        contentValues.put(COL4, product.getPrice());
        contentValues.put(COL5, product.getCategory());
        contentValues.put(COL6, product.getDescription());
        contentValues.put(COL7, product.getAddress());
        contentValues.put(COL8, product.getPostalCode());

        //actually run the insertion command on our DB specifying the table name and contentvalues
        long result = db.insert(TABLE_NAME, null, contentValues); //will return -1 if fail, if positive it is successful

        //if result is -1 then return false to method else return true
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean DeleteProduct(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL1 + "=" + id, null) > 0;
    }

    //method to get a cursor to view the Products data
    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase(); // get a readable instance of sqlite database

        //defining cursor variable to SELECT sql query, also ordered by id descending order
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL1 + " DESC", null);

        //return the cursor instance, moving to first if it is null
        if(cursor!= null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    //method to get a cursor to specific Product row by id
    public Cursor searchDataById(int id) {
        SQLiteDatabase db = this.getReadableDatabase(); // get a readable instance of sqlite database

        //defining cursor variable to SELECT sql query, also ordered by id descending order
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + "=" + id, null);

        //return the cursor instance, moving to first if it is null
        if(cursor!= null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    //execution method to upgrade db by deleting existing table and running onCreate again
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_TABLE); // run query to delete existing table
        onCreate(sqLiteDatabase);
    }
}
