package com.camp.android.luciana.foodunity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daria Yakovleva on 8/4/16.
 */
public class UsersAdapter extends RecyclerView.Adapter<UserViewHolder> {

    List<Pair<User, Integer>> data;
    LayoutInflater inflater = null;
    Activity activity = null;

    public UsersAdapter() {
        data = new ArrayList<>();
    }


    public UsersAdapter(List<Pair<User, Integer>> data, Activity activity) {
        this.data = data;
        this.activity = activity;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).hashCode();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("USERS LIST ADAPTER", "Start onCreateView");
        View curView = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_reserved_item, parent, false);
        UserViewHolder holder = new UserViewHolder(curView);
        Log.d("USERS LIST ADAPTER", "End onCreateView");
        return holder;
    }


    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        Log.d("USERS LIST ADAPTER", "Start onBindView");
        final User current = data.get(position).first;
        holder.setData(current, data.get(position).second);
        Log.d("USERS LIST ADAPTER", "End onCreateView");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Pair<User, Integer> user) {
        data.add(user);
        notifyItemChanged(data.size() - 1);
    }

}