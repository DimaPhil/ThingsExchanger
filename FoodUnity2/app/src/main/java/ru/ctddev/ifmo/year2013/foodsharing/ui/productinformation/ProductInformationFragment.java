package ru.ctddev.ifmo.year2013.foodsharing.ui.productinformation;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import ru.ctddev.ifmo.year2013.foodsharing.model.Data;
import ru.ctddev.ifmo.year2013.foodsharing.model.Donation;
import ru.ctddev.ifmo.year2013.foodsharing.R;
import ru.ctddev.ifmo.year2013.foodsharing.ui.CallBack;

/**
 * Created by demouser on 8/4/16.
 */
public class ProductInformationFragment extends DialogFragment {

    CallBack mLister;
    String productID = Data.productID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("PRODUCT INFO FRAGMENT", "Start1 create View");
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Log.d("PRODUCT INFO FRAGMENT", "onCreateView start");
        //TODO how to get information?
        final View v = inflater.inflate(R.layout.product_information_fragment, container, false);
        ProductInformationHolder holder = new ProductInformationHolder(v);
        Donation donation = Data.donations.get(productID);
        holder.setData(donation);
        getDialog().setTitle("Donation Information");

        Log.d("PRODUCT INFO FRAGMENT", "onCreateView end");
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


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d("PRODUCT INFO FRAGMENT", "Start activity created");
        super.onActivityCreated(savedInstanceState);
        Log.d("PRODUCT INFO FRAGMENT", "End activity created");
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("PRODUCT INFO FRAGMENT", "Start click on item");
        //TODO
    }
}