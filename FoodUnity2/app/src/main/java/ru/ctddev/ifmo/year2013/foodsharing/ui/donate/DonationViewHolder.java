package ru.ctddev.ifmo.year2013.foodsharing.ui.donate;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dinuscxj.progressbar.CircleProgressBar;

import ru.ctddev.ifmo.year2013.foodsharing.model.Data;
import ru.ctddev.ifmo.year2013.foodsharing.model.Donation;
import ru.ctddev.ifmo.year2013.foodsharing.R;
import ru.ctddev.ifmo.year2013.foodsharing.ui.user.UsersFragment;

public class DonationViewHolder extends RecyclerView.ViewHolder {

    public View view;
    TextView nameView;
    TextView expiratioDateView;
    TextView descriptionView;
    ImageView imageView;
    CircleProgressBar progressBar;
    Donation donation;

    public DonationViewHolder(final View itemView) {
        super(itemView);
        view = itemView;
        imageView = (ImageView)view.findViewById(R.id.product_image); //TODO set image
        nameView = (TextView)view.findViewById(R.id.product_name);
        descriptionView = (TextView)view.findViewById(R.id.product_description);
        expiratioDateView = (TextView)view.findViewById(R.id.expiration_date);
        progressBar = (CircleProgressBar)view.findViewById(R.id.product_progress_bar);
        setProgressBarSetting();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DONATION VIEW HOLDER", "Start Item Click");

                FragmentManager fm = ((Activity)view.getContext()).getFragmentManager();
                UsersFragment dialog = new UsersFragment();
//                dialog.setArguments();
//                 TODO how??

                donation.getIden();
                dialog.show(fm, "Users");

                Log.d("DONATION VIEW HOLDER", " End Item Click");
            }
        });
    }
    public CircleProgressBar getProgressBar() {
        return progressBar;
    }

    private void setProgressBarSetting() {
        progressBar.setBackgroundColor(Color.YELLOW);
        progressBar.setProgressBackgroundColor(Color.YELLOW);
        progressBar.setProgressEndColor(Color.GREEN);
        progressBar.setProgressStartColor(Color.GREEN);
        progressBar.setProgressTextColor(Color.BLACK);
        progressBar.setProgressTextSize(40);
        progressBar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        progressBar.setStyle(1);
    }
    
    public void setData(final Donation donation) {
        Data.productID = donation.iden;
        this.donation = donation;
        nameView.setText(donation.food_description);
        descriptionView.setText(donation.optional_info);
        expiratioDateView.setText("Expiration date: " + donation.getExpiry_date());
        expiratioDateView.setText("Expiration date: " + donation.expiry_date);
        imageView.setImageResource(R.mipmap.pizza);
        getProgressBar().setProgress((int)((donation.getReserved() + 0.0) / donation.getQuantity() * 100));
        getProgressBar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Count: " + donation.getQuantity() + "\n" + "Reserved: " + donation.getReserved()+ "\n" +
                        "Free: " + donation.getFree(), Toast.LENGTH_SHORT).show();
            }
        });
        //TODO download image
    }

}
