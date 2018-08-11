package com.exportershouse.biotech;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import customfonts.MyTextView;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView TEmail;
    EditText Email;
    MyTextView backLogin;
    Button Reset;
    RequestQueue requestQueue1;
    String Url;

    private ProgressDialog pDialog;

    Fragment fragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        TEmail=(TextView)findViewById(R.id.TEmail);
        Email=(EditText)findViewById(R.id.input_Email);
        backLogin=(MyTextView)findViewById(R.id.backLogin);
        Reset=(Button) findViewById(R.id.btn_reset);

        backLogin.setText("<< Back to BioTech Login Page");

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        Email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            TEmail.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (Email.getText().length() > 0)
                        TEmail.setVisibility(View.VISIBLE);
                    else
                        TEmail.setVisibility(View.INVISIBLE);
                }
            }
        });

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(f);
                finish();
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestQueue1 = Volley.newRequestQueue(getApplicationContext());

                Url = (String) getText(R.string.login_url);
                if(!Email.getText().toString().isEmpty()) {
                    if(isNetworkAvailable()) {
                        check_email();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Please check your internet connecion or try again.", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Email.setError("You must enter Email Id");
                }

            }
        });


    }

    private void check_email()
    {
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest jsonobject = new StringRequest(Request.Method.POST, Url+"api/forgot_pass" + "?email="+Email.getText().toString(), new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {


                if(response.equalsIgnoreCase("false_email"))
                {
                    Email.setError("Email-id does not exist");
                    hideDialog();
                }
                else
                {

                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    hideDialog();

                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);
                    finish();
                }



            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Server Error", Toast.LENGTH_LONG).show();

                hideDialog();
            }
        });

        jsonobject.setRetryPolicy(new DefaultRetryPolicy(100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue1.add(jsonobject);
    }

    public boolean isNetworkAvailable() {

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        boolean isConnected = netInfo != null && netInfo.isConnectedOrConnecting();

        return isConnected;
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
