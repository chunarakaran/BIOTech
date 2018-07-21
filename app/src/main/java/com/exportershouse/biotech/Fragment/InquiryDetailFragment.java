package com.exportershouse.biotech.Fragment;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exportershouse.biotech.MainActivity;
import com.exportershouse.biotech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import customfonts.MyTextView;

/**
 * Created by Shrey on 24-04-2018.
 */

public class InquiryDetailFragment extends Fragment {

    View rootview;

    private ProgressDialog pDialog;

    Button Reply;


    String Inquryid;

    MyTextView party_name,conPersonName,Phone_No,city,state,cust_ref,priority,brand,dis;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_inquiry_detail, container, false);

        getActivity().setTitle("Inquiry Status");
//        ((MainActivity) getActivity()).hideBottomNavigationButton();

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        String Url = getResources().getString(R.string.url);

        Reply=(Button)rootview.findViewById(R.id.btn_reply);


        party_name=(MyTextView) rootview.findViewById(R.id.party_name);
        conPersonName=(MyTextView) rootview.findViewById(R.id.conPersonName);
        Phone_No=(MyTextView) rootview.findViewById(R.id.Phone_No);
        city=(MyTextView) rootview.findViewById(R.id.city);
        state=(MyTextView) rootview.findViewById(R.id.state);
        cust_ref=(MyTextView) rootview.findViewById(R.id.cust_ref);
        priority=(MyTextView) rootview.findViewById(R.id.priority);
        brand=(MyTextView) rootview.findViewById(R.id.brand);
        dis=(MyTextView) rootview.findViewById(R.id.dis);

        Bundle bundle=getArguments();
        Inquryid=String.valueOf(bundle.getString("Inquryid"));

//        Toast.makeText(getActivity(), "You clicked " + Inquryid, Toast.LENGTH_SHORT).show();


        String HTTP_JSON_URL = Url+"api/enquiry_view_id?id="+Inquryid;

        JSON_HTTP_CALL(HTTP_JSON_URL);

        Reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transection=getFragmentManager().beginTransaction();
                InquiryReplyFragment mfragment=new InquiryReplyFragment();

                Bundle bundle=new Bundle();
                bundle.putString("Inquryid",Inquryid);
                mfragment.setArguments(bundle);

                transection.replace(R.id.container, mfragment);
                transection.addToBackStack(null).commit();
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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("view_enquiry_id");
                    for(int i=0;i<jsonArray.length();i++)
                    {

                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        String Sparty_name,SconPersonName,SPhone_No,Scity,Sstate,Scust_ref,Spriority,Sbrand,Sdis;

//                        jsonObject1.getString("id");
                        Sparty_name=jsonObject1.getString("party_name");
                        SconPersonName=jsonObject1.getString("contact_person_name");
                        SPhone_No=jsonObject1.getString("contact_person_no");
                        Scity=jsonObject1.getString("city");
                        Sstate=jsonObject1.getString("state");
                        Scust_ref=jsonObject1.getString("customer_enquiry_ref");
                        Spriority=jsonObject1.getString("priority");
                        Sbrand=jsonObject1.getString("brand_name");
                        Sdis=jsonObject1.getString("desc");





                        party_name.setText(Sparty_name.toString());
                        conPersonName.setText(SconPersonName.toString());
                        Phone_No.setText(SPhone_No.toString());
                        city.setText(Scity.toString());
                        state.setText(Sstate.toString());
                        cust_ref.setText(Scust_ref.toString());
                        priority.setText(Spriority.toString());
                        brand.setText(Sbrand.toString());
                        dis.setText(Html.fromHtml(Sdis.toString(), Html.FROM_HTML_MODE_COMPACT));





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
