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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.exportershouse.biotech.Adapter.AutoCompleteAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shrey on 24-04-2018.
 */

public class NewOrderFragment extends Fragment {

    View rootview;

    private ProgressDialog pDialog;

    Button add,submit,edit;
    LinearLayout Layout1,Layout2;

    Spinner brand_spinner,color_spinner,partno_spinner;

    String brand_name,color_name,part_no;
    private static String color_id;
    String URL;
    ArrayList<String> Brand,color,partno;

    TextView orderno,cdate;

    String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_neworder, container, false);

        URL = getString(R.string.url);


        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        getActivity().setTitle("New Order");
        ((MainActivity) getActivity()).hideBottomNavigationButton();

        //Creating the instance of ArrayAdapter containing list of fruit names
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                (getActivity(), android.R.layout.select_dialog_item, fruits);

        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv = (AutoCompleteTextView) rootview.findViewById(R.id.party_name);
        actv.setThreshold(1);//will start working from first character
        int layout = android.R.layout.simple_list_item_1;
        AutoCompleteAdapter adapter = new AutoCompleteAdapter (getActivity(), layout);
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.BLACK);



        add=(Button)rootview.findViewById(R.id.btn_add);
        submit=(Button)rootview.findViewById(R.id.btn_submit);
        edit=(Button)rootview.findViewById(R.id.btn_edit);
        Layout2=(LinearLayout)rootview.findViewById(R.id.l2);
        Layout2.setVisibility(LinearLayout.GONE);
        submit.setVisibility(Button.GONE);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add.setVisibility(Button.GONE);
                submit.setVisibility(View.VISIBLE);
                Layout2.setVisibility(View.VISIBLE);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                add.setVisibility(View.VISIBLE);
                submit.setVisibility(Button.GONE);
                Layout2.setVisibility(LinearLayout.GONE);
            }
        });


        brand_spinner=(Spinner)rootview.findViewById(R.id.brand_spinner);
        Brand=new ArrayList<>();
        loadBrandSpinnerData(URL);

        brand_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                brand_name=   brand_spinner.getItemAtPosition(brand_spinner.getSelectedItemPosition()).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        color_spinner=(Spinner)rootview.findViewById(R.id.color_spinner);
        color=new ArrayList<>();
        loadColorSpinnerData(URL);

        color_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                color_name=   color_spinner.getItemAtPosition(color_spinner.getSelectedItemPosition()).toString();
                int colorid=color_spinner.getSelectedItemPosition();
                color_id=Integer.toString(colorid+1);
//                cdate.setText(color_id);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        partno_spinner=(Spinner)rootview.findViewById(R.id.partno_spinner);
        partno=new ArrayList<>();
        loadPartnoSpinnerData(URL);

        partno_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                part_no=   partno_spinner.getItemAtPosition(partno_spinner.getSelectedItemPosition()).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        orderno=(TextView)rootview.findViewById(R.id.order_no);
        loadOrdernoData(URL);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        cdate=(TextView)rootview.findViewById(R.id.order_date);
        cdate.setText(formattedDate);





        rootview.setFocusableInTouchMode(true);
        rootview.requestFocus();
        rootview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // DO WHAT YOU WANT ON BACK PRESSEDkhj
                    getFragmentManager().popBackStack();
                    return true;
                } else {
                    return false;
                }
            }
        });


        return rootview;
    }

    private void loadBrandSpinnerData(String url)
    {
        pDialog.setMessage("Please Wait ...");
        showDialog();

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url+"api/brand", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("brand");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String cat_name=jsonObject1.getString("name");
                        Brand.add(cat_name);
                    }

                    brand_spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Brand));
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

    private void loadColorSpinnerData(String url)
    {
        pDialog.setMessage("Please Wait ...");
        showDialog();

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url+"api/color", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("colors");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String cat_name=jsonObject1.getString("name");
                        color.add(cat_name);
                    }

                    color_spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, color));
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


    private void loadPartnoSpinnerData(String url)
    {
        pDialog.setMessage("Please Wait ...");
        showDialog();

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url+"api/part?color_id=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("parts");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String cat_name=jsonObject1.getString("part_no");
                        partno.add(cat_name);
                    }

                    partno_spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, partno));
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

    private void loadOrdernoData(String url)
    {
        pDialog.setMessage("Please Wait ...");
        showDialog();

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url+"api/order_no", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                        JSONObject jsonObject=new JSONObject(response);
                        String order_no=jsonObject.getString("date");

                    orderno.setText(order_no);

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
