package ru.ctddev.ifmo.year2013.foodsharing.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.ctddev.ifmo.year2013.foodsharing.model.Data;
import ru.ctddev.ifmo.year2013.foodsharing.model.Donation;
import ru.ctddev.ifmo.year2013.foodsharing.R;
import ru.ctddev.ifmo.year2013.foodsharing.model.Reservation;

/**
 * Created by Daria Yakovleva on 8/4/16.
 */
public class ProductListFragment extends Fragment {

    RecyclerView products;
    ProductListAdapter productListAdapter;
    CallBack mLister;
    List<Donation> donationsList;
    FloatingActionButton add;
    ProductListsActivity activity;
    View v;
    FirebaseAuth mAuth;

    String userID = "-KOOrnYrzKryy84k77tr";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Log.d("PRODUCT LIST FRAGMENT", "Start create View");

        activity = (ProductListsActivity)getActivity();

        v = inflater.inflate(R.layout.list_product_fragment, container, false);
        add = (FloatingActionButton)v.findViewById(R.id.add_button);
        TextView header = (TextView)v.findViewById(R.id.product_list_header);
        products = (RecyclerView) v.findViewById(R.id.list_product);

//        userID = Data.userID;

        donationsList = new ArrayList<>();

        Map<String, Donation> donations = Data.donations;//activity.getDonations();
        List<String> userIdDonations = Data.users.get(userID).getDonations();//activity.getUsers().get(userID).getDonations();

        switch (getId()) {
            case R.id.donation_fragment: {
                header.setText("MY DONATION");
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, DonateFoodActivity.class);
                        startActivity(intent);
                    }
                });
                System.err.println(activity + " " + activity.getUsers() + " ");

                for (String id: userIdDonations) {
                    donationsList.add(donations.get(id));
                }
            }
            break;
            case R.id.reservation_fragment: {
                header.setText("MY RESERVATION");
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, FoodmapActivity.class);
                        startActivity(intent);
                    }
                });
                List<Reservation> userIdReservations = Data.users.get(userID).getReservations();//activity.getUsers().get(userID).getReservations();
                for (Reservation id: userIdReservations) {
                    donationsList.add(donations.get(id.iden));
                }
            }
            break;
        }

        setAdapter(products, getId());
        setAnimator(products);
        setLayoutManager(products);
        setItemTouchHelper(products, productListAdapter);

        Log.d("PRODUCT LIST FRAGMENT", "End create View");
        return v;
    }


    private void setAdapter(RecyclerView view, int viewID) {
        productListAdapter = new ProductListAdapter(viewID, donationsList, getActivity());
        view.setAdapter(productListAdapter);
    }

    private void setLayoutManager(RecyclerView view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        products.setLayoutManager(gridLayoutManager);s
        view.setLayoutManager(layoutManager);
    }
    private void  setAnimator(RecyclerView view) {
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        view.setItemAnimator(itemAnimator);
    }
    private void setItemTouchHelper(RecyclerView view, final ProductListAdapter adapter) {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) { //ItemTouchHelper.RIGHT
                    //TODO only of empty!!
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        int swipedPosition = viewHolder.getAdapterPosition();
                        adapter.remove(swipedPosition);

                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(view);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof CallBack) {
            mLister = (CallBack)context;
        } else {
            throw new IllegalStateException("Where is callback?");
        }
    }

}
