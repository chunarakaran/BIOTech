package com.exportershouse.biotech.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.exportershouse.biotech.Adapter.GetBrandDataAdapter;
import com.exportershouse.biotech.Adapter.GetColorDataAdapter;
import com.exportershouse.biotech.Adapter.GetLtrDataAdapter;
import com.exportershouse.biotech.Adapter.GetPartnoDataAdapter;
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

    Spinner brand_spinner;

    final ArrayList<GetBrandDataAdapter> datalist = new ArrayList<GetBrandDataAdapter>();
    final ArrayList<GetColorDataAdapter> datalist1 = new ArrayList<>();
    final ArrayList<GetPartnoDataAdapter> datalist2 = new ArrayList<>();

    public ArrayList<String> color_list = new ArrayList<>();
    public ArrayList<String> part_list = new ArrayList<>();
    public ArrayList<String> ltr_list = new ArrayList<>();
    public ArrayList<String> list = new ArrayList<String>();

    TextView Tdname,Tpname,Taddress,Tcity,Tpincode,Tdis,Temail,Tnameofcontper,Tmboofcontper,TWamboofcontper,Treason,TOthbrandname,Treason2,Tremark;

    EditText distName,pName,Address,city,pincode,district,email,CperName,CperMob,CperWA,BrandReason,BranName,PartyReason,remark;

    private RadioGroup radioGroup,radioGroup1,radioGroup2;
    private RadioButton radioButton,radioButton1,radioButton2;


    View Layout7;
    LinearLayout Layout1,Layout2,Layout3,Layout4,Layout5,Layout6;
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


    TextView Tltr;

    TextView text1,text2,text3,text4;
    View view1,view2,view3,view4;
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


    int k2 = -1;
    int flag2;
    public static EditText textView1[] = new EditText[100];
    public List<EditText> qty = new ArrayList<EditText>();

    int k3 = -1;
    int flag3;
    public static TextView ltrSpinner[] = new TextView[100];
    public List<TextView> ltr_array = new ArrayList<TextView>();


    String brand_name,color_name,part_no;
    String color_id,brand_id,otherbrand_id,partno_id;





    String SnameFirm;

    String Huid,Hdate,Hday,Htime,HdistName,HpName,Haddress,Hcity,Hpincode,Hdistrict,Hstate,Houtlet,Hemail,HperName,HcoperMob,HcoperWA,Hbrand,HbrandReason,HotherBrand,HpartyOrder,HpartyReason,
            Hpartno,Hqty,Hltr,Hremark,HnameFirm;

    String[] str = new String[10];
    String[] str1 = new String[10];
    String[] str2 = new String[10];

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
//        ((MainActivity) getActivity()).hideBottomNavigationButton();

        URL = getString(R.string.url);

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(getActivity());
        progressDialog = new ProgressDialog(getActivity());

        initialize();

        EditFocus();

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

        brand_spinner=(Spinner)rootview.findViewById(R.id.brand_spinner);
