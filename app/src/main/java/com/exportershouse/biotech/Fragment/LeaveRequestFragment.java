package com.exportershouse.biotech.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shrey on 24-04-2018.
 */

public class LeaveRequestFragment extends Fragment {

    View rootview;
    private ProgressDialog pDialog;

    private Calendar mcalendar;
    EditText fromDate, toDate,remark;
    private int day,month,year;

    Button send;

    TextView TfromDate,TtoDate,Tremark;

    String URL;

    Spinner leaveSpinner;
    final ArrayList<GetStateDataAdapter> Leavedatalist = new ArrayList<>();


    String User_id;
    public static final String PREFS_NAME = "login";

    String leaveid;

    String hUid,hLeaveid,hfromDate,htoDate,hRemark;

    //volley
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String HttpUrl = "http://biotechautomfg.com/api/leave_type";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SharedPreferences sp = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        User_id = sp.getString("User", "");

        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_leaverequest, container, false);

        getActivity().setTitle("Leave Request");
        ((MainActivity) getActivity()).hideBottomNavigationButton();

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(getActivity());
        progressDialog = new ProgressDialog(getActivity());



//        TfromDate=(TextView)rootview.findViewById(R.id.TfromDate);
//        TtoDate=(TextView)rootview.findViewById(R.id.TtoDate);
        Tremark=(TextView)rootview.findViewById(R.id.Tremark);

        fromDate=(EditText)rootview.findViewById(R.id.input_fromDate);
        toDate=(EditText)rootview.findViewById(R.id.input_toDate);
        mcalendar=Calendar.getInstance();
        day=mcalendar.get(Calendar.DAY_OF_MONTH);
        year=mcalendar.get(Calendar.YEAR);
        month=mcalendar.get(Calendar.MONTH);

        URL = getString(R.string.url);

        send=(Button)rootview.findViewById(R.id.btn_submit);
        remark=(EditText)rootview.findViewById(R.id.input_remark);

        leaveSpinner=(Spinner)rootview.findViewById(R.id.leave_spinner);

        loadLeaveType_SpinnerData(URL);

        leaveSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long row_id)
            {
//                com_name=   statespinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                leaveid = Leavedatalist.get(position).getId();
//                Toast.makeText(getContext(),"Id   " +leaveid , Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });



        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog();
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog1();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                final String VfromDate,VtoDate,Vremark;
                VfromDate = fromDate.getText().toString();
                VtoDate = toDate.getText().toString();
                Vremark=remark.getText().toString();

                if(VfromDate.length()==0)
                {
                    fromDate.requestFocus();
                    fromDate.setError("Select Date");
                }
                else if (VtoDate.length()==0)
                {
                    toDate.requestFocus();
                    toDate.setError("Select Date");
                }
                else if (Vremark.length()==0)
                {
                    remark.requestFocus();
                    remark.setError("Enter Reason");
                }
                else {
                    Add_Leave();

                }

            }
        });

        EditFocus();

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

    public void DateDialog(){

        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                int Month;
                Month=monthOfYear+1;
                fromDate.setText(year + "-" + Month + "-" + dayOfMonth);
            }};
        DatePickerDialog dpDialog=new DatePickerDialog(getActivity(), listener, year, month, day);
        dpDialog.getDatePicker().setMinDate(mcalendar.getTimeInMillis());
        dpDialog.show();
    }

    public void DateDialog1(){

        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                int Month;
                Month=monthOfYear+1;
                toDate.setText(year + "-" + Month + "-" + dayOfMonth);
            }};
        DatePickerDialog dpDialog=new DatePickerDialog(getActivity(), listener, year, month, day);
        dpDialog.getDatePicker().setMinDate(mcalendar.getTimeInMillis());
        dpDialog.show();
    }

    public void EditFocus()
    {
        remark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Tremark.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (remark.getText().length() > 0)
                        Tremark.setVisibility(View.VISIBLE);
                    else
                        Tremark.setVisibility(View.INVISIBLE);
                }
            }
        });


    }


    private void loadLeaveType_SpinnerData(String url)
    {
        pDialog.setMessage("Please Wait ...");
        showDialog();

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url+"api/view_leave_type", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                final ArrayList<String> list = new ArrayList<>();

                list.clear();

                try{
                    GetStateDataAdapter GetDatadp ;
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject jsonObject1;
                    JSONArray jsonArray=jsonObject.getJSONArray("view_leave_type");
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject1=jsonArray.getJSONObject(i);

                        GetDatadp = new GetStateDataAdapter();
                        GetDatadp.setName(jsonObject1.getString("leave_type"));
                        GetDatadp.setId(jsonObject1.getString("id"));
                        Leavedatalist.add(GetDatadp);

                        list.add(jsonObject1.getString("leave_type"));

                    }
                    leaveSpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list));
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


    public void GetValueFromEditText()
    {
        hUid=User_id.toString();
        hLeaveid=leaveid.toString();
        hfromDate=fromDate.getText().toString().trim();
        htoDate=toDate.getText().toString().trim();
        hRemark=remark.getText().toString().trim();

    }

    public void Add_Leave()
    {
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        GetValueFromEditText();

        // Creating string request with post method.
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
//                        Toast.makeText(getActivity(), ServerResponse, Toast.LENGTH_LONG).show();
                        new FancyGifDialog.Builder(getActivity())
                                .setTitle("Success")
                                .setMessage("Your Leave Request Send Successfully")
                                .setNegativeBtnText("Cancel")
                                .setPositiveBtnBackground("#FF4081")
                                .setPositiveBtnText("Ok")
                                .setNegativeBtnBackground("#FFA9A7A8")
                                .setGifResource(R.drawable.check)   //Pass your Gif here
                                .isCancellable(false)
                                .OnPositiveClicked(new FancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {
//                                        Toast.makeText(getActivity(),"Ok",Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();

                                    }
                                })
                                .OnNegativeClicked(new FancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {
//
                                        fromDate.setText("");
                                        toDate.setText("");
                                        remark.setText("");
                                    }
                                })
                                .build();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.

                //Company
                params.put("user_id", hUid);
                params.put("leave_type", hLeaveid);
                params.put("from_date", hfromDate);
                params.put("to_date", htoDate);
                params.put("remark", hRemark);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest1);

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
