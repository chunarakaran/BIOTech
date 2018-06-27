package com.exportershouse.biotech;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePasswordActivity extends AppCompatActivity {

    TextView newpwd,confpwd;
    EditText NewPwd,ConfPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        newpwd=(TextView)findViewById(R.id.Tnewpwd);
        confpwd=(TextView)findViewById(R.id.Tconfpwd);

        NewPwd=(EditText)findViewById(R.id.input_newpwd);
        ConfPwd=(EditText)findViewById(R.id.input_confpwd);

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
}