//        Brand=new ArrayList<>();
        loadBrandSpinnerData(URL);

        brand_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                brand_name=   brand_spinner.getItemAtPosition(brand_spinner.getSelectedItemPosition()).toString();
                brand_id = datalist.get(i).getId();

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
        Layout5=(LinearLayout)rootview.findViewById(R.id.sl3);
        Layout6=(LinearLayout)rootview.findViewById(R.id.input_brandname);
        Layout7=(View) rootview.findViewById(R.id.sl4);
        Layout1.setVisibility(LinearLayout.GONE);
        Layout3.setVisibility(LinearLayout.GONE);


        submit=(Button)rootview.findViewById(R.id.btn_submit);
        ad=(CircleButton) rootview.findViewById(R.id.ad);
        delete=(CircleButton) rootview.findViewById(R.id.delete);


        mLayout = (LinearLayout)rootview.findViewById(R.id.s1);
        mLayout1 = (LinearLayout)rootview.findViewById(R.id.s2);

        Add_controls();

        ad.setOnClickListener(new View.OnClickListener() {
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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation();
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
                Layout5.setVisibility(LinearLayout.VISIBLE);
                Layout3.setVisibility(LinearLayout.GONE);
                Layout6.setVisibility(LinearLayout.VISIBLE);
                Layout7.setVisibility(LinearLayout.VISIBLE);
                break;

            case R.id.partyNo:
                if (checked)
                    Layout3.setVisibility(LinearLayout.VISIBLE);
                    Layout4.setVisibility(LinearLayout.GONE);
                    Layout5.setVisibility(LinearLayout.GONE);
                    Layout6.setVisibility(LinearLayout.GONE);
                Layout7.setVisibility(LinearLayout.GONE);

                break;
        }
    }


    public void initialize()
    {

        Tltr=(TextView)rootview.findViewById(R.id.test);

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

        Tdname=(TextView)rootview.findViewById(R.id.Tdname);
        Tpname=(TextView)rootview.findViewById(R.id.Tpname);
        Taddress=(TextView)rootview.findViewById(R.id.Taddress);
        Tcity=(TextView)rootview.findViewById(R.id.Tcity);
        Tpincode=(TextView)rootview.findViewById(R.id.Tpincode);
        Tdis=(TextView)rootview.findViewById(R.id.Tdis);
        Temail=(TextView)rootview.findViewById(R.id.Temail);
        Tnameofcontper=(TextView)rootview.findViewById(R.id.Tnameofcontper);
        Tmboofcontper=(TextView)rootview.findViewById(R.id.Tmboofcontper);
        TWamboofcontper=(TextView)rootview.findViewById(R.id.TWamboofcontper);
        Treason=(TextView)rootview.findViewById(R.id.Treason);
        TOthbrandname=(TextView)rootview.findViewById(R.id.TOthbrandname);
        Treason2=(TextView)rootview.findViewById(R.id.Treason2);
        Tremark=(TextView)rootview.findViewById(R.id.Tremark);

    }

    public void EditFocus()
    {
        distName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Tdname.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (distName.getText().length() > 0)
                        Tdname.setVisibility(View.VISIBLE);
                    else
                        Tdname.setVisibility(View.INVISIBLE);
                }
            }
        });

        pName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Tpname.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (pName.getText().length() > 0)
                        Tpname.setVisibility(View.VISIBLE);
                    else
                        Tpname.setVisibility(View.INVISIBLE);
                }
            }
        });

        Address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Taddress.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (Address.getText().length() > 0)
                        Taddress.setVisibility(View.VISIBLE);
                    else
                        Taddress.setVisibility(View.INVISIBLE);
                }
            }
        });

        city.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Tcity.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (city.getText().length() > 0)
                        Tcity.setVisibility(View.VISIBLE);
                    else
                        Tcity.setVisibility(View.INVISIBLE);
                }
            }
        });

        pincode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Tpincode.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (pincode.getText().length() > 0)
                        Tpincode.setVisibility(View.VISIBLE);
                    else
                        Tpincode.setVisibility(View.INVISIBLE);
                }
            }
        });

        district.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Tdis.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (district.getText().length() > 0)
                        Tdis.setVisibility(View.VISIBLE);
                    else
                        Tdis.setVisibility(View.INVISIBLE);
                }
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Temail.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (email.getText().length() > 0)
                        Temail.setVisibility(View.VISIBLE);
                    else
                        Temail.setVisibility(View.INVISIBLE);
                }
            }
        });

        CperName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Tnameofcontper.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (CperName.getText().length() > 0)
                        Tnameofcontper.setVisibility(View.VISIBLE);
                    else
                        Tnameofcontper.setVisibility(View.INVISIBLE);
                }
            }
        });

        CperMob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Tmboofcontper.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (CperMob.getText().length() > 0)
                        Tmboofcontper.setVisibility(View.VISIBLE);
                    else
                        Tmboofcontper.setVisibility(View.INVISIBLE);
                }
            }
        });


        CperWA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            TWamboofcontper.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (CperWA.getText().length() > 0)
                        TWamboofcontper.setVisibility(View.VISIBLE);
                    else
                        TWamboofcontper.setVisibility(View.INVISIBLE);
                }
            }
        });

        BrandReason.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Treason.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (BrandReason.getText().length() > 0)
                        Treason.setVisibility(View.VISIBLE);
                    else
                        Treason.setVisibility(View.INVISIBLE);
                }
            }
        });

        BranName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            TOthbrandname.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (BranName.getText().length() > 0)
                        TOthbrandname.setVisibility(View.VISIBLE);
                    else
                        TOthbrandname.setVisibility(View.INVISIBLE);
                }
            }
        });

        PartyReason.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Treason2.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (PartyReason.getText().length() > 0)
                        Treason2.setVisibility(View.VISIBLE);
                    else
                        Treason2.setVisibility(View.INVISIBLE);
                }
            }
        });

        remark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Tremark.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (distName.getText().length() > 0)
                        Tremark.setVisibility(View.VISIBLE);
                    else
                        Tremark.setVisibility(View.INVISIBLE);
                }
            }
        });



    }

    public void Add_controls()
    {
        try
        {
            k++;
            flag=k;
            final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(420,120);
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
                    color_id = datalist1.get(i).getId();

                    adapterView.setTag(color_id);

                    if(color_id.toString().trim()!=null && brand_id !=null)
                    {

                        loadPartnoSpinnerData(URL + "api/part?company_id=" + brand_id.toString() + "&color_id=" + color_id.toString());
                    }


                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // DO Nothing here
                    list.clear();
                }
            });





            k1++;
            flag1=k1;
            final LinearLayout.LayoutParams lparams2 = new LinearLayout.LayoutParams(420,120);
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
            final LinearLayout.LayoutParams lparams1 = new LinearLayout.LayoutParams(380, 120);
            lparams1.setMargins(10, 20, 1, 10);
            textView1[flag2] = new EditText(getActivity());
            textView1[flag2].setLayoutParams(lparams1);
            textView1[flag2].setHint("Enter Qty");
            textView1[flag2].setInputType(InputType.TYPE_CLASS_NUMBER);
            textView1[flag2].setBackgroundResource(R.drawable.rounded_border_edittext);
            textView1[flag2].setPadding(20,0,0,0);
            textView1[flag2].setId(flag2);




            textView1[flag2].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {


                    String c,d;
                    c=textView1[flag2].getText().toString();
                    d=Tltr.getText().toString();
                    int a,b;



                    if (c.toString().trim().length()>0)
                    {

                        b=Integer.parseInt(c);
                        a=Integer.parseInt(d);
                        int total;
                        total=b*a;
                        ltrSpinner[flag3].setText(Integer.toString(total));


                        String[] Bqty = new String[qty.size()];
                        for(int i = 0; i < qty.size(); i++){
                            Bqty[i]=qty.get(i).getText().toString();
                        }

                        int myar[]=new int[Bqty.length];

                        for(int i=0;i<Bqty.length;i++)
                        {
                            myar[i]=Integer.parseInt(Bqty[i]);
                        }

                        int sumqty = 0;

                        for(int i : myar) {
                            sumqty += i;
                        }

//                        dQty.setText(Integer.toString(sumqty));

                        String[] cltr = new String[ltr_array.size()];
                        for(int i = 0; i < ltr_array.size(); i++){
                            cltr[i]=ltr_array.get(i).getText().toString();

                        }

                        int ltar[]=new int[cltr.length];

                        for(int i=0;i<cltr.length;i++)
                        {
                            ltar[i]=Integer.parseInt(cltr[i]);
                        }

                        int sumltr = 0;

                        for(int i : ltar) {
                            sumltr =sumltr + i;
                        }

//                        dLtr.setText(Integer.toString(sumltr));


                    }
                    else {

                        ltrSpinner[flag3].setText("0");
//                        dQty.setText("0");
//                        dLtr.setText("0");
                    }


                }
            });


            k3++;
            flag3=k3;
            final LinearLayout.LayoutParams lparams3 = new LinearLayout.LayoutParams(380,120);
            lparams3.setMargins(1, 20, 1, 10);
            ltrSpinner[flag3] = new TextView(getActivity());
            ltrSpinner[flag3].setLayoutParams(lparams3);
            ltrSpinner[flag3].setText("0");
            ltrSpinner[flag3].setTypeface(null, Typeface.BOLD);
            ltrSpinner[flag3].setTextColor(Color.parseColor("#212121"));
            ltrSpinner[flag3].setPadding(10,0,0,0);
            ltrSpinner[flag3].setId(flag3);

