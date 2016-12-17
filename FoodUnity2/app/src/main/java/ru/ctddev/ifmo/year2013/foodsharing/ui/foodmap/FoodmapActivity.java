package ru.ctddev.ifmo.year2013.foodsharing.ui.foodmap;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;

import ru.ctddev.ifmo.year2013.foodsharing.R;

public class FoodmapActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodmap);


    }
    protected void onSearchButtonClicked(View v){
        EditText loc = (EditText)findViewById(R.id.search_loc);
        GoogleMap map = ((FoodmapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment)).getMap();

        LatLng loc_on_map = getLocationFromAddress(loc.getText().toString());
        try{
        map.setMyLocationEnabled(true);} catch(SecurityException e){};
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc_on_map, 13));
    }

    public LatLng getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude() ,
                    location.getLongitude());


        } catch(Exception e){};
        return p1;
    }

}
