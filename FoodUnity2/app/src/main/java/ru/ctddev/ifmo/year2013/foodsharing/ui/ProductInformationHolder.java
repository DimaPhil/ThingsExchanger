package ru.ctddev.ifmo.year2013.foodsharing.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.ctddev.ifmo.year2013.foodsharing.model.Donation;
import ru.ctddev.ifmo.year2013.foodsharing.R;

/**
 * Created by demouser on 8/4/16.
 */
public class ProductInformationHolder {

    public View view;
    ImageView imageView;
    TextView nameView;
    TextView descriptionView;
    TextView expirationDateView;
    TextView countView;
    TextView reservedView;

    public ProductInformationHolder(View itemView) {
        view = itemView;
        imageView = (ImageView)view.findViewById(R.id.product_information_image);
        nameView = (TextView)view.findViewById(R.id.product_information_name);
        descriptionView = (TextView)view.findViewById(R.id.product_information_description);
        expirationDateView = (TextView)view.findViewById(R.id.product_information_date);
        countView = (TextView)view.findViewById(R.id.product_information_count);
        reservedView = (TextView)view.findViewById(R.id.product_information_reserved);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("USERS LIST FRAGMENT", "Start Item Click");
//                //TODO cancel confirm
////                TextView text = (TextView)view;
////                mLister.onItemClick(text.getText().toString());
//                Log.d("USERS LIST FRAGMENT", " End Item Click");
//            }
//        });
    }

    public void setData(final Donation donation) {
        imageView.setImageResource(R.mipmap.pizza); //TODO
        nameView.setText(donation.food_description);
        descriptionView.setText(donation.optional_info);
        expirationDateView.setText("Expiration date: " + donation.expiry_date);
        countView.setText("Count: " + donation.getQuantity());
        reservedView.setText("Reserved: " + donation.getReserved());
    }
}
