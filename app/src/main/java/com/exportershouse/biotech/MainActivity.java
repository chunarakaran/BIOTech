package com.exportershouse.biotech;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.exportershouse.biotech.Adapter.SessionManager;
import com.exportershouse.biotech.Fragment.DashboardFragment;
import com.exportershouse.biotech.Fragment.LeaveStatusFragment;
import com.exportershouse.biotech.Fragment.OrderStatusFragment;
import com.exportershouse.biotech.Fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {



    Fragment fragment = null;
    BottomNavigationView navigation;
    private SessionManager session;


//    String language;
//    public static final String PREFS_NAME = "login";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragment = new DashboardFragment();
                    fragmentTransaction.replace(R.id.container, fragment).commit();

                    return true;
                case R.id.navigation_leave:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragment = new LeaveStatusFragment();
                    fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_order:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragment = new OrderStatusFragment();
                    fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_profile:
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragment = new ProfileFragment();
                    fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new DashboardFragment();
        fragmentTransaction.replace(R.id.container, fragment).commit();

    }

    public void showBottomNavigationButton() {
        navigation.setVisibility(View.VISIBLE);
    };

    public void hideBottomNavigationButton() {
        navigation.setVisibility(View.GONE);
    };

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_settings:
                logoutUser();

                break;

        }
        return true;
    }

    private void logoutUser() {
        session.setLogin(false);
        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
