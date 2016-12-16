package com.camp.android.luciana.foodunity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daria Yakovleva on 8/4/16.
 */
public class ProductListAdapter extends RecyclerView.Adapter {
    List<Donation> data;
    LayoutInflater inflater = null;
    Activity activity = null;
    Integer viewID = -1;

    public ProductListAdapter() {
        data = new ArrayList<>();
    }


    public ProductListAdapter(Integer viewID, List<Donation> data, Activity activity) {
        this.viewID = viewID;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("PRODUCT LIST ADAPTER", "Start onCreateView");
        RecyclerView.ViewHolder holder = null;
        switch (viewID) {
            case (R.id.donation_fragment): {
                View curView = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_item, parent, false);
                holder = new DonationViewHolder(curView);
            }
            break;
            case (R.id.reservation_fragment): {
                View curView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_item, parent, false);
                holder = new ReservationViewHolder(curView);
            }
            break;
        }
        Log.d("PRODUCT LIST ADAPTER", "End onCreateView");
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("PRODUCT LIST ADAPTER", "Start onBindView");
        final Donation current = data.get(position);
        switch (viewID) {
            case (R.id.donation_fragment): {
                ((DonationViewHolder)holder).setData(current);
            }
            break;
            case (R.id.reservation_fragment): {
                ((ReservationViewHolder)holder).setData(current);
            }
            break;
        }

        Log.d("PRODUCT LIST ADAPTER", "End onCreateView");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void remove(int position) {
        Donation item = data.get(position);
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Donation donation) {
        data.add(donation);
        notifyItemChanged(data.size() - 1);
    }

}

