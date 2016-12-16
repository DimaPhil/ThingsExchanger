package com.camp.android.luciana.foodunity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FoodmapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener, OnMapReadyCallback, GoogleMap.OnCameraIdleListener {
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;

    private final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_NONE};
    private int curMapTypeIndex = 3;

    private static HashMap<String, Donation> markerToDonations;

    public GoogleMap getMap() {
        return map;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    private GoogleMap map;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        markerToDonations = new HashMap<>();
        setHasOptionsMenu(true);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        getMapAsync(this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnCameraIdleListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }


    @Override
    public void onResume() {
        super.onResume();


    }
    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            initCamera(mCurrentLocation);
        } catch (SecurityException e) {
        }
    }

    private void initMarkers() {
   //     LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
        DatabaseReference donationsRef = FirebaseDatabase.getInstance().getReference("donations");
        Query query = donationsRef.orderByChild("description");

        final List<Donation> donations = new ArrayList<>();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                final Donation donation = snapshot.getValue(Donation.class);
                donation.setIden(snapshot.getKey());
                Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                Location locationA = new Location("");
                double marker_latitude = donation.getLatitude(), marker_longitude = donation.getLongitude();

                locationA.setLatitude(marker_latitude);
                locationA.setLongitude(marker_longitude);

                if(mCurrentLocation != null) {
                    double distanceTo = (int) Math.floor(locationA.distanceTo(mCurrentLocation)) / 100 * 100;
                    String measurement;
                    if (distanceTo >= 1000) {
                        distanceTo /= 1000;
                        measurement = "km";
                    } else {
                        measurement = "meters";
                    }

                    Log.d("marker", "addMarker " + donation.getDescription());
                    Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(marker_latitude, marker_longitude)).title(donation.getDescription()).snippet(Double.toString(distanceTo) + " " + measurement));
                    if (donation.getReserved() < donation.getQuantity()) {
                        markerToDonations.put(marker.getId(), donation);
                    }
                    map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(final Marker marker) {
                        /*FragmentManager fm = getActivity().getFragmentManager();
                        FoodDialogFragment dialogFragment = new FoodDialogFragment();

                        dialogFragment.setDonation(markerToDonations.get(marker.getId()));
                        dialogFragment.show(fm, "Sample Fragment");*/


                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                            builder.setMessage("Would you like to reserve " + marker.getTitle() + "?")
                                    .setTitle("Reserve food");

                            builder.setPositiveButton("Reserve", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    //TODO: add product to reservation list
                                    addReservationToProfile(donation);

                                    //TODO: remove from map
                                    marker.remove();
                                    int numReserved = (donation.getReserved()) + 1;
                                    donation.setReserved(numReserved);

                                }
                            });

                            AlertDialog dialog = builder.create();

                            dialog.show();
                        }
                    });
                    donations.add(donation);
                }
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


            // ....
        });
    }


    private void addReservationToProfile(Donation donation) {

    }

    private void initCamera(Location location) throws SecurityException {
        LatLng latLng;
        if (location != null) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        }else {
            latLng = new LatLng(51.4931007, -0.1487067);
        }
        CameraPosition position = CameraPosition.builder()
                .target(latLng)
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);

        map.setMapType(MAP_TYPES[curMapTypeIndex]);
        map.setTrafficEnabled(true);
        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude - 0.01, latLng.longitude - 0.01)));
    }

    @Override
    public void onCameraIdle() {
        initMarkers();
    }
}
