package ru.ctddev.ifmo.year2013.foodsharing.ui;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.ctddev.ifmo.year2013.foodsharing.R;
import ru.ctddev.ifmo.year2013.foodsharing.model.User;

/**
 * Created by Daria Yakovleva on 8/4/16.
 */
public class UserViewHolder extends RecyclerView.ViewHolder {

        public View view;
        TextView nameView;
        TextView phoneView;
        TextView timeView;
        ImageView imageView;

        public UserViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            nameView = (TextView)view.findViewById(R.id.user_reserved_name);
            phoneView = (TextView)view.findViewById(R.id.user_reserved_phone);
            timeView = (TextView)view.findViewById(R.id.user_reserved_time_left);
            imageView = (ImageView)view.findViewById(R.id.user_reserved_image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("USERS LIST FRAGMENT", "Start Item Click");
                    //TODO cancel confirm
//                TextView text = (TextView)view;
//                mLister.onItemClick(text.getText().toString());
                    Log.d("USERS LIST FRAGMENT", " End Item Click");
                }
            });
        }
    
    public void setData(final User user, Integer time) {
        nameView.setText(user.name);
        phoneView.setText(user.phone);
        timeView.setText(time.toString() + ":00"); //TODO
        imageView.setImageResource(R.drawable.figure); //TODO
    }
}
