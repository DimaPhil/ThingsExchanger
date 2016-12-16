package ru.ctddev.ifmo.year2013.foodsharing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DonateFoodActivity extends BaseActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donate_food_swipe);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            super.setUsername(user.getDisplayName());
            super.setPhotoUrl(user.getPhotoUrl());
        }
        initDrawer();
    }

    public void donateClick(View view) {
        DatabaseReference donationsRef = FirebaseDatabase.getInstance().getReference("donations");
        Donation current_donation = parse();
        // User entered invalid information
        if (current_donation == null || !validateDonation(current_donation)) {
            return;
        }
        donationsRef.push().setValue(current_donation);
        launchConfirmationDialog();
    }

    private Boolean validateDonation(Donation current_donation) {
        if (current_donation.getAddress() == null || current_donation.getAddress().isEmpty()) {
            launchErrorDialog("Address is required");
            return false;
        } else if (current_donation.getDescription() == null || current_donation.getDescription().isEmpty()) {
            launchErrorDialog("Description is required");
            return false;
        } /*else if (current_donation.getLatitude() == 100 || current_donation.getLongitude() == 100) {
            launchErrorDialog("Invalid address entered");
            return false;
        } */else {
            return true;
        }
    }

    private void launchErrorDialog(String errorMsg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(errorMsg)
                .setTitle(R.string.error);

        builder.setPositiveButton(R.string.button_ok_text, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public Donation parse() {
        EditText food_description_view = (EditText) findViewById(R.id.donate_food_description);
        CheckedTextView veg_view = (CheckedTextView) findViewById(R.id.donate_veg_checkbox);
        CheckedTextView allergens_view = (CheckedTextView) findViewById(R.id.donate_allergen_checkbox);
        EditText address_view = (EditText) findViewById(R.id.donate_address);
        EditText quantity_view = (EditText) findViewById(R.id.donate_quantity);
        EditText expiration_date_view = (EditText) findViewById(R.id.donate_expiry);
        EditText optional_info_view = (EditText) findViewById(R.id.donate_optional_info);

        String food_description = food_description_view.getText().toString();
        Boolean veg = veg_view.isChecked();
        Boolean allergens = allergens_view.isChecked();
        String address = address_view.getText().toString();
        String quantityStr = quantity_view.getText().toString();
        int quantity;
        if (quantityStr == null || quantityStr.isEmpty()) {
            quantity = 1;
        } else {
            quantity = Integer.parseInt(quantityStr);
        }
        String optional_info = optional_info_view.getText().toString();
        String expiration_date = expiration_date_view.getText().toString();
        double longitude = getLongitude(address);
        double latitude = getLatitude(address);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String display_name;
        if (currentUser == null) {
            display_name = "Anonymous";
        } else {
            display_name = currentUser.getDisplayName();
        }
        return new Donation(veg, allergens, address, optional_info, food_description, latitude, longitude, quantity, display_name, expiration_date);
    }

    private double getLongitude(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        Double longitude = (double) 0;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null || address.isEmpty()) {
                return 100; // range of longitude 0-(+/-)90
            }
            Address location = address.get(0);
            longitude = location.getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return longitude;
    }

    private double getLatitude(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        Double latitude = (double) 0;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null || address.isEmpty()) {
                return 100; // range of latitude 0-(+/-)90
            }
            Address location = address.get(0);
            latitude = location.getLatitude();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return latitude;
    }

    public void launchConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.home, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(DonateFoodActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void checkBoxListener(View view) {
        CheckedTextView ctv = (CheckedTextView) view;

        if (ctv.isChecked()) {
            ctv.setChecked(false);
        } else {
            ctv.setChecked(true);
        }
    }

    public void getLocation(View view) {

        //region Get Location
        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
        // Check permissions
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);
        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();
        //endregion

        // Convert lat/long to string
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();

            // Set text on form
            EditText address_view = (EditText) findViewById(R.id.donate_address);
            address_view.setText(address + " " + city + " " + country + " " + postalCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
