package com.exportershouse.biotech.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alexzh.circleimageview.CircleImageView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exportershouse.biotech.ChangePasswordActivity;
import com.exportershouse.biotech.ForgotPasswordActivity;
import com.exportershouse.biotech.MainActivity;
import com.exportershouse.biotech.MyLocationUsingLocationAPI;
import com.exportershouse.biotech.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import customfonts.MyTextView;

/**
 * Created by Shrey on 24-04-2018.
 */

public class ProfileFragment extends Fragment {

    View rootview;

    private ProgressDialog pDialog;

    MyTextView Name,Email,Mobileno,Gender,changePwd,City,Country;

    CircleImageView profile;

    String User_id;
    public static final String PREFS_NAME = "login";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences sp = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        User_id = sp.getString("User", "");

        getActivity().setTitle("Profile");
        ((MainActivity) getActivity()).hideBottomNavigationButton();

        profile=(CircleImageView)rootview.findViewById(R.id.banar1);
        Name=(MyTextView)rootview.findViewById(R.id.username);
        Email=(MyTextView)rootview.findViewById(R.id.email1);
        Mobileno=(MyTextView)rootview.findViewById(R.id.mobno);
        Gender=(MyTextView)rootview.findViewById(R.id.gender);
        City=(MyTextView)rootview.findViewById(R.id.Ecity);
        Country=(MyTextView)rootview.findViewById(R.id.country);
        changePwd=(MyTextView)rootview.findViewById(R.id.pass1);

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

//        Email.setText(User_id);
        String Url = getResources().getString(R.string.url);

        String HTTP_JSON_URL = Url+"api/view_user_profile?user_id="+User_id;

        JSON_HTTP_CALL(HTTP_JSON_URL);



        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(),
                R.drawable.profile1);

        ImageView imgv = (ImageView)rootview.findViewById(R.id.banar1);

        //  Bitmap bitmap = StringToBitMap(imgv);
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);

        imgv.setImageBitmap(circleBitmap);

        changePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
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

    private void JSON_HTTP_CALL(String url)
    {

        pDialog.setMessage("Please Wait ...");
        showDialog();

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
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
                        mobileno=jsonObject1.getString("number");
                        gender=jsonObject1.getString("gender");
                        city=jsonObject1.getString("city");
                        country=jsonObject1.getString("country");


                        Picasso.with(getContext()).load(img_path).into(profile);
                        Name.setText(name);
                        Email.setText(email);
                        Mobileno.setText(mobileno);
                        Gender.setText(gender);
                        City.setText(city);
                        Country.setText(country);


                    }

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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
