package ru.ctddev.ifmo.year2013.foodsharing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ru.ctddev.ifmo.year2013.foodsharing.model.Data;
import ru.ctddev.ifmo.year2013.foodsharing.database.DatabaseConnection;
import ru.ctddev.ifmo.year2013.foodsharing.model.Donation;
import ru.ctddev.ifmo.year2013.foodsharing.R;
import ru.ctddev.ifmo.year2013.foodsharing.model.User;
import ru.ctddev.ifmo.year2013.foodsharing.ui.donate.DonateFoodActivity;
import ru.ctddev.ifmo.year2013.foodsharing.ui.foodmap.FoodmapActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_swipe);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            super.setPhotoUrl(currentUser.getPhotoUrl());
            super.setUsername(currentUser.getDisplayName());
        }
        initDrawer();

        DatabaseConnection connection = new DatabaseConnection();
        DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("DBC", "All done: " + Data.users.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        users.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                User user = snapshot.getValue(User.class);
                user.iden = snapshot.getKey();
                Data.users.put(snapshot.getKey(), user);
                Log.i("DBC", "Got child: " + snapshot.getKey() + ", new size: " + Data.users.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        DatabaseReference donations = FirebaseDatabase.getInstance().getReference("donations");
        donations.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("DBC", "All done: " + Data.donations.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        donations.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Donation donation = snapshot.getValue(Donation.class);
                donation.id = snapshot.getKey();
                donation.reserved = 0;
                Data.donations.put(snapshot.getKey(), donation);
                Log.i("DBC", "Got child: " + snapshot.getKey() + ", new size: " + Data.donations.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    public void onDonateClick(View view) {
        Intent intent = new Intent(this, DonateFoodActivity.class);
        startActivity(intent);
    }

    public void onReserveClick(View view) {
        Intent intent = new Intent(this, FoodmapActivity.class);
        startActivity(intent);
    }
}
