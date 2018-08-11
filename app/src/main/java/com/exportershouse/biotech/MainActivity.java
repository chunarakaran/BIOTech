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
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exportershouse.biotech.Adapter.DataAdapter2;
import com.exportershouse.biotech.Adapter.RecyclerViewAdapter2;
import com.exportershouse.biotech.Adapter.SessionManager;
import com.exportershouse.biotech.Fragment.ChangePasswordFragment;
import com.exportershouse.biotech.Fragment.DashboardFragment;
import com.exportershouse.biotech.Fragment.DistributorFragment;
import com.exportershouse.biotech.Fragment.InquryStatusFragment;
import com.exportershouse.biotech.Fragment.LeaveRequestFragment;
import com.exportershouse.biotech.Fragment.LeaveStatusFragment;
import com.exportershouse.biotech.Fragment.NewOrderFragment;
import com.exportershouse.biotech.Fragment.OrderStatusFragment;
import com.exportershouse.biotech.Fragment.ProfileFragment;
import com.exportershouse.biotech.Fragment.ReportingFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{



    Fragment fragment = null;
    BottomNavigationView navigation;
    NavigationView navigationView;
    private SessionManager session;

    View hView;
    CircleImageView User_pic;
    TextView User_name,User_email;

    TextView textCartItemCount;
    public int mCartItemCount;

    String User_id;
    public static final String PREFS_NAME = "login";


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

        SharedPreferences sp = getApplicationContext().getSharedPreferences(PREFS_NAME, getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        User_id = sp.getString("User", "");

        String Url = getResources().getString(R.string.url);
        String HTTP_JSON_URL = Url+"api/view_user_profile?user_id="+User_id;

        String notify = Url+"api/enquiry_view?send_person_id="+User_id;


        hView =  navigationView.getHeaderView(0);
        User_pic=(CircleImageView)hView.findViewById(R.id.user_pic);
        User_name = (TextView)hView.findViewById(R.id.user_name);
        User_email = (TextView)hView.findViewById(R.id.user_email);

        JSON_HTTP_CALL(HTTP_JSON_URL);
        notification(notify);

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
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

//        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId())
//        {
//            case R.id.action_settings:
//                logoutUser();
//                break;
//            case R.id.action_checkin:
//                Intent intent = new Intent(getApplicationContext(), MyLocationUsingLocationAPI.class);
//                startActivity(intent);
//                break;
//            case R.id.action_inquiry:
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragment = new InquryStatusFragment();
//                fragmentTransaction.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
//                break;
//
//        }
//        return true;

        switch (item.getItemId()) {

            case R.id.action_cart: {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragment = new InquryStatusFragment();
                fragmentTransaction.replace(R.id.content_frame, fragment).addToBackStack(null).commit();
                // Do something
                return true;
            }
        }
        return super.onOptionsItemSelected(item);

    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
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

            case R.id.nav_addDist:
                fragment = new DistributorFragment();
                break;

            case R.id.nav_addDailyreport:
                fragment = new ReportingFragment();
                break;

            case R.id.nav_newOrder:
                fragment = new NewOrderFragment();
                break;

            case R.id.nav_viewOrder:
                fragment = new OrderStatusFragment();
                break;

            case R.id.nav_addLeave:
                fragment = new LeaveRequestFragment();
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
            case R.id.nav_logout:
                logoutUser();
                break;

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void JSON_HTTP_CALL(String url)
    {



        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("user_pro");

                    for(int i=0;i<jsonArray.length();i++)
                    {

                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        String name,email,gender,mobileno,city,country;

                        String base_url = getString(R.string.url);
                        String img_path = base_url+jsonObject1.getString("emp_image");

                        jsonObject1.getString("id");
                        name=jsonObject1.getString("name");
                        email=jsonObject1.getString("email");



                        Picasso.with(getApplicationContext()).load(img_path).into(User_pic);
                        User_name.setText(name);
                        User_email.setText(email);



                    }


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

    private void notification(String url)
    {

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("view_enquiry");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        mCartItemCount=jsonArray.length();

                        if (textCartItemCount != null) {
                            if (mCartItemCount == 0) {
                                if (textCartItemCount.getVisibility() != View.GONE) {
                                    textCartItemCount.setVisibility(View.GONE);
                                }
                            } else {
                                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                                    textCartItemCount.setVisibility(View.VISIBLE);
                                }
                            }
                        }


                    }

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



}