package ru.ctddev.ifmo.year2013.foodsharing.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.ctddev.ifmo.year2013.foodsharing.model.Donation;
import ru.ctddev.ifmo.year2013.foodsharing.R;

/**
 * Created by demouser on 8/4/16.
 */
public class FoodDialogFragment extends DialogFragment {
    int mNum;

    public Donation getDonation() {
        return donation;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }

    private Donation donation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int style = DialogFragment.STYLE_NORMAL, theme = 0;

        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.reserve_food, container, false);
        TextView address = (TextView) v.findViewById(R.id.food_name);
        address.setText(donation.getAddress());
        final Spinner spinner = (Spinner) v.findViewById(R.id.quantity);
        // Pick a style based on the num.

        //Sample String ArrayList
        ArrayList<String> arrayList1 = new ArrayList<String>();
        for(int i=1; i<=donation.getQuantity();i++)
            arrayList1.add(Integer.toString(i));

        ArrayAdapter<String> adp = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, arrayList1);
        spinner.setAdapter(adp);

        spinner.setVisibility(View.VISIBLE);
        //Set listener Called when the item is selected in spinner

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long arg3)
            {
                String selected = spinner.getSelectedItem().toString();
                Map<String, Object> donations = new HashMap<String, Object>();
                DatabaseReference donationsRef = FirebaseDatabase.getInstance().getReference("");

                //update the whole donation
                donations.put("donations/"+donation.getId(),donation.getQuantity() - Integer.parseInt(selected));
                donationsRef.updateChildren(donations);

            }

            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub
            }
        });


        return v;

    }

    public void onClick(View v){
        if(v.getId() == R.id.reserve){






        }
    }


}