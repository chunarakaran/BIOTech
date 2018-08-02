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
import android.widget.Button;
import android.widget.EditText;
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

public class ChangePasswordFragment extends Fragment {

    View rootview;

    TextView newpwd,confpwd,Toldpwd;
    EditText NewPwd,ConfPwd,Oldpwd;
    Button Submit;

    String HUser_id,HNewPwd,HOldPwd;


    //volley
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String HttpUrl = "http://biotechautomfg.com/api/change_pass";

    String User_id;
    public static final String PREFS_NAME = "login";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_change_password, container, false);

        SharedPreferences sp = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        User_id = sp.getString("User", "");

        getActivity().setTitle("Change Password");

        requestQueue = Volley.newRequestQueue(getActivity());
        progressDialog = new ProgressDialog(getActivity());

        Initialize();

        EditTextFocus();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Validation();
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

    public void Initialize()
    {
        Toldpwd=(TextView)rootview.findViewById(R.id.Toldpwd);
        newpwd=(TextView)rootview.findViewById(R.id.Tnewpwd);
        confpwd=(TextView)rootview.findViewById(R.id.Tconfpwd);

        Oldpwd=(EditText)rootview.findViewById(R.id.input_oldpwd);
        NewPwd=(EditText)rootview.findViewById(R.id.input_newpwd);
        ConfPwd=(EditText)rootview.findViewById(R.id.input_confpwd);

        Submit=(Button)rootview.findViewById(R.id.btn_submit);

    }

    public void EditTextFocus()
    {
        Oldpwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Toldpwd.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (Oldpwd.getText().length() > 0)
                        Toldpwd.setVisibility(View.VISIBLE);
                    else
                        Toldpwd.setVisibility(View.INVISIBLE);
                }
            }
        });


        NewPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            newpwd.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (NewPwd.getText().length() > 0)
                        newpwd.setVisibility(View.VISIBLE);
                    else
                        newpwd.setVisibility(View.INVISIBLE);
                }
            }
        });

        ConfPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            confpwd.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (ConfPwd.getText().length() > 0)
                        confpwd.setVisibility(View.VISIBLE);
                    else
                        confpwd.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void GetValueFromEditText()
    {
        HUser_id=User_id.toString();
        HOldPwd=Oldpwd.getText().toString();
        HNewPwd=NewPwd.getText().toString();
    }

    public void ChangePassword()
    {
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        GetValueFromEditText();

        // Creating string request with post method.
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {


                        if(ServerResponse.equalsIgnoreCase("false_oldpassword"))
                        {
                            Oldpwd.setError("Old Password does not Match");
                            progressDialog.dismiss();
                        }
                        else
                        {

                            new FancyGifDialog.Builder(getActivity())
                                    .setTitle("Success")
                                    .setMessage("Password Changed Successfully")
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

                                        }
                                    })
                                    .build();



//                            Toast.makeText(getActivity(), ServerResponse, Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }

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
                params.put("user_id", HUser_id);
                params.put("oldpassword", HOldPwd);
                params.put("password", HNewPwd);


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest1);
    }

    public void Validation()
    {

        String Oldpass,Newpass,ConPass;
        Oldpass=Oldpwd.getText().toString();
        Newpass=NewPwd.getText().toString();
        ConPass=ConfPwd.getText().toString();

        if (Oldpass.length()==0)
        {
            Oldpwd.requestFocus();
            Oldpwd.setError("Please Enter Old Password");

        }
        else if (Newpass.length()==0)
        {
            NewPwd.requestFocus();
            NewPwd.setError("Please Enter New Password");
        }

        else if (ConPass.length()==0)
        {
            ConfPwd.requestFocus();
            ConfPwd.setError("Please Enter confirm Password");
        }

        else if (Newpass.equals(ConPass))
        {
            ChangePassword();
        }
        else {
            ConfPwd.requestFocus();
            ConfPwd.setError("Password does not match");
        }

    }


}
