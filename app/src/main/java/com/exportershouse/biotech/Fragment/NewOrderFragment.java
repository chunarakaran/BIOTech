package com.exportershouse.biotech.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exportershouse.biotech.Adapter.GetBrandDataAdapter;
import com.exportershouse.biotech.Adapter.GetColorDataAdapter;
import com.exportershouse.biotech.Adapter.GetLtrDataAdapter;
import com.exportershouse.biotech.Adapter.GetOrderDataAdapter;
import com.exportershouse.biotech.Adapter.GetPartnoDataAdapter;
import com.exportershouse.biotech.Database.myDBClass;
import com.exportershouse.biotech.MainActivity;
import com.exportershouse.biotech.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import at.markushi.ui.CircleButton;

/**
 * Created by Shrey on 24-04-2018.
 */

public class NewOrderFragment extends Fragment {

    View rootview;

    private ProgressDialog pDialog;

    CircleButton ad,delete;

    Button add,submit,edit;
    LinearLayout Layout1,Layout2;
    TextInputLayout t1;

    Spinner brand_spinner,color_spinner,partno_spinner,Test;
    final ArrayList<GetBrandDataAdapter> datalist = new ArrayList<GetBrandDataAdapter>();
    final ArrayList<GetColorDataAdapter> datalist1 = new ArrayList<>();
    final ArrayList<GetPartnoDataAdapter> datalist2 = new ArrayList<>();
    final ArrayList<GetOrderDataAdapter> datalist3 = new ArrayList<>();
    final ArrayList<GetLtrDataAdapter> datalist4 = new ArrayList<>();


    public ArrayList<String> color_list = new ArrayList<>();
    public ArrayList<String> part_list = new ArrayList<>();
    public ArrayList<String> ltr_list = new ArrayList<>();


    private LinearLayout mLayout;
    int k = -1;
    int flag;
    public static Spinner colorSpinner[] = new Spinner[100];
    public List<Spinner> color_array = new ArrayList<Spinner>();

    private LinearLayout  mLayout1;
    int k1 = -1;
    int flag1;
    public static Spinner partSpinner[] = new Spinner[100];
    public List<Spinner> part_array = new ArrayList<Spinner>();

    private LinearLayout  mLayout2;
    int k2 = -1;
    int flag2;
    public static EditText textView1[] = new EditText[100];
    public List<EditText> qty = new ArrayList<EditText>();

    private LinearLayout  mLayout3;
    int k3 = -1;
    int flag3;
    public static Spinner ltrSpinner[] = new Spinner[100];
    public List<Spinner> ltr_array = new ArrayList<Spinner>();






    String brand_name,color_name,part_no;
    String color_id,brand_id,partno_id;
    String URL;
    public String Order_no="BIO/1803/1101",sDate,Ono,test;

    ArrayList<String> Brand,color,partno;




    TextView orderno,cdate;

    AutoCompleteTextView actv;


    EditText Qty,Ltr,CityName,Discount,Remark;
    TextView dQty,dLtr,dpart_no,dSrno,dTotal;

    int Srno=0;

    String User_id;
    public static final String PREFS_NAME = "login";



    String hUid,hBrandid,hOrderno,hDate,hPartyname,hCityname,hColorid,hPartnoid,hQty,hLtr,hTotal,hDis,hRemark;
    //volley
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String HttpUrl = "http://biotechautomfg.com/api/order_add";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        SharedPreferences sp = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        User_id = sp.getString("User", "");

        // get Data from SQLite
        final myDBClass myDb = new myDBClass(getActivity());
        final String [] myData = myDb.SelectAllData();
        myDb.InsertData("demo");


        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_neworder, container, false);


        URL = getString(R.string.url);


        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(getActivity());
        progressDialog = new ProgressDialog(getActivity());

        getActivity().setTitle("New Order");
        ((MainActivity) getActivity()).hideBottomNavigationButton();
        // Fragment locked in landscape screen orientation
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        setRetainInstance(true);





        initialize();


        //Getting the instance of AutoCompleteTextView
        actv = (AutoCompleteTextView) rootview.findViewById(R.id.party_name);
        actv.setThreshold(1);//will start working from first character
//        int layout = android.R.layout.simple_list_item_1;
//        AutoCompleteAdapter adapter1 = new AutoCompleteAdapter (getActivity(), layout);
      //  actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.BLACK);

