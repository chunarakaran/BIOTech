package com.exportershouse.biotech.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exportershouse.biotech.MainActivity;
import com.exportershouse.biotech.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shrey on 24-04-2018.
 */

public class InquiryReplyFragment extends Fragment {

    View rootview;

    EditText reason,remark;
    Button send;

    TextView Treason,Tremark;

    Spinner Inq_status;

    LinearLayout l1,l2;





    String User_id;
    public static final String PREFS_NAME = "login";

    String Inquryid;
    String InqStatus;
    String hUid,hInquryid,HInqStatus,hReason,hRemark;

    //volley
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String HttpUrl = "http://biotechautomfg.com/api/enquiry_edit";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SharedPreferences sp = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        User_id = sp.getString("User", "");

        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_inquiryreply, container, false);

        getActivity().setTitle("Inquiry Reply");
        ((MainActivity) getActivity()).hideBottomNavigationButton();

        Bundle bundle=getArguments();
        Inquryid=String.valueOf(bundle.getString("Inquryid"));

        requestQueue = Volley.newRequestQueue(getActivity());
        progressDialog = new ProgressDialog(getActivity());


        send=(Button)rootview.findViewById(R.id.btn_submit);
        remark=(EditText)rootview.findViewById(R.id.input_remark);
        reason=(EditText)rootview.findViewById(R.id.input_reason);
        Inq_status=(Spinner)rootview.findViewById(R.id.InqStatus_spinner);
        l1= (LinearLayout) rootview.findViewById(R.id.input_layout_reason);
        l2= (LinearLayout) rootview.findViewById(R.id.input_layout_remark);


        Tremark=(TextView)rootview.findViewById(R.id.Tremark);
        Treason=(TextView)rootview.findViewById(R.id.Treason);

        Inq_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                InqStatus=Inq_status.getSelectedItem().toString();
                int id;
                id=Inq_status.getSelectedItemPosition();

//                Toast.makeText(getContext(),"Id   " +InqStatus , Toast.LENGTH_LONG).show();
                if(id==1)
                {
                    l2.setVisibility(LinearLayout.VISIBLE);
                    l1.setVisibility(LinearLayout.GONE);
                }
                else {
                    l1.setVisibility(LinearLayout.VISIBLE);
                    l2.setVisibility(LinearLayout.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });






        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Send_Reply();

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



    public void EditFocus()
    {
        reason.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Treason.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (reason.getText().length() > 0)
                        Treason.setVisibility(View.VISIBLE);
                    else
                        Treason.setVisibility(View.INVISIBLE);
                }
            }
        });

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




    public void GetValueFromEditText()
    {
        hUid=User_id.toString();
        hInquryid=Inquryid.toString();
        HInqStatus= InqStatus.toString();
        hReason=reason.getText().toString().trim();
        hRemark=remark.getText().toString().trim();

    }

    public void Send_Reply()
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
                                .setMessage("Your Inquiry Reply Successfully")
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
                params.put("id", hInquryid);
                params.put("enquiry_status", HInqStatus);
                params.put("reason", hReason);
                params.put("remarks", hRemark);


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest1);

    }
}
