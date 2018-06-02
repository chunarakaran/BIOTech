package com.exportershouse.biotech;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exportershouse.biotech.Adapter.SessionManager;

public class LoginActivity extends AppCompatActivity {

    EditText user_id,user_password;
    Button login_btn;
    RequestQueue requestQueue1;
    String Url;

    private SessionManager session;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "login";

    boolean mRecentlyBackPressed;
    Handler mExitHandler;
    Runnable mExitRunnable;
    long delay;
    private Context context;

    private ProgressDialog pDialog;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
            // DO WHAT YOU WANT ON BACK PRESSED
            if (mRecentlyBackPressed) {
                //super.onBackPressed();
                MainActivity a = new MainActivity();
                a.finish();
                finish();
//                Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();

            }
            else {
                mRecentlyBackPressed = true;
                Toast.makeText(this, "Press Back Again To Exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mRecentlyBackPressed = false;
                    }
                }, 2000);

            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        delay = 2000L;
        mRecentlyBackPressed = false;
        mExitHandler = new Handler();
        mExitRunnable = new Runnable() {

            @Override
            public void run() {
                mRecentlyBackPressed = false;
            }
        };

        user_id = (EditText) findViewById(R.id.userEmail);
        user_password = (EditText) findViewById(R.id.userpassword);
        login_btn = (Button) findViewById(R.id.BtnLogin);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }



        sharedpreferences = getSharedPreferences(PREFS_NAME, this.MODE_PRIVATE);

        editor = sharedpreferences.edit();

//        editor.clear();
//        editor.commit();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestQueue1 = Volley.newRequestQueue(getApplicationContext());

                Url = (String) getText(R.string.login_url);
                if(!user_id.getText().toString().isEmpty() && !user_password.getText().toString().isEmpty()) {
                    if(isNetworkAvailable()) {
                        check_login();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Please check your internet connecion or try again.", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    user_id.setError("You must enter user name");
                    user_password.setError("You must enter password");
                }
             }

        });



    }

    private void check_login()
    {
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest jsonobject = new StringRequest(Request.Method.POST, Url+"api/login" + "?email="+user_id.getText().toString()+"&password="+user_password.getText().toString(), new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {


                if(response.equalsIgnoreCase("false_u"))
                {
                    user_id.setError("User-id does not exist");
                    user_password.setText("");
                    hideDialog();
                }
                else if(response.equalsIgnoreCase("false"))
                {
                    user_password.setError("Incorrect password");
                    user_password.setText("");
                    hideDialog();
                }
                else
                {
                    editor.putString("User", response);
                    editor.commit();

                    // Create login session
                    session.setLogin(true);

                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
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