//        Qty = (EditText) rootview.findViewById(R.id.input_Qty);
//        Ltr = (EditText) rootview.findViewById(R.id.input_Ltr);
        CityName = (EditText) rootview.findViewById(R.id.city_name);
        Discount=(EditText)rootview.findViewById(R.id.input_discount);
        Remark=(EditText)rootview.findViewById(R.id.input_remark);

        dQty=(TextView)rootview.findViewById(R.id.Qty);
        dLtr=(TextView)rootview.findViewById(R.id.Ltr);
        dpart_no=(TextView)rootview.findViewById(R.id.partno);
        dSrno=(TextView)rootview.findViewById(R.id.srno);
        dTotal=(TextView)rootview.findViewById(R.id.total);



        add=(Button)rootview.findViewById(R.id.btn_add);
        submit=(Button)rootview.findViewById(R.id.btn_submit);
        edit=(Button)rootview.findViewById(R.id.btn_edit);
        Layout2=(LinearLayout)rootview.findViewById(R.id.l2);
        Layout1=(LinearLayout)rootview.findViewById(R.id.l1);
        t1=(TextInputLayout)rootview.findViewById(R.id.input_brandname);
        Layout2.setVisibility(LinearLayout.GONE);
        submit.setVisibility(Button.GONE);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add.setVisibility(Button.GONE);
                submit.setVisibility(View.VISIBLE);
                Layout2.setVisibility(View.VISIBLE);

                Srno=Srno+1;
                dSrno.setText(String.valueOf(Srno));
//                dQty.setText(Qty.getText().toString());
//                dLtr.setText(Ltr.getText().toString());
//                dTotal.setText(Ltr.getText().toString());
//                dpart_no.setText(part_no.toString());
                t1.setVisibility(TextInputLayout.GONE);
                Layout1.setVisibility(LinearLayout.GONE);

                myDb.InsertData(actv.getText().toString());



            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadOrdernoData(URL);

                add.setVisibility(View.VISIBLE);
                submit.setVisibility(Button.GONE);
                Layout2.setVisibility(LinearLayout.GONE);
                t1.setVisibility(TextInputLayout.VISIBLE);
                Layout1.setVisibility(LinearLayout.VISIBLE);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Add_Order();
            }
        });



        brand_spinner=(Spinner)rootview.findViewById(R.id.brand_spinner);
//        Brand=new ArrayList<>();
        loadBrandSpinnerData(URL);

        brand_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                brand_name=   brand_spinner.getItemAtPosition(brand_spinner.getSelectedItemPosition()).toString();
                brand_id = datalist.get(i).getId();
//                Toast.makeText(getContext(),"Id   " +brand_id , Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

//        color_spinner=(Spinner)rootview.findViewById(R.id.color_spinner);
//        color=new ArrayList<>();
//        loadColorSpinnerData(URL);

//        color_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
//            {
//                color_name=   color_spinner.getItemAtPosition(color_spinner.getSelectedItemPosition()).toString();
//                color_id = datalist1.get(i).getId();
////                Toast.makeText(getContext(),"Id   " +color_id , Toast.LENGTH_LONG).show();
//                loadPartnoSpinnerData(URL+"api/part?color_id="+color_id.toString());
//
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // DO Nothing here
//            }
//        });





//        partno_spinner=(Spinner)rootview.findViewById(R.id.partno_spinner);
//        partno=new ArrayList<>();

//        partno_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
//            {
//                part_no=   partno_spinner.getItemAtPosition(partno_spinner.getSelectedItemPosition()).toString();
//                partno_id = datalist2.get(i).getId();
////                Toast.makeText(getContext(),"Id   " +partno_id , Toast.LENGTH_LONG).show();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // DO Nothing here
//            }
//        });

        orderno=(TextView)rootview.findViewById(R.id.order_no);
        loadOrdernoData(URL);

        loadOrderData(URL);




        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        cdate=(TextView)rootview.findViewById(R.id.order_date);
        cdate.setText(formattedDate);
        sDate=formattedDate;


        mLayout = (LinearLayout)rootview.findViewById(R.id.s1);
        mLayout1 = (LinearLayout)rootview.findViewById(R.id.s2);
        mLayout2 = (LinearLayout)rootview.findViewById(R.id.s3);
        mLayout3 = (LinearLayout)rootview.findViewById(R.id.s4);

        Add_controls();

        ad.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Add_controls();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete_controls();
            }
        });




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

    public void initialize()
    {
        ad=(CircleButton) rootview.findViewById(R.id.ad);
        delete=(CircleButton) rootview.findViewById(R.id.delete);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void Add_controls()
    {

        try
        {
            k++;
            flag=k;
            final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(380, LinearLayout.LayoutParams.WRAP_CONTENT);
            lparams.setMargins(1, 20, 1, 0);
            colorSpinner[flag] = new Spinner(getActivity());
            colorSpinner[flag].setLayoutParams(lparams);
            colorSpinner[flag].setId(flag);
            colorSpinner[flag].setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, color_list));

            loadColorSpinnerData(URL);

            colorSpinner[flag].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                {
//                color_name=   color_spinner.getItemAtPosition(color_spinner.getSelectedItemPosition()).toString();
                    color_id = datalist1.get(i).getId();
//                Toast.makeText(getContext(),"Id   " +color_id , Toast.LENGTH_LONG).show();
                    loadPartnoSpinnerData(URL+"api/part?color_id="+color_id.toString());


                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // DO Nothing here
                }
            });



            k1++;
            flag1=k1;
            final LinearLayout.LayoutParams lparams2 = new LinearLayout.LayoutParams(380, LinearLayout.LayoutParams.WRAP_CONTENT);
            lparams2.setMargins(1, 20, 1, 0);
            partSpinner[flag1] = new Spinner(getActivity());
            partSpinner[flag1].setLayoutParams(lparams2);
            partSpinner[flag1].setId(flag1);
            partSpinner[flag1].setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, part_list));

            partSpinner[flag1].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
//                part_no=   partno_spinner.getItemAtPosition(partno_spinner.getSelectedItemPosition()).toString();
                partno_id = datalist2.get(i).getId();
//                Toast.makeText(getContext(),"Id   " +partno_id , Toast.LENGTH_LONG).show();
                loadLtrSpinnerData(URL+"api/ltr?id="+partno_id.toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });


            k2++;
            flag2=k2;
            final LinearLayout.LayoutParams lparams1 = new LinearLayout.LayoutParams(280, LinearLayout.LayoutParams.WRAP_CONTENT);
            lparams1.setMargins(1, 20, 1, 0);
            textView1[flag2] = new EditText(getActivity());
            textView1[flag2].setLayoutParams(lparams1);
            textView1[flag2].setHint("Enter Qty");
            textView1[flag2].setTooltipText("Hello");
            textView1[flag2].setId(flag2);



            k3++;
            flag3=k3;
            final LinearLayout.LayoutParams lparams3 = new LinearLayout.LayoutParams(250, LinearLayout.LayoutParams.WRAP_CONTENT);
            lparams3.setMargins(1, 20, 1, 0);
            ltrSpinner[flag3] = new Spinner(getActivity());
            ltrSpinner[flag3].setLayoutParams(lparams3);
            ltrSpinner[flag3].setId(flag3);
            ltrSpinner[flag3].setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, ltr_list));





        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        mLayout.addView(colorSpinner[flag]);
        mLayout1.addView(partSpinner[flag1]);
        mLayout2.addView(textView1[flag2]);
        mLayout3.addView(ltrSpinner[flag3]);


        color_array.add(colorSpinner[flag]);
        part_array.add(partSpinner[flag1]);
        qty.add(textView1[flag2]);
        ltr_array.add(ltrSpinner[flag3]);






    }

    public void Delete_controls()
    {
        mLayout.removeViewAt(mLayout.getChildCount()-1);
        mLayout1.removeViewAt(mLayout1.getChildCount()-1);
        mLayout2.removeViewAt(mLayout2.getChildCount()-1);
        mLayout3.removeViewAt(mLayout3.getChildCount()-1);
//        mLayout.removeView(colorSpinner[flag]);
//        mLayout1.removeView(partSpinner[flag1]);
//        mLayout2.removeView(textView1[flag2]);
//        mLayout3.removeView(ltrSpinner[flag3]);

        color_array.remove(colorSpinner[flag]);
        part_array.remove(partSpinner[flag1]);
        qty.remove(textView1[flag2]);
        ltr_array.remove(ltrSpinner[flag3]);
    }

    private void loadBrandSpinnerData(String url)
    {
        pDialog.setMessage("Please Wait ...");
        showDialog();

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url+"api/brand", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                final ArrayList<String> list = new ArrayList<>();
                list.clear();

                try{
                    GetBrandDataAdapter GetDatadp ;
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("brand");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        GetDatadp = new GetBrandDataAdapter();
                        GetDatadp.setName(jsonObject1.getString("name"));
                        GetDatadp.setId(jsonObject1.getString("id"));
                        datalist.add(GetDatadp);

                        list.add(jsonObject1.getString("name"));

//                        String brand=jsonObject1.getString("name");
//                        String bid=jsonObject1.getString("id");
//                        Brand.add(brand);

                    }

                    brand_spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list));
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


                color_list.clear();
                try{
                    GetColorDataAdapter GetDatadp ;
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("colors");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        GetDatadp = new GetColorDataAdapter();
                        GetDatadp.setName(jsonObject1.getString("name"));
                        GetDatadp.setId(jsonObject1.getString("id"));
                        datalist1.add(GetDatadp);

                        color_list.add(jsonObject1.getString("name"));

                    }

//                    actv.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list));
//                    color_spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, color_list));
                    colorSpinner[flag].setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, color_list));
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
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                part_list.clear();
                try{
                    GetPartnoDataAdapter GetDatadp ;
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("parts");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        GetDatadp = new GetPartnoDataAdapter();
                        GetDatadp.setName(jsonObject1.getString("part_no"));
                        GetDatadp.setId(jsonObject1.getString("id"));
                        datalist2.add(GetDatadp);

                        part_list.add(jsonObject1.getString("part_no"));

                    }

//                    partno_spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, part_list));
                    partSpinner[flag1].setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, part_list));
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

    private void loadLtrSpinnerData(String url)
    {
        pDialog.setMessage("Please Wait ...");
        showDialog();

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                ltr_list.clear();
                try{
                    GetLtrDataAdapter GetDatadp ;
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("ltr");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        GetDatadp = new GetLtrDataAdapter();
                        GetDatadp.setName(jsonObject1.getString("ltr"));
                        GetDatadp.setId(jsonObject1.getString("id"));
                        datalist4.add(GetDatadp);

                        ltr_list.add(jsonObject1.getString("ltr"));

                    }

                    ltrSpinner[flag3].setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, ltr_list));
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

                    String order_no = jsonObject.getString("date");

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


    private void loadOrderData(String url)
    {
        pDialog.setMessage("Please Wait ...");
        showDialog();

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url+"api/view_order", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                final ArrayList<String> list = new ArrayList<>();
                list.clear();
                try{
                    GetOrderDataAdapter GetDatadp ;
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("view_order");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        GetDatadp = new GetOrderDataAdapter();
                        GetDatadp.setName(jsonObject1.getString("party_name"));
                        GetDatadp.setId(jsonObject1.getString("id"));
                        datalist3.add(GetDatadp);

                        list.add(jsonObject1.getString("party_name"));

                    }

                    actv.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list));
//                    color_spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list));
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



    public void GetValueFromEditText()
    {

        hUid=User_id.toString();
        hBrandid=brand_id.toString();
        hOrderno=orderno.getText().toString();
        hDate=sDate.toString();
        hPartyname=actv.getText().toString().trim();
        hCityname=CityName.getText().toString().trim();
//        hColorid=color_id.toString();
//        hPartnoid=partno_id.toString();
//        hQty=Qty.getText().toString().trim();
//        hLtr=Ltr.getText().toString().trim();
        hTotal=Ltr.getText().toString().trim();
        hDis=Discount.getText().toString().trim();
        hRemark=Remark.getText().toString().trim();

    }

    public void Add_Order()
    {
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        GetValueFromEditText();



//                Toast.makeText(getContext(),"Response:-"+hTotal+hDis+hRemark  , Toast.LENGTH_LONG).show();

        // Creating string request with post method.
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, HttpUrl+"?user_id="+hUid+"&brand_name="+hBrandid+"&order_no="+hOrderno+"&current_date="+hDate+"&party_name="+hPartyname
                +"&city="+hCityname+"&color_id="+hColorid+"&part_id="+hPartnoid+"&qty="+hQty+"&ltr="+hLtr+"&total="+hTotal+"&discount="+hDis+"&remarks="+hRemark,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
//                        Toast.makeText(getActivity(), ServerResponse, Toast.LENGTH_LONG).show();
                        new FancyGifDialog.Builder(getActivity())
                                .setTitle("Order Added Successfully")
                                .setMessage("Do you want to Add Another")
                                .setNegativeBtnText("No")
                                .setPositiveBtnBackground("#FF4081")
                                .setPositiveBtnText("Yes")
                                .setNegativeBtnBackground("#FFA9A7A8")
                                .setGifResource(R.drawable.check)   //Pass your Gif here
                                .isCancellable(true)
                                .OnPositiveClicked(new FancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {
//                                        Toast.makeText(getActivity(),"Ok",Toast.LENGTH_SHORT).show();
                                        FragmentTransaction transection=getFragmentManager().beginTransaction();
                                        NewOrderFragment mfragment=new NewOrderFragment();
                                        transection.replace(R.id.container, mfragment);
                                        transection.addToBackStack(null).commit();
                                    }
                                })
                                .OnNegativeClicked(new FancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {
//
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .build();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.

                //Company
                params.put("user_id", hUid);
                params.put("brand_name", hBrandid);
                params.put("order_no", hOrderno);
                params.put("current_date", hDate);
                params.put("party_name", hPartyname);
                params.put("city", hCityname);
                params.put("color_id", hColorid);
                params.put("part_id", hPartnoid);
                params.put("qty", hQty);
                params.put("ltr", hLtr);
                params.put("total", hTotal);
                params.put("discount", hDis);
                params.put("remarks", hRemark);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest1);
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
