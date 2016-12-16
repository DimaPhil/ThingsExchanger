package com.camp.android.luciana.foodunity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.admin.DeviceAdminInfo;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daria Yakovleva on 8/4/16.
 */
public class ProductListsActivity extends FragmentActivity implements CallBack {

    public static String USERS = "users";
    String userID = "-KOOrnYrzKryy84k77tr"; //TODO where?
    public Map<String, User> usersMap = new HashMap<>();
    public Map<String, Donation> donationsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser current = mAuth.getCurrentUser();
        this.userID = current.getUid();
//        Data.userID = userID;
        Log.d("PRODUCT LIST ACTIVITY", "Start create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_list_activity);
        /*
                DatabaseConnection connection = new DatabaseConnection();
        DatabaseConnection.OnInfo info = new DatabaseConnection.OnInfo() {
                    @Override
                    public void gotUser(User user) {
                        //TODO do smth with user
                        usersMap.put(user.iden, user);
                        Data.users = usersMap;
                        System.out.println(user.toString());
                    }

                    @Override
                    public void gotDonation(Product donation) {
                        //TODO smth
                    }

                    @Override
                    public void gotUsers(List<User> users) {
                        for (User user : users) {
                            usersMap.put(user.iden, user);
                            Data.users = usersMap;
                            System.out.println(user.toString());
                        }
                    }

                    @Override
                    public void gotDonations(List<Product> donations) {
                        for (Product donation : donations) {
                            donationsMap.put(donation.iden, donation);
                            Data.donations = donationsMap;
                            System.out.println(donation.toString());
                        }
                    }
                };
                connection.getAllUsers(info);
                connection.getAllDonations(info);
*/

//        if (findViewById(R.id.detail_container) != null) {
//            Fragment ViewFragment = new ViewFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, ViewFragment);
//        }
        //TODO change smth Phone or Tablet
        Log.d("PRODUCT LIST ACTIVITY", "End create");
    }

    @Override
    public void onItemClick(String itemName) {
        Log.d("PRODUCT LIST ACTIVITY", "Start Item Click");
        //TODO

        Log.d("PRODUCT LIST ACTIVITY", "End Item Click");
    }

    public Map<String, User> getUsers() {
        return usersMap;
    }
    public  Map<String, Donation> getDonations() {
        return donationsMap;
    }

    public String getUserID() {
        return userID;
    }
}