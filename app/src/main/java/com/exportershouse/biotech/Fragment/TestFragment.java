package com.exportershouse.biotech.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.exportershouse.biotech.R;

import java.util.Calendar;

/**
 * Created by Shrey on 24-04-2018.
 */

public class TestFragment extends Fragment {

    View rootview;

    TextView textDummyHintMobileNumber;
    TextView textDummyHintPromoCode;
    EditText editMobileNumber;
    EditText editPromoCode;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_test, container, false);

        textDummyHintMobileNumber = (TextView) rootview.findViewById(R.id.text_dummy_hint_mobile_number);
        textDummyHintPromoCode = (TextView) rootview.findViewById(R.id.text_dummy_hint_promo_code);
        editMobileNumber = (EditText)rootview. findViewById(R.id.edit_mobile_number);
        editPromoCode = (EditText)rootview. findViewById(R.id.edit_promo_code);


        // Mobile number
        editMobileNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            textDummyHintMobileNumber.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (editMobileNumber.getText().length() > 0)
                        textDummyHintMobileNumber.setVisibility(View.VISIBLE);
                    else
                        textDummyHintMobileNumber.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Promo code
        editPromoCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            textDummyHintPromoCode.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (editPromoCode.getText().length() > 0)
                        textDummyHintPromoCode.setVisibility(View.VISIBLE);
                    else
                        textDummyHintPromoCode.setVisibility(View.INVISIBLE);
                }
            }
        });



        rootview.setFocusableInTouchMode(true);
        rootview.requestFocus();
        rootview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // DO WHAT YOU WANT ON BACK PRESSED
                    getFragmentManager().popBackStack();
                    return true;
                } else {
                    return false;
                }
            }
        });


        return rootview;
    }


}
