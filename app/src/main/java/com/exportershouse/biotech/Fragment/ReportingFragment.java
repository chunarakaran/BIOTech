package com.exportershouse.biotech.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.exportershouse.biotech.MainActivity;
import com.exportershouse.biotech.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Shrey on 24-04-2018.
 */

public class ReportingFragment extends Fragment implements View.OnClickListener {

    View rootview;

    LinearLayout Layout1,Layout2,Layout3,Layout4;
    TextView cdate,cday,ctime;

    String User_id;
    public static final String PREFS_NAME = "login";
    int uid;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_reporting, container, false);

        SharedPreferences sp = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        User_id = sp.getString("User", "");

        getActivity().setTitle("Reporting");
        ((MainActivity) getActivity()).hideBottomNavigationButton();

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        cdate=(TextView)rootview.findViewById(R.id.date);
        cdate.setText(formattedDate);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        cday=(TextView)rootview.findViewById(R.id.day);
        cday.setText(dayOfTheWeek);


        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("KK:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
        String localTime = date.format(currentLocalTime);
        ctime=(TextView)rootview.findViewById(R.id.time);
        ctime.setText(localTime);

        rootview.findViewById(R.id.radioyes).setOnClickListener(this);
        rootview.findViewById(R.id.radioNo).setOnClickListener(this);
        rootview.findViewById(R.id.partyyes).setOnClickListener(this);
        rootview.findViewById(R.id.partyNo).setOnClickListener(this);

        Layout1=(LinearLayout)rootview.findViewById(R.id.l1);
        Layout2=(LinearLayout)rootview.findViewById(R.id.l2);
        Layout3=(LinearLayout)rootview.findViewById(R.id.sl1);
        Layout4=(LinearLayout)rootview.findViewById(R.id.sl2);
        Layout1.setVisibility(LinearLayout.GONE);
        Layout3.setVisibility(LinearLayout.GONE);



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

            case R.id.partyyes:
                if (checked)
                    Layout4.setVisibility(LinearLayout.VISIBLE);
                Layout3.setVisibility(LinearLayout.GONE);
                break;

            case R.id.partyNo:
                if (checked)
                    Layout3.setVisibility(LinearLayout.VISIBLE);
                Layout4.setVisibility(LinearLayout.GONE);
                break;
        }
    }

}
