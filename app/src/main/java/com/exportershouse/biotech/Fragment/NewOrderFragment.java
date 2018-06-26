package com.exportershouse.biotech.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import com.exportershouse.biotech.Adapter.GetOtherBrandDataAdapter;
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
    LinearLayout t1,t2;

    Spinner brand_spinner,Otherbrand_spinner,color_spinner,partno_spinner,Test;
    final ArrayList<GetBrandDataAdapter> datalist = new ArrayList<GetBrandDataAdapter>();
    final ArrayList<GetOtherBrandDataAdapter> datalist5 = new ArrayList<>();
    final ArrayList<GetColorDataAdapter> datalist1 = new ArrayList<>();
    final ArrayList<GetPartnoDataAdapter> datalist2 = new ArrayList<>();
    final ArrayList<GetOrderDataAdapter> datalist3 = new ArrayList<>();
    final ArrayList<GetLtrDataAdapter> datalist4 = new ArrayList<>();


    public ArrayList<String> color_list = new ArrayList<>();
    public ArrayList<String> part_list = new ArrayList<>();
    public ArrayList<String> ltr_list = new ArrayList<>();

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

    private LinearLayout  mLayout2;
    int k2 = -1;
    int flag2;
    public static EditText textView1[] = new EditText[100];
    public List<EditText> qty = new ArrayList<EditText>();

    private LinearLayout  mLayout3;
    int k3 = -1;
    int flag3;
    public static TextView ltrSpinner[] = new TextView[100];
    public List<Spinner> ltr_array = new ArrayList<Spinner>();






    String brand_name,color_name,part_no;
    String color_id,brand_id,partno_id;
    String URL;
    public String Order_no="BIO/1803/1101",sDate,Ono,test;

    ArrayList<String> Brand,color,partno;




    TextView orderno,cdate;

    AutoCompleteTextView actv;

    TextView Tparty_name,Tcity,Tname,Torderby,Tdiscount,Tremark;

    EditText Ltr,CityName,Trsnsportname,orderBy,Discount,Remark;
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


        initialize();

        EditFocus();


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
        t1= (LinearLayout) rootview.findViewById(R.id.input_brandname);
        t2= (LinearLayout) rootview.findViewById(R.id.input_otherBrand);
        Layout2.setVisibility(LinearLayout.GONE);
        submit.setVisibility(Button.GONE);
        t2.setVisibility(LinearLayout.GONE);

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
                t1.setVisibility(LinearLayout.GONE);
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
                t1.setVisibility(LinearLayout.VISIBLE);
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
                int id;
                id=Integer.parseInt(brand_id);
