package com.exportershouse.biotech.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.exportershouse.biotech.R;

/**
 * Created by Shrey on 24-04-2018.
 */

public class NextDistributorFragment extends Fragment implements View.OnClickListener {

    View rootview;



    LinearLayout Layout1,Layout2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_nextdistributor, container, false);

        rootview.findViewById(R.id.radioyes).setOnClickListener(this);
        rootview.findViewById(R.id.radioNo).setOnClickListener(this);

        Layout1=(LinearLayout)rootview.findViewById(R.id.l1);
        Layout2=(LinearLayout)rootview.findViewById(R.id.l2);
        Layout1.setVisibility(LinearLayout.GONE);



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

    @Override
    public void onClick(View view)
    {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioyes:
                if (checked)
                    Layout2.setVisibility(LinearLayout.VISIBLE);
                    Layout1.setVisibility(LinearLayout.GONE);
                    break;
            case R.id.radioNo:
                if (checked)
                    Layout1.setVisibility(LinearLayout.VISIBLE);
                    Layout2.setVisibility(LinearLayout.GONE);
                    break;
        }
    }
}
