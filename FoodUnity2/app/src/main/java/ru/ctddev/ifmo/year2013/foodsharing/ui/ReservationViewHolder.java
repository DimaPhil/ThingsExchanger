package ru.ctddev.ifmo.year2013.foodsharing.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.ctddev.ifmo.year2013.foodsharing.model.Data;
import ru.ctddev.ifmo.year2013.foodsharing.model.Donation;
import ru.ctddev.ifmo.year2013.foodsharing.R;

/**
 * Created by Daria Yakovleva on 8/4/16.
 */
public class ReservationViewHolder extends RecyclerView.ViewHolder {

    public View view;
    ImageView imageView;
    TextView nameView;
    TextView descriptionView;
    TextView ownerView;
    TextView timeView;
    String id;

    public ReservationViewHolder(final View itemView) {
        super(itemView);
        view = itemView;
        imageView = (ImageView) view.findViewById(R.id.reservation_image);
        //TODO set image
        nameView = (TextView) view.findViewById(R.id.reservation_name);
        descriptionView = (TextView) view.findViewById(R.id.reservation_description);
        ownerView = (TextView) view.findViewById(R.id.owner);
        timeView = (TextView) view.findViewById(R.id.reservation_time_left);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RESERVATION VIEW HOLDER", "Start Item Click");

                FragmentManager fm = ((Activity) view.getContext()).getFragmentManager();
                ProductInformationFragment dialog = new ProductInformationFragment();
                //TODO sent productID
//                Data.productID = view.g
                dialog.show(fm, "Product information: " + Data.productID);

                Log.d("RESERVATION VIEW HOLDER", " End Item Click");
            }
        });
    }

    public void setData(final Donation donation) {
        Data.productID = donation.iden;
        imageView.setImageResource(R.mipmap.pizza);
        nameView.setText(donation.food_description);
        descriptionView.setText(donation.optional_info);
        ownerView.setText("Owner: " + donation.getDisplay_name()); //TODO
        timeView.setText("INF"); //TODO
        //TODO download image
    }
}

