package com.exportershouse.biotech;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.exportershouse.biotech.Fragment.ChangePasswordFragment;
import com.exportershouse.biotech.Fragment.DashboardFragment;
import com.exportershouse.biotech.Fragment.DistributorFragment;
import com.exportershouse.biotech.Fragment.NextDistributorFragment;

import customfonts.MyTextView;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView TEmail;
    EditText Email;
    MyTextView backLogin;
    Button Reset;

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
//                android.app.FragmentTransaction transection=getFragmentManager().beginTransaction();
//                fragment=new ChangePasswordFragment();
//                transection.replace(R.id.container,fragment);
//                transection.addToBackStack(null).commit();
            }
        });


    }
}
