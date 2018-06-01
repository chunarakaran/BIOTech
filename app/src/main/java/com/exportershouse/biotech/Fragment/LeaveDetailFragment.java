package com.exportershouse.biotech.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
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
import com.exportershouse.biotech.Adapter.DataAdapter;
import com.exportershouse.biotech.Adapter.RecyclerViewAdapter;
import com.exportershouse.biotech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import customfonts.MyTextView;

/**
 * Created by Shrey on 24-04-2018.
 */

public class LeaveDetailFragment extends Fragment {

    View rootview;

    private ProgressDialog pDialog;


    String Leaveid;

    MyTextView Leavetpe,From_date,To_date,status,Remark;

//    TextView Leavetpe,date,status,Remark;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_leavedetail, container, false);

        getActivity().setTitle("Leave Status");

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        String Url = getResources().getString(R.string.url);


        Leavetpe=(MyTextView) rootview.findViewById(R.id.leave_type);
        From_date=(MyTextView) rootview.findViewById(R.id.from_date);
        To_date=(MyTextView) rootview.findViewById(R.id.to_date);
        status=(MyTextView) rootview.findViewById(R.id.status);
        Remark=(MyTextView) rootview.findViewById(R.id.remark);

        Bundle bundle=getArguments();
        Leaveid=String.valueOf(bundle.getString("leaveid"));


        String HTTP_JSON_URL = Url+"api/view_leave_detail?id="+Leaveid;

        JSON_HTTP_CALL(HTTP_JSON_URL);

//        test.setText(Leaveid);

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
                    JSONArray jsonArray=jsonObject.getJSONArray("leave");
                    for(int i=0;i<jsonArray.length();i++)
                    {

                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        String leavetype,Fromdate,Todate,Sstatus,Sremark;

                        jsonObject1.getString("id");
                        leavetype=jsonObject1.getString("leave_type");
                        Fromdate=jsonObject1.getString("from_date");
                        Todate=jsonObject1.getString("to_date");
                        Sstatus=jsonObject1.getString("leave_status");
                        Sremark=jsonObject1.getString("remark");

                        Leavetpe.setText(leavetype.toString());
                        From_date.setText(Fromdate.toString());
                        To_date.setText(Todate.toString());
                        status.setText(Sstatus.toString());
                        Remark.setText(Sremark.toString());

                        if (Sstatus.toString()=="yes") {
                            status.setTextColor(Color.parseColor("#4caf50"));
                        }




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
