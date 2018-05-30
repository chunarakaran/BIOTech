package com.exportershouse.biotech.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exportershouse.biotech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shrey on 24-04-2018.
 */

public class OrderDetailFragment extends Fragment {

    View rootview;

    private ProgressDialog pDialog;


    String Orderid;

    TextView order_no,date,party_name,city,total_case;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_order_detail, container, false);

        getActivity().setTitle("Order Status");

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        String Url = getResources().getString(R.string.url);


        order_no=(TextView)rootview.findViewById(R.id.order_no);
        date=(TextView)rootview.findViewById(R.id.date);
        party_name=(TextView)rootview.findViewById(R.id.party_name);
        city=(TextView)rootview.findViewById(R.id.city);
        total_case=(TextView)rootview.findViewById(R.id.total_case);

        Bundle bundle=getArguments();
        Orderid=String.valueOf(bundle.getString("orderid"));


        String HTTP_JSON_URL = Url+"api/view_order_detail?id="+Orderid;

        JSON_HTTP_CALL(HTTP_JSON_URL);

//        order_no.setText(Orderid);

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
                    JSONArray jsonArray=jsonObject.getJSONArray("view_order_detail");
                    for(int i=0;i<jsonArray.length();i++)
                    {

                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        String Sorder_no,Sdate,Sparty_name,Scity,Stotal_case;

//                        jsonObject1.getString("id");
                        Sorder_no=jsonObject1.getString("order_no");
                        Sdate=jsonObject1.getString("current_date");
                        Sparty_name=jsonObject1.getString("party_name");
                        Scity=jsonObject1.getString("city");
                        Stotal_case=jsonObject1.getString("total");

                        order_no.setText(Sorder_no.toString());
                        date.setText(Sdate.toString());
                        party_name.setText(Sparty_name.toString());
                        city.setText(Scity.toString());
                        total_case.setText(Stotal_case.toString());





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