//                Toast.makeText(getContext(),"Id   " +brand_id , Toast.LENGTH_LONG).show();

                if(id==3)
                {
                    t2.setVisibility(LinearLayout.VISIBLE);
                }
                else {
                    t2.setVisibility(LinearLayout.GONE);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

//        if(brand_id=="3")
//        {
//            t2.setVisibility(LinearLayout.VISIBLE);
//        }

        Otherbrand_spinner=(Spinner)rootview.findViewById(R.id.Otherbrand_spinner);

        loadOtherBrandSpinnerData(URL);

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
//        mLayout3 = (LinearLayout)rootview.findViewById(R.id.s4);

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



//        CityName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(actionId == EditorInfo.IME_ACTION_DONE){
//                    //do stuff
//
//                    Toast.makeText(getActivity(), CityName.getText(), Toast.LENGTH_SHORT).show();
//                    Trsnsportname.setText(CityName.getText());
//                    return true;
//                }
//                return false;
//            }
//        });



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

        actv = (AutoCompleteTextView) rootview.findViewById(R.id.party_name);
        actv.setThreshold(1);//will start working from first character
        actv.setTextColor(Color.BLACK);

        CityName = (EditText) rootview.findViewById(R.id.city_name);
        Trsnsportname = (EditText) rootview.findViewById(R.id.trans_name);
        orderBy = (EditText) rootview.findViewById(R.id.orderby);
        Discount=(EditText)rootview.findViewById(R.id.input_discount);
        Remark=(EditText)rootview.findViewById(R.id.input_remark);


        Tparty_name=(TextView)rootview.findViewById(R.id.Tparty_name);
        Tcity=(TextView)rootview.findViewById(R.id.Tcity);
        Tname=(TextView)rootview.findViewById(R.id.Tname);
        Torderby=(TextView)rootview.findViewById(R.id.Torderby);
        Tdiscount=(TextView)rootview.findViewById(R.id.Tdiscount);
        Tremark=(TextView)rootview.findViewById(R.id.Tremark);
    }

    public void EditFocus()
    {
        actv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Tparty_name.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (actv.getText().length() > 0)
                        Tparty_name.setVisibility(View.VISIBLE);
                    else
                        Tparty_name.setVisibility(View.INVISIBLE);
                }
            }
        });

        CityName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                    if (CityName.getText().length() > 0)
                        Tcity.setVisibility(View.VISIBLE);
                    else
                        Tcity.setVisibility(View.INVISIBLE);
                }
            }
        });

        Trsnsportname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Tname.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (Trsnsportname.getText().length() > 0)
                        Tname.setVisibility(View.VISIBLE);
                    else
                        Tname.setVisibility(View.INVISIBLE);
                }
            }
        });

        orderBy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Torderby.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (orderBy.getText().length() > 0)
                        Torderby.setVisibility(View.VISIBLE);
                    else
                        Torderby.setVisibility(View.INVISIBLE);
                }
            }
        });

        Discount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // Show white background behind floating label
                            Tdiscount.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                } else {
                    // Required to show/hide white background behind floating label during focus change
                    if (Discount.getText().length() > 0)
                        Tdiscount.setVisibility(View.VISIBLE);
                    else
                        Tdiscount.setVisibility(View.INVISIBLE);
                }
            }
        });


        Remark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                    if (Remark.getText().length() > 0)
                        Tremark.setVisibility(View.VISIBLE);
                    else
                        Tremark.setVisibility(View.INVISIBLE);
                }
            }
        });

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
            final LinearLayout.LayoutParams lparams1 = new LinearLayout.LayoutParams(380, 120);
            lparams1.setMargins(1, 20, 1, 0);
            textView1[flag2] = new EditText(getActivity());
            textView1[flag2].setLayoutParams(lparams1);
            textView1[flag2].setHint("Enter Qty");
            textView1[flag2].setInputType(InputType.TYPE_CLASS_NUMBER);
            textView1[flag2].setBackgroundResource(R.drawable.rounded_border_edittext);
            textView1[flag2].setTooltipText("Hello");
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

                    String c;
                    c=textView1[flag2].getText().toString();
                    int a=20,b;
                    b=Integer.parseInt(c);
                    int total;
                    total=b*a;
                    ltrSpinner[flag3].setText(Integer.toString(total));
                }
            });


            k3++;
            flag3=k3;
            final LinearLayout.LayoutParams lparams3 = new LinearLayout.LayoutParams(380,120);
            lparams3.setMargins(1, 20, 1, 0);
            ltrSpinner[flag3] = new TextView(getActivity());
            ltrSpinner[flag3].setLayoutParams(lparams3);
            ltrSpinner[flag3].setText("ltr");
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
            text1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 40));
            text1.setText("Select Color");
            text1.setTypeface(null, Typeface.BOLD);
            text1.setTextColor(Color.parseColor("#212121"));
            text1.setPadding(10,0,0,0);

            text2=new TextView(getActivity());
            text2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 40));
            text2.setText("Select Part no");
            text2.setTypeface(null, Typeface.BOLD);
            text2.setTextColor(Color.parseColor("#212121"));
            text2.setPadding(10,0,0,0);

            text3=new TextView(getActivity());
            text3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 40));
            text3.setText("Enter QTY");
            text3.setTypeface(null, Typeface.BOLD);
            text3.setTextColor(Color.parseColor("#212121"));
            text3.setPadding(10,0,0,0);

            text4=new TextView(getActivity());
            text4.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 40));
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
//        ltr_array.add(ltrSpinner[flag3]);






    }

    public void Delete_controls()
    {
        mLayout.removeViewAt(mLayout.getChildCount()-1);
        mLayout1.removeViewAt(mLayout1.getChildCount()-1);

//        mLayout2.removeViewAt(mLayout2.getChildCount()-1);
//        mLayout3.removeViewAt(mLayout3.getChildCount()-1);
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

    private void loadOtherBrandSpinnerData(String url)
    {
        pDialog.setMessage("Please Wait ...");
        showDialog();

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url+"api/view_other_company", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                final ArrayList<String> list = new ArrayList<>();
                list.clear();

                try{
                    GetOtherBrandDataAdapter GetDatadp ;
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("view_other_company");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);

                        GetDatadp = new GetOtherBrandDataAdapter();
                        GetDatadp.setName(jsonObject1.getString("name"));
                        GetDatadp.setId(jsonObject1.getString("id"));
                        datalist5.add(GetDatadp);

                        list.add(jsonObject1.getString("name"));

//                        String brand=jsonObject1.getString("name");
//                        String bid=jsonObject1.getString("id");
//                        Brand.add(brand);

                    }

                    Otherbrand_spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list));
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

//                    ltrSpinner[flag3].setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, ltr_list));
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
