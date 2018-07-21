package com.exportershouse.biotech;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.exportershouse.biotech.Adapter.SessionManager;
import com.exportershouse.biotech.Fragment.DashboardFragment;
import com.exportershouse.biotech.Fragment.InquryStatusFragment;
import com.exportershouse.biotech.Fragment.LeaveStatusFragment;
import com.exportershouse.biotech.Fragment.OrderStatusFragment;
import com.exportershouse.biotech.Fragment.ProfileFragment;




public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{



    Fragment fragment = null;
    BottomNavigationView navigation;
    NavigationView navigationView;
    private SessionManager session;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "LAST_LAUNCH_DATE";


//    String language;
//    public static final String PREFS_NAME = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.nav_home);


        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }



        if(connected()){
            Toast.makeText(getApplicationContext(),"Welcome To BioTech" , Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"User is Not connected" , Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (checkNavigationMenuItem() != 0)
            {
                navigationView.setCheckedItem(R.id.nav_home);
                fragment = new DashboardFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
            else
                super.onBackPressed();
        }
    }

    private int checkNavigationMenuItem() {
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).isChecked())
                return i;
        }
        return -1;
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
            case R.id.action_checkin:
                Intent intent = new Intent(getApplicationContext(), MyLocationUsingLocationAPI.class);
                startActivity(intent);
                break;
            case R.id.action_inquiry:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragment = new InquryStatusFragment();
                fragmentTransaction.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
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

    private boolean connected(){
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo !=null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }


    private void displaySelectedScreen(int itemId) {



        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new DashboardFragment();
                break;

            case R.id.nav_viewOrder:
                fragment = new OrderStatusFragment();
                break;

            case R.id.nav_viewLeave:
                fragment = new LeaveStatusFragment();
                break;

            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;

            case R.id.nav_checkin:
                Intent intent = new Intent(getApplicationContext(), MyLocationUsingLocationAPI.class);
                startActivity(intent);
                break;
            case R.id.nav_inquiry:
                fragment = new InquryStatusFragment();
                break;

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

}