//            ltrSpinner[flag3].setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, ltr_list));

            view1=new View(getActivity());
            view1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3));
            view1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            view2=new View(getActivity());
            view2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3));
            view2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            view3=new View(getActivity());
            view3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3));
            view3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            view4=new View(getActivity());
            view4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 3));
            view4.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            text1=new TextView(getActivity());
            text1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 60));
            text1.setText("Select Color");
            text1.setTypeface(null, Typeface.BOLD);
            text1.setTextColor(Color.parseColor("#212121"));
            text1.setPadding(10,0,0,0);

            text2=new TextView(getActivity());
            text2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 60));
            text2.setText("Select Part no");
            text2.setTypeface(null, Typeface.BOLD);
            text2.setTextColor(Color.parseColor("#212121"));
            text2.setPadding(10,0,0,0);

            text3=new TextView(getActivity());
            text3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 60));
            text3.setText("Enter QTY");
            text3.setTypeface(null, Typeface.BOLD);
            text3.setTextColor(Color.parseColor("#212121"));
            text3.setPadding(10,0,0,0);

            text4=new TextView(getActivity());
            text4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 60));
            text4.setText("Total LTR");
            text4.setTypeface(null, Typeface.BOLD);
            text4.setTextColor(Color.parseColor("#212121"));
            text4.setPadding(10,0,0,0);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        mLayout.addView(text1);
        mLayout.addView(colorSpinner[flag]);
        mLayout.addView(view1);

        mLayout1.addView(text2);
        mLayout1.addView(partSpinner[flag1]);
        mLayout1.addView(view2);

        mLayout.addView(text3);
        mLayout.addView(textView1[flag2]);
        mLayout.addView(view3);

        mLayout1.addView(text4);
        mLayout1.addView(ltrSpinner[flag3]);
        mLayout1.addView(view4);




        color_array.add(colorSpinner[flag]);
        part_array.add(partSpinner[flag1]);
        qty.add(textView1[flag2]);
        ltr_array.add(ltrSpinner[flag3]);



    }

    public void Delete_controls()
    {
        mLayout.removeView(colorSpinner[flag]);
        mLayout1.removeView(partSpinner[flag1]);
        mLayout.removeView(textView1[flag2]);
        mLayout1.removeView(ltrSpinner[flag3]);

        mLayout.removeView(text1);
        mLayout1.removeView(text2);
        mLayout.removeView(text3);
        mLayout1.removeView(text4);

        mLayout.removeView(view1);
        mLayout1.removeView(view2);
        mLayout.removeView(view3);
        mLayout1.removeView(view4);

        color_array.remove(colorSpinner[flag]);
        part_array.remove(partSpinner[flag1]);
        qty.remove(textView1[flag2]);
        ltr_array.remove(ltrSpinner[flag3]);

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

                try
                {
                    GetLtrDataAdapter GetDatadp ;
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("ltrs");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        String Ltr = jsonObject1.getString("ltr");
                        Tltr.setText(Ltr);

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


    @SuppressLint("NewApi")
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

        String[] Apartno = new String[part_array.size()];
        for(int i = 0; i < part_array.size(); i++){
            Apartno[i]=part_array.get(i).getSelectedItem().toString();
            Hpartno=TextUtils.join(",",Apartno);
        }

        String[] Aqty = new String[qty.size()];
        for(int i = 0; i < qty.size(); i++){
            Aqty[i]=qty.get(i).getText().toString();
            Hqty=TextUtils.join(",",Aqty);
        }

        String[] Altr = new String[ltr_array.size()];
        for(int i = 0; i < ltr_array.size(); i++){
            Altr[i]=ltr_array.get(i).getText().toString();
            Hltr=TextUtils.join(",",Altr);
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
//                        Toast.makeText(getActivity(), ServerResponse, Toast.LENGTH_LONG).show();

                        new FancyGifDialog.Builder(getActivity())
                                .setTitle("Success")
                                .setMessage("Your Daily Report Add Successfully")
                                .setNegativeBtnText("Cancel")
                                .setPositiveBtnBackground("#FF4081")
                                .setPositiveBtnText("Ok")
                                .setNegativeBtnBackground("#FFA9A7A8")
                                .setGifResource(R.drawable.check)   //Pass your Gif here
                                .isCancellable(false)
                                .OnPositiveClicked(new FancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {
//                                        Toast.makeText(getActivity(),"Ok",Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();

                                    }
                                })
                                .OnNegativeClicked(new FancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {
//
//                                        fromDate.setText("");
//                                        toDate.setText("");
//                                        remark.setText("");
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

                params.put("part_no", Hpartno);
                params.put("qty",Hqty);
                params.put("total_ltr",Hltr);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest1);


    }

    public void Validation()
    {
        final String VdistName,VpName,VAddress,Vcity,Vpincode,Vdistrict,Vemail,VCperName,VCperMob,VCperWA,VBrandReason,VBranName,VPartyReason,Vremark;

        VdistName = distName.getText().toString();
        VpName = pName.getText().toString();
        VAddress = Address.getText().toString();
        Vcity = city.getText().toString();
        Vpincode = pincode.getText().toString();
        Vdistrict = district.getText().toString();
        Vemail = email.getText().toString();
        VCperName = CperName.getText().toString();
        VCperMob = CperMob.getText().toString();
        VCperWA = CperWA.getText().toString();
        VBrandReason = BrandReason.getText().toString();
        VBranName = BranName.getText().toString();
        VPartyReason = PartyReason.getText().toString();
        Vremark = remark.getText().toString();

        if(VdistName.length()==0)
        {
            distName.requestFocus();
            distName.setError("Please Enter Distributor Name");
        }
        else if (!VdistName.matches("^[a-zA-Z ]+$")){
            distName.requestFocus();
            distName.setError("Enter Valid Distributor Name");
        }
        else if(VpName.length()==0)
        {
            pName.requestFocus();
            pName.setError("Please Enter Party Name");
        }
        else if (!VpName.matches("^[a-zA-Z ]+$")){
            pName.requestFocus();
            pName.setError("Enter Valid Party Name");
        }
        else if(VAddress.length()==0)
        {
            Address.requestFocus();
            Address.setError("Please Enter Address");
        }
        else if(Vcity.length()==0)
        {
            city.requestFocus();
            city.setError("Please Enter City Name");
        }
        else if (!Vcity.matches("^[a-zA-Z ]+$")){
            city.requestFocus();
            city.setError("Enter Valid City Name");
        }

        else if (Vpincode.length()==0){
            pincode.requestFocus();
            pincode.setError("Please Enter Pin code");
        }

        else if (Vpincode.length()<6||Vpincode.length()>6||Vpincode.contains(" ")){
            pincode.requestFocus();
            pincode.setError("Invalid Pin code");
        }

        else if(Vdistrict.length()==0)
        {
            district.requestFocus();
            district.setError("Please Enter District Name");
        }
        else if (!Vdistrict.matches("^[a-zA-Z ]+$")){
            district.requestFocus();
            district.setError("Enter Valid District Name");
        }

        else if (Vemail.length()==0){
            email.requestFocus();
            email.setError("Please Enter Email");
        }

        else if (!Vemail.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")||Vemail.contains(" ")){
            email.requestFocus();
            email.setError("Invalid Email Address");
        }

        else if(VCperName.length()==0)
        {
            CperName.requestFocus();
            CperName.setError("Please Enter Name of Contact Person");
        }
        else if (!VCperName.matches("^[a-zA-Z ]+$")){
            CperName.requestFocus();
            CperName.setError("Enter Valid Name of Contact Person");
        }

        else if (VCperMob.length()==0){
            CperMob.requestFocus();
            CperMob.setError("Please Enter Mobile No");
        }
        else if (!VCperMob.matches("^[0-9]{10}$")||VCperMob.contains(" ")){
            CperMob.requestFocus();
            CperMob.setError("Invalid Mobile No");
        }

        else if (VCperWA.length()==0){
            CperWA.requestFocus();
            CperWA.setError("Please Enter Mobile No");
        }
        else if (!VCperWA.matches("^[0-9]{10}$")||VCperWA.contains(" ")){
            CperWA.requestFocus();
            CperWA.setError("Invalid Mobile No");
        }

        else if (Vremark.length()==0)
        {
            remark.requestFocus();
            remark.setError("Please Enter Remark");
        }

        else{
            Add_report();
        }

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
