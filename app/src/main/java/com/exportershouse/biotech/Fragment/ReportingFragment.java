package com.exportershouse.biotech.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exportershouse.biotech.Adapter.GetStateDataAdapter;
import com.exportershouse.biotech.MainActivity;
import com.exportershouse.biotech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Shrey on 24-04-2018.
 */

public class ReportingFragment extends Fragment implements View.OnClickListener {

    View rootview;

    private ProgressDialog pDialog;


    LinearLayout Layout1,Layout2,Layout3,Layout4;
    TextView cdate,cday,ctime;
    Spinner statespinner;
    final ArrayList<GetStateDataAdapter> statedatalist = new ArrayList<>();

    String URL;

    String Sstateid;


    String User_id;
    public static final String PREFS_NAME = "login";
    int uid;


    private LinearLayout mLayout;
    private EditText mEditText;
    private Button mButton,submit;
    int k = -1;
    int flag;
    int ss=0;
    ArrayList<String> applnserverinstnos = new ArrayList<String>();
    public static EditText textView[] = new EditText[100];



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

        URL = getString(R.string.url);

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        initialize();

        loadState_SpinnerData(URL);

        statespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long row_id)
            {
                Sstateid = statedatalist.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });


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

        submit=(Button)rootview.findViewById(R.id.btn_submit);


        mLayout = (LinearLayout)rootview.findViewById(R.id.sl2);
        mEditText = (EditText)rootview.findViewById(R.id.partno);
        mButton = (Button)rootview.findViewById(R.id.add);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    k++;
                    flag=k;
                    final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100);
                    lparams.setMargins(1, 20, 1, 0);
                    textView[flag] = new EditText(getActivity());
                    textView[flag].setLayoutParams(lparams);
                    textView[flag].setId(flag);

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                mLayout.addView(textView[flag]);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(getActivity(), Sstateid, Toast.LENGTH_LONG).show();
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
//                Layout2.setVisibility(LinearLayout.GONE);
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


    public void initialize()
    {
        statespinner=(Spinner)rootview.findViewById(R.id.state_spinner);
    }

    private void loadState_SpinnerData(String url)
    {
        pDialog.setMessage("Please Wait ...");
        showDialog();

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url+"api/view_state", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                final ArrayList<String> list = new ArrayList<>();

                list.clear();

                try{
                    GetStateDataAdapter GetDatadp ;
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject jsonObject1;
                    JSONArray jsonArray=jsonObject.getJSONArray("state");
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject1=jsonArray.getJSONObject(i);

                        GetDatadp = new GetStateDataAdapter();
                        GetDatadp.setName(jsonObject1.getString("state"));
                        GetDatadp.setId(jsonObject1.getString("id"));
                        statedatalist.add(GetDatadp);

                        list.add(jsonObject1.getString("state"));

                    }
                    statespinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list));
                    hideDialog();
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
