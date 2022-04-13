package com.example.lopawltyinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    //objects definition
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciation the objects
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //call the menu options
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        //link the drawer with the menu options
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Read selection of the menu drawer
        setNavigationDrawer();
    }

    //Method to read the selection of the customer and inflated the fragment depending on the menu item
    private void setNavigationDrawer() {
        //Navigation menu instantiation
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        //nav menu listener
        navView.setNavigationItemSelectedListener(item -> {
            //Fragment to be exposed definition // to instantiate when selecting and option
            Fragment fragment = null;
            int itemId = item.getItemId();
            if(itemId == R.id.nav_insert){
                fragment = new InsertFragment();
            } else if (itemId == R.id.nav_list){
                fragment = new ListFragment();
            } else if (itemId == R.id.nav_edit){
                fragment = new EditFragment();
            } else if (itemId == R.id.nav_delete){
                fragment = new DeleteFragment();
            }

            //Logic to make transition to new fragment view
            if(fragment != null){ //Validate a selection from user
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment); //Replace the content on frame section on view to the fragment desired
                transaction.commit(); //submit transaction
                drawerLayout.closeDrawers(); //close the navigation menu
                return true;
            }
            return false;
        });
    }

    //Method called whenever an item in menu is selected.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}