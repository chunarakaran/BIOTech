package com.exportershouse.biotech.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.exportershouse.biotech.Adapter.GetStateDataAdapter;
import com.exportershouse.biotech.MainActivity;
import com.exportershouse.biotech.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import at.markushi.ui.CircleButton;

/**
 * Created by Shrey on 24-04-2018.
 */

public class ReportingFragment extends Fragment implements View.OnClickListener {

    View rootview;

    private ProgressDialog pDialog;

    EditText distName,pName,Address,city,pincode,district,email,CperName,CperMob,CperWA,BrandReason,BranName,PartyReason,remark;

    private RadioGroup radioGroup,radioGroup1,radioGroup2;
    private RadioButton radioButton,radioButton1,radioButton2;


    LinearLayout Layout1,Layout2,Layout3,Layout4;
    TextView cdate,cday,ctime;
    Spinner statespinner,nameFirm;
    final ArrayList<GetStateDataAdapter> statedatalist = new ArrayList<>();

    String URL;

    String Sstateid;

    CircleButton ad,delete;
    private Button submit;


    String User_id;
    public static final String PREFS_NAME = "login";
    int uid;


    private LinearLayout mLayout;
    int k = -1;
    int flag;
    public static EditText textView[] = new EditText[100];
    public List<EditText> part = new ArrayList<EditText>();


    private LinearLayout  mLayout1;
    int k1 = -1;
    int flag1;
    public static EditText textView1[] = new EditText[100];
    public List<EditText> qty = new ArrayList<EditText>();

    private LinearLayout  mLayout2;
    int k2 = -1;
    int flag2;
    public static EditText textView2[] = new EditText[100];
    public List<EditText> TotalLtr = new ArrayList<EditText>();

    String SnameFirm;

    String Huid,Hdate,Hday,Htime,HdistName,HpName,Haddress,Hcity,Hpincode,Hdistrict,Hstate,Houtlet,Hemail,HperName,HcoperMob,HcoperWA,Hbrand,HbrandReason,HotherBrand,HpartyOrder,HpartyReason,
            Hpartno,Hqty,Hltr,Hremark,HnameFirm;

    //volley
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String HttpUrl = "http://biotechautomfg.com/api/add_dailyreport";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_reporting, container, false);

        SharedPreferences sp = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        User_id = sp.getString("User", "");

        getActivity().setTitle("Reporting");
        ((MainActivity) getActivity()).hideBottomNavigationButton();

        URL = getString(R.string.url);

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(getActivity());
        progressDialog = new ProgressDialog(getActivity());

        initialize();

        loadState_SpinnerData(URL);

        statespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long row_id)
            {
                Sstateid = statedatalist.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        nameFirm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long row_id)
            {
                SnameFirm = nameFirm.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });



        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        cdate=(TextView)rootview.findViewById(R.id.date);
        cdate.setText(formattedDate);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        cday=(TextView)rootview.findViewById(R.id.day);
        cday.setText(dayOfTheWeek);


        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+05:30"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("KK:mm");
        date.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
        String localTime = date.format(currentLocalTime);
        ctime=(TextView)rootview.findViewById(R.id.time);
        ctime.setText(localTime);

        rootview.findViewById(R.id.radioyes).setOnClickListener(this);
        rootview.findViewById(R.id.radioNo).setOnClickListener(this);
        rootview.findViewById(R.id.partyyes).setOnClickListener(this);
        rootview.findViewById(R.id.partyNo).setOnClickListener(this);

        Layout1=(LinearLayout)rootview.findViewById(R.id.l1);
        Layout2=(LinearLayout)rootview.findViewById(R.id.l2);
        Layout3=(LinearLayout)rootview.findViewById(R.id.sl1);
        Layout4=(LinearLayout)rootview.findViewById(R.id.sl2);
        Layout1.setVisibility(LinearLayout.GONE);
        Layout3.setVisibility(LinearLayout.GONE);

        submit=(Button)rootview.findViewById(R.id.btn_submit);
        ad=(CircleButton) rootview.findViewById(R.id.ad);
        delete=(CircleButton) rootview.findViewById(R.id.delete);



        mLayout = (LinearLayout)rootview.findViewById(R.id.s1);
        mLayout1 = (LinearLayout)rootview.findViewById(R.id.s2);
        mLayout2 = (LinearLayout)rootview.findViewById(R.id.s3);


        try
        {
            k++;
            flag=k;
            final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(320, LinearLayout.LayoutParams.WRAP_CONTENT);
            lparams.setMargins(1, 20, 1, 0);
            textView[flag] = new EditText(getActivity());
            textView[flag].setLayoutParams(lparams);
            textView[flag].setHint("Enter Part no");
            textView[flag].setId(flag);

            k1++;
            flag1=k1;
            final LinearLayout.LayoutParams lparams1 = new LinearLayout.LayoutParams(150, LinearLayout.LayoutParams.WRAP_CONTENT);
            lparams1.setMargins(1, 20, 1, 0);
            textView1[flag1] = new EditText(getActivity());
            textView1[flag1].setLayoutParams(lparams1);
            textView1[flag1].setHint("Qty");
            textView1[flag1].setId(flag1);

            k2++;
            flag2=k2;
            final LinearLayout.LayoutParams lparams2 = new LinearLayout.LayoutParams(210, LinearLayout.LayoutParams.WRAP_CONTENT);
            lparams2.setMargins(1, 20, 1, 0);
            textView2[flag2] = new EditText(getActivity());
            textView2[flag2].setLayoutParams(lparams2);
            textView2[flag2].setHint("Total Ltr");
            textView2[flag2].setId(flag2);


        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        mLayout.addView(textView[flag]);
        mLayout1.addView(textView1[flag1]);
        mLayout2.addView(textView2[flag2]);

        part.add(textView[flag]);
        qty.add(textView1[flag1]);
        TotalLtr.add(textView2[flag2]);

        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    k++;
                    flag=k;
                    final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(300, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lparams.setMargins(1, 20, 1, 0);
                    textView[flag] = new EditText(getActivity());
                    textView[flag].setLayoutParams(lparams);
                    textView[flag].setHint("Enter Part no");
                    textView[flag].setId(flag);



                    k1++;
                    flag1=k1;
                    final LinearLayout.LayoutParams lparams1 = new LinearLayout.LayoutParams(130, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lparams1.setMargins(1, 20, 1, 0);
                    textView1[flag1] = new EditText(getActivity());
                    textView1[flag1].setLayoutParams(lparams1);
                    textView1[flag1].setHint("Qty");
                    textView1[flag1].setId(flag1);

                    k2++;
                    flag2=k2;
                    final LinearLayout.LayoutParams lparams2 = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lparams2.setMargins(1, 20, 1, 0);
                    textView2[flag2] = new EditText(getActivity());
                    textView2[flag2].setLayoutParams(lparams2);
                    textView2[flag2].setHint("Total Ltr");
                    textView2[flag2].setId(flag2);


                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                mLayout.addView(textView[flag]);
                mLayout1.addView(textView1[flag1]);
                mLayout2.addView(textView2[flag2]);

                part.add(textView[flag]);
                qty.add(textView1[flag1]);
                TotalLtr.add(textView2[flag2]);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLayout.removeView(textView[flag]);
                mLayout1.removeView(textView1[flag1]);
                mLayout2.removeView(textView2[flag2]);

                part.remove(textView[flag]);
                qty.remove(textView1[flag1]);
                TotalLtr.remove(textView2[flag2]);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetValueFromEditText();

//                remark.setText(Hltr);
//                Toast.makeText(getActivity(), Hpartno, Toast.LENGTH_LONG).show();

                Add_report();
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

    @Override
    public void onClick(View view)
    {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioyes:
                if (checked)
                    Layout2.setVisibility(LinearLayout.VISIBLE);
                Layout1.setVisibility(LinearLayout.GONE);
                break;
            case R.id.radioNo:
                if (checked)
                    Layout1.setVisibility(LinearLayout.VISIBLE);
//                Layout2.setVisibility(LinearLayout.GONE);
                break;

            case R.id.partyyes:
                if (checked)
                    Layout4.setVisibility(LinearLayout.VISIBLE);
                Layout3.setVisibility(LinearLayout.GONE);
                break;

            case R.id.partyNo:
                if (checked)
                    Layout3.setVisibility(LinearLayout.VISIBLE);
//                Layout4.setVisibility(LinearLayout.GONE);
                break;
        }
    }


    public void initialize()
    {

        nameFirm=(Spinner)rootview.findViewById(R.id.name_Cperson);

        statespinner=(Spinner)rootview.findViewById(R.id.state_spinner);

        distName=(EditText)rootview.findViewById(R.id.input_dname);
        pName=(EditText)rootview.findViewById(R.id.input_pname);
        Address=(EditText)rootview.findViewById(R.id.input_address);
        city=(EditText)rootview.findViewById(R.id.input_city);
        pincode=(EditText)rootview.findViewById(R.id.input_pincode);
        district=(EditText)rootview.findViewById(R.id.input_dis);
        email=(EditText)rootview.findViewById(R.id.input_email);
        CperName=(EditText)rootview.findViewById(R.id.input_nameofcontper);
        CperMob=(EditText)rootview.findViewById(R.id.input_mboofcontper);
        CperWA=(EditText)rootview.findViewById(R.id.input_Wamboofcontper);
        BrandReason=(EditText)rootview.findViewById(R.id.input_reason);
        BranName=(EditText)rootview.findViewById(R.id.input_Othbrandname);
        PartyReason=(EditText)rootview.findViewById(R.id.input_reason2);
        remark=(EditText)rootview.findViewById(R.id.input_remark);
    }

    private void loadState_SpinnerData(String url)
    {
        pDialog.setMessage("Please Wait ...");
        showDialog();

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url+"api/view_state", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                final ArrayList<String> list = new ArrayList<>();

                list.clear();

                try{
                    GetStateDataAdapter GetDatadp ;
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject jsonObject1;
                    JSONArray jsonArray=jsonObject.getJSONArray("state");
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject1=jsonArray.getJSONObject(i);

                        GetDatadp = new GetStateDataAdapter();
                        GetDatadp.setName(jsonObject1.getString("state"));
                        GetDatadp.setId(jsonObject1.getString("id"));
                        statedatalist.add(GetDatadp);

                        list.add(jsonObject1.getString("state"));

                    }
                    statespinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list));
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
        radioGroup = (RadioGroup)rootview.findViewById(R.id.radio);
        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton)rootview.findViewById(selectedId);

        radioGroup1 = (RadioGroup)rootview.findViewById(R.id.radio1);
        // get selected radio button from radioGroup
        int selectedId1 = radioGroup1.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton1 = (RadioButton)rootview.findViewById(selectedId1);

        radioGroup2 = (RadioGroup)rootview.findViewById(R.id.radio2);
        // get selected radio button from radioGroup
        int selectedId2 = radioGroup2.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton2 = (RadioButton)rootview.findViewById(selectedId2);

        Huid=User_id.toString();

        Hdate=cdate.getText().toString();
        Hday=cday.getText().toString();
        Htime=ctime.getText().toString();

        HnameFirm=SnameFirm;

        HdistName=distName.getText().toString();
        HpName=pName.getText().toString();
        Haddress=Address.getText().toString();
        Hcity=city.getText().toString();
        Hpincode=pincode.getText().toString();
        Hdistrict=district.getText().toString();
        Hemail=email.getText().toString();
        HperName=CperName.getText().toString();
        HcoperMob=CperMob.getText().toString();
        HcoperWA=CperWA.getText().toString();
        HbrandReason=BrandReason.getText().toString();
        HotherBrand=BranName.getText().toString();
        HpartyReason=PartyReason.getText().toString();
        Hremark=remark.getText().toString();

        Hstate=Sstateid.toString();

        Houtlet=radioButton.getText().toString();
        Hbrand=radioButton1.getText().toString();
        HpartyOrder=radioButton2.getText().toString();


        String[] partno = new String[part.size()];
        for(int i = 0; i < part.size(); i++){
            partno[i]=part.get(i).getText().toString();
            Hpartno=Arrays.toString(partno);
        }

        String[] Qty = new String[qty.size()];
        for(int i = 0; i < qty.size(); i++){
            Qty[i]=qty.get(i).getText().toString();
            Hqty=Arrays.toString(Qty);
        }

        String[] Ltr = new String[TotalLtr.size()];
        for(int i = 0; i < TotalLtr.size(); i++){
            Ltr[i]=TotalLtr.get(i).getText().toString();
            Hltr=Arrays.toString(Ltr);
        }



    }


    public void Add_report()
    {
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        GetValueFromEditText();

        // Creating string request with post method.
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
                        Toast.makeText(getActivity(), ServerResponse, Toast.LENGTH_LONG).show();

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
                params.put("emp_id", Huid);

                params.put("date", Hdate);
                params.put("day", Hday);
                params.put("time", Htime);

                params.put("distributer_name", HdistName);
                params.put("party_firm_name", HpName);
                params.put("address", Haddress);
                params.put("city", Hcity);
                params.put("pincode", Hpincode);
                params.put("district", Hdistrict);
                params.put("email_address", Hemail);
                params.put("contact_person_name", HnameFirm+" "+HperName);
                params.put("contact_person_no", HcoperMob);
                params.put("contact_person_whatsapp_no", HcoperWA);
                params.put("brand_reason", HbrandReason);
                params.put("other_brand_name", HotherBrand);
                params.put("order_reason", HpartyReason);
                params.put("remarks", Hremark);

                params.put("state_id", Hstate);

                params.put("available_outlet", Houtlet);
                params.put("used_our_brand", Hbrand);
                params.put("party_order", HpartyOrder);

//                params.put("part_no", Hpartno);
//                params.put("qty", Hqty);
//                params.put("total_ltr", Hltr);

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
