package com.camp.android.luciana.foodunity;

import android.location.Location;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by Daria Yakovleva on 8/4/16.
 */
public class DatabaseConnection {
    public static String DONATIONS = "donations";
    public static String USERS = "users";
    public static String NAME = "foodunity-a9577";

    public DatabaseConnection() {}

    public DatabaseConnection(String tableName) {
    }

    interface OnInfo {
        void gotUser(User user);
        void gotDonation(Donation donation);
        void gotUsers(List<User> users);
        void gotDonations(List<Donation> donations);
    }

    public void getUserByID(final String id, final OnInfo tocall) {
        DatabaseReference users = FirebaseDatabase.getInstance().getReference(USERS);
        users.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                if (snapshot.getKey().compareTo(id) != 0) return;
                User user = snapshot.getValue(User.class);
                System.out.println(user.toString());
                tocall.gotUser(user);
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

    public void getDonationByID(final String id, final OnInfo tocall) {
        DatabaseReference donations = FirebaseDatabase.getInstance().getReference(DONATIONS);
        donations.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                if (snapshot.getKey().compareTo(id) != 0) return;
                Donation don = snapshot.getValue(Donation.class);
                don.reserved = 0;
                don.iden = snapshot.getKey();
                System.out.println(don.toString());
                tocall.gotDonation(don);
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

    public void getAllUsers(final OnInfo tocall) {
        DatabaseReference donationsRef = FirebaseDatabase.getInstance().getReference(USERS);
        Query query = donationsRef.orderByChild("name");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                Log.i("DBC", "Got child: " + snapshot.getValue(User.class));
                tocall.gotUser(snapshot.getValue(User.class));
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

    public void getAllDonations(final OnInfo tocall) {
        DatabaseReference donationsRef = FirebaseDatabase.getInstance().getReference(DONATIONS);
        Query query = donationsRef.orderByChild("name");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                List<Donation> donations = new ArrayList<>();
                Donation don = snapshot.getValue(Donation.class);
                don.reserved = 0;
                don.iden = snapshot.getKey();
                donations.add(don);
                tocall.gotDonations(donations);
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


}
