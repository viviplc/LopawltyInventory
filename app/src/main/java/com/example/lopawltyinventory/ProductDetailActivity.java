package com.example.lopawltyinventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lopawltyinventory.Models.Product;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class ProductDetailActivity extends FragmentActivity implements OnMapReadyCallback {
    private Product product;
    private GoogleMap mMap;

    //Define View components
    TextView prodName, prodId, prodQty, prodPrice, prodCat, prodAdd, ProdPostal, prodDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        product = (Product) getIntent().getSerializableExtra("PRODUCT"); // get the product object from the intent from the main view
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map); // get the map fragment
        mapFragment.getMapAsync(this); // get the map by calling the get map async
        setTestProductTextView(); // set the test product text view
    }

    // REMOVE THIS METHOD LATER - set the product text view to the product string
    private void setTestProductTextView() {
        //instance od the view components
        prodName = (TextView) findViewById(R.id.productName);
        prodId = (TextView) findViewById(R.id.productId);
        prodQty = (TextView) findViewById(R.id.productQty);
        prodPrice = (TextView) findViewById(R.id.productPrice);
        prodCat = (TextView) findViewById(R.id.productCat);
        prodAdd = (TextView) findViewById(R.id.productAdd);
        ProdPostal = (TextView) findViewById(R.id.productPostal);
        prodDes = (TextView) findViewById(R.id.productDes);

        //Set components content
        prodName.setText(product.getName());
        prodId.setText(Integer.toString(product.getId()));
        prodQty.setText(Integer.toString(product.getQuantityInStock()));
        prodPrice.setText("$ " + Double.toString(product.getPrice()));
        prodCat.setText(product.getCategory());
        prodAdd.setText(product.getAddress());
        ProdPostal.setText(product.getPostalCode());
        prodDes.setText(product.getDescription());
    }

    //on map ready to handle the method when the map is ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //setting the map zoom max and min references
        mMap.setMaxZoomPreference(10.0f);
        mMap.setMinZoomPreference(15.0f);

        //geocoder instance to convert text postal code address to latitude and longitude
        Geocoder geocoder = new Geocoder(this);
        try {
            //get the addresses mapped to postal code
            List<Address> addresses = geocoder.getFromLocationName(product.getPostalCode() + ", Canada", 1);
            if (addresses != null && !addresses.isEmpty()) { // if an address is available
                Address address = addresses.get(0); // get the first address in the address list
                // Use the address as needed
                LatLng postalCodeLocation = new LatLng(address.getLatitude(), address.getLongitude()); // create a latitude amd longitude object
                mMap.addMarker(new MarkerOptions().position(postalCodeLocation).title(product.getName())); // add the location marker on the map with the title of the marker as the product name
                mMap.moveCamera(CameraUpdateFactory.newLatLng(postalCodeLocation)); // move the camera of the map
            } else {
                // Display appropriate message when Geocoder services are not available
                Toast.makeText(this, "Unable to geocode zipcode", Toast.LENGTH_LONG).show(); // message when geocoder cannot find lat and long linked to postal code as it is not available
            }
        } catch (IOException e) {
            // handle exception
        }
    }
}