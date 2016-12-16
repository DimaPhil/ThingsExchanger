package ru.ctddev.ifmo.year2013.foodsharing.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.Pair;

import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import ru.ctddev.ifmo.year2013.foodsharing.model.Data;
import ru.ctddev.ifmo.year2013.foodsharing.R;
import ru.ctddev.ifmo.year2013.foodsharing.model.Reservation;
import ru.ctddev.ifmo.year2013.foodsharing.model.User;

/**
 * Created by Daria Yakovleva on 8/4/16.
 */
public class UsersFragment extends DialogFragment {

    RecyclerView users;
    UsersAdapter usersListAdapter;
    CallBack mLister;
    List<Pair<User, Integer>> usersList;
    String productID; //TODO where??

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("USERS LIST FRAGMENT", "Start1 create View");
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Log.d("USERS LIST FRAGMENT", "onCreateView start");
        final View v = inflater.inflate(R.layout.users_reserved_fragment, container, false);
        users = (RecyclerView) v.findViewById(R.id.list_users_reserved);

        getDialog().setTitle("Users");
        //data list
        //TODO get it from userID
        usersList = new ArrayList<>();

        for (User user: Data.users.values()) {
            for (Reservation reserve: user.getReservations()) {
                System.err.println(reserve.iden + " " + Data.productID+ " " + reserve.quantity);
                if (reserve.iden != null) {
                    if (reserve.iden.compareTo(Data.productID) == 0)
                        usersList.add(new Pair<User, Integer>(user, reserve.quantity));
                }
            }
        }

        setAdapter(users, getId());
        setAnimator(users);
        setLayoutManager(users);
        setItemTouchHelper(users, usersListAdapter);

        Log.d("USERS LIST FRAGMENT", "onCreateView end");
        return v;
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
//
//    class PositiveButtonClickListener implements DialogInterface.OnClickListener {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            dialog.dismiss();
//        }
//    }
//
//    DialogInterface.OnClickListener selectItemListener = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//
//            dialog.dismiss();
//        }
//
//    };

    private void setAdapter(RecyclerView view, int viewID) {
        usersListAdapter = new UsersAdapter(usersList, getActivity());
        view.setAdapter(usersListAdapter);
    }

    private void setLayoutManager(RecyclerView view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        view.setLayoutManager(layoutManager);
    }
    private void  setAnimator(RecyclerView view) {
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        view.setItemAnimator(itemAnimator);
    }
    private void setItemTouchHelper(RecyclerView view, final UsersAdapter adapter) {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, 0) { //ItemTouchHelper.RIGHT
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
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("USERS LIST FRAGMENT", "Start activity created");
        super.onActivityCreated(savedInstanceState);
        Log.d("USERS LIST FRAGMENT", "End activity created");
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("USERS LIST FRAGMENT", "Start click on item");
        //TODO
    }
}
