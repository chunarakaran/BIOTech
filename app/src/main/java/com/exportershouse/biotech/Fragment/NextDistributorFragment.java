package com.exportershouse.biotech.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exportershouse.biotech.MainActivity;
import com.exportershouse.biotech.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shrey on 24-04-2018.
 */

public class NextDistributorFragment extends Fragment implements View.OnClickListener {

    View rootview;

    Fragment fragment = null;

    TextView Tareacovered,Tnumofstaff,Tnumofparty,Treason,TbankName,TbankAcno,TbankBrname,Tbankcity,Tbankifsc,Tchequeno,TpName;

    EditText areaCovered,noOfStaff,noOfParties,reason;
    EditText bankName,bankACno,bankBranchname,bankCity,bankIFSC,bankSecurityCheqNo,bankPersonalName;
    private RadioGroup radioGroup,radioGroup1;
    private RadioButton radioButton,radioButton1,VradioYes,VradioNo;
    CheckBox ck1,ck2,ck3,ck4,ck5;
    String c1,c2,c3,c4,c5;

    Button Submit;

    LinearLayout Layout1,Layout2;

    String Sdate,Siname,Siaddress,Sicity,Sipincode,Sidistrict,Siemail,Silandline,Simobileno,SiFpan_no,SiGST_no,SiFparty_name,SiName_conPerson,SiMobile_conPerson,SiYearlyTarg,SiTrans_name,
            Scompanyname,Sstateid,Spartyname,Sdaysname;

    String Hdate,Hiname,Hiaddress,Hicity,Hipincode,Hidistrict,Hiemail,Hilandline,Himobileno,HiFpan_no,HiGST_no,HiFparty_name,HiName_conPerson,HiMobile_conPerson,HiYearlyTarg,HiTrans_name,
            Hcompanyname,Hstateid,Hpartyname,Hdaysname;

    String HUid,HareaCovered,HnoOfStaff,HnoOfParties,HoutletCovered,Hsecuritycheque,Hdocument,Hreason,HbankName,HbankACno,HbankBranchname,HbankCity,HbankIFSC,HbankSecurityCheqNo,HbankPersonalName;

    String User_id;
    public static final String PREFS_NAME = "login";


    //volley
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String HttpUrl = "http://biotechautomfg.com/api/add_distributor";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SharedPreferences sp = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        User_id = sp.getString("User", "");

        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_nextdistributor, container, false);

        requestQueue = Volley.newRequestQueue(getActivity());
        progressDialog = new ProgressDialog(getActivity());

        rootview.findViewById(R.id.radioyes).setOnClickListener(this);
        rootview.findViewById(R.id.radioNo).setOnClickListener(this);

        initialize();

//        EditFocus();

        Bundle bundle=getArguments();
        Sdate=String.valueOf(bundle.getString("Hdate"));
        Siname=String.valueOf(bundle.getString("HnameFirm"));
        Siaddress=String.valueOf(bundle.getString("Hiaddress"));
        Sicity=String.valueOf(bundle.getString("Hicity"));
        Sipincode=String.valueOf(bundle.getString("Hipincode"));
        Sidistrict=String.valueOf(bundle.getString("Hidistrict"));
        Siemail=String.valueOf(bundle.getString("Hiemail"));
        Silandline=String.valueOf(bundle.getString("Hilandline"));
        Simobileno=String.valueOf(bundle.getString("Himobileno"));
        SiFpan_no=String.valueOf(bundle.getString("HiFpan_no"));
        SiGST_no=String.valueOf(bundle.getString("HiGST_no"));
        SiFparty_name=String.valueOf(bundle.getString("HnameParty"));
        SiName_conPerson=String.valueOf(bundle.getString("HiName_conPerson"));
        SiMobile_conPerson=String.valueOf(bundle.getString("HiMobile_conPerson"));
        SiYearlyTarg=String.valueOf(bundle.getString("HiYearlyTarg"));
        SiTrans_name=String.valueOf(bundle.getString("HiTrans_name"));
        Scompanyname=String.valueOf(bundle.getString("Hcompanyname"));
        Sstateid=String.valueOf(bundle.getString("Hstateid"));
        Spartyname=String.valueOf(bundle.getString("Hpartyname"));
        Sdaysname=String.valueOf(bundle.getString("Hdaysname"));


        Layout1=(LinearLayout)rootview.findViewById(R.id.l1);
        Layout2=(LinearLayout)rootview.findViewById(R.id.l2);
        Layout1.setVisibility(LinearLayout.GONE);


        Submit.setOnClickListener(new View.OnClickListener() {
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
                    Layout2.setVisibility(LinearLayout.GONE);
                    break;
        }
    }

    public void initialize()
    {
        Submit=(Button)rootview.findViewById(R.id.btn_submit);

        VradioYes=(RadioButton)rootview.findViewById(R.id.radioyes);
        VradioNo=(RadioButton)rootview.findViewById(R.id.radioNo);

        areaCovered=(EditText)rootview.findViewById(R.id.input_areacovered);
        noOfStaff=(EditText)rootview.findViewById(R.id.input_numofstaff);
        noOfParties=(EditText)rootview.findViewById(R.id.input_numofparty);

        reason=(EditText)rootview.findViewById(R.id.input_reason);

        bankName=(EditText)rootview.findViewById(R.id.input_bankName);
        bankACno=(EditText)rootview.findViewById(R.id.input_bankAcno);
        bankBranchname=(EditText)rootview.findViewById(R.id.input_bankBrname);
        bankCity=(EditText)rootview.findViewById(R.id.input_bankcity);
        bankIFSC=(EditText)rootview.findViewById(R.id.input_bankifsc);
        bankSecurityCheqNo=(EditText)rootview.findViewById(R.id.input_chequeno);
        bankPersonalName=(EditText)rootview.findViewById(R.id.input_pName);

        ck1=(CheckBox)rootview.findViewById(R.id.c1);
        ck2=(CheckBox)rootview.findViewById(R.id.c2);
        ck3=(CheckBox)rootview.findViewById(R.id.c3);
        ck4=(CheckBox)rootview.findViewById(R.id.c4);
        ck5=(CheckBox)rootview.findViewById(R.id.c5);

//        Tareacovered=(TextView)rootview.findViewById(R.id.Tareacovered);
//        Tnumofstaff=(TextView)rootview.findViewById(R.id.Tnumofstaff);
//        Tnumofparty=(TextView)rootview.findViewById(R.id.Tnumofparty);
//
//        Treason=(TextView)rootview.findViewById(R.id.Treason);
//
//        TbankName=(TextView)rootview.findViewById(R.id.TbankName);
//        TbankAcno=(TextView)rootview.findViewById(R.id.TbankAcno);
//        TbankBrname=(TextView)rootview.findViewById(R.id.TbankBrname);
//        Tbankcity=(TextView)rootview.findViewById(R.id.Tbankcity);
//        Tbankifsc=(TextView)rootview.findViewById(R.id.Tbankifsc);
//        Tchequeno=(TextView)rootview.findViewById(R.id.Tchequeno);
//        TpName=(TextView)rootview.findViewById(R.id.TpName);
    }

//    public void EditFocus()
//    {
//        areaCovered.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if (hasFocus) {
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // Show white background behind floating label
//                            Tareacovered.setVisibility(View.VISIBLE);
//                        }
//                    }, 100);
//                } else {
//                    // Required to show/hide white background behind floating label during focus change
//                    if (areaCovered.getText().length() > 0)
//                        Tareacovered.setVisibility(View.VISIBLE);
//                    else
//                        Tareacovered.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
//
//        noOfStaff.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if (hasFocus) {
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // Show white background behind floating label
//                            Tnumofstaff.setVisibility(View.VISIBLE);
//                        }
//                    }, 100);
//                } else {
//                    // Required to show/hide white background behind floating label during focus change
//                    if (noOfStaff.getText().length() > 0)
//                        Tnumofstaff.setVisibility(View.VISIBLE);
//                    else
//                        Tnumofstaff.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
//
//        noOfParties.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if (hasFocus) {
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // Show white background behind floating label
//                            Tnumofparty.setVisibility(View.VISIBLE);
//                        }
//                    }, 100);
//                } else {
//                    // Required to show/hide white background behind floating label during focus change
//                    if (noOfParties.getText().length() > 0)
//                        Tnumofparty.setVisibility(View.VISIBLE);
//                    else
//                        Tnumofparty.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
//
//        reason.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if (hasFocus) {
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // Show white background behind floating label
//                            Treason.setVisibility(View.VISIBLE);
//                        }
//                    }, 100);
//                } else {
//                    // Required to show/hide white background behind floating label during focus change
//                    if (reason.getText().length() > 0)
//                        Treason.setVisibility(View.VISIBLE);
//                    else
//                        Treason.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
//
//
//        bankName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if (hasFocus) {
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // Show white background behind floating label
//                            TbankName.setVisibility(View.VISIBLE);
//                        }
//                    }, 100);
//                } else {
//                    // Required to show/hide white background behind floating label during focus change
//                    if (bankName.getText().length() > 0)
//                        TbankName.setVisibility(View.VISIBLE);
//                    else
//                        TbankName.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
//
//        bankACno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if (hasFocus) {
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // Show white background behind floating label
//                            TbankAcno.setVisibility(View.VISIBLE);
//                        }
//                    }, 100);
//                } else {
//                    // Required to show/hide white background behind floating label during focus change
//                    if (bankACno.getText().length() > 0)
//                        TbankAcno.setVisibility(View.VISIBLE);
//                    else
//                        TbankAcno.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
//
//        bankBranchname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if (hasFocus) {
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // Show white background behind floating label
//                            TbankBrname.setVisibility(View.VISIBLE);
//                        }
//                    }, 100);
//                } else {
//                    // Required to show/hide white background behind floating label during focus change
//                    if (bankBranchname.getText().length() > 0)
//                        TbankBrname.setVisibility(View.VISIBLE);
//                    else
//                        TbankBrname.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
//
//        bankCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if (hasFocus) {
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // Show white background behind floating label
//                            Tbankcity.setVisibility(View.VISIBLE);
//                        }
//                    }, 100);
//                } else {
//                    // Required to show/hide white background behind floating label during focus change
//                    if (bankCity.getText().length() > 0)
//                        Tbankcity.setVisibility(View.VISIBLE);
//                    else
//                        Tbankcity.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
//
//        bankIFSC.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if (hasFocus) {
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // Show white background behind floating label
//                            Tbankifsc.setVisibility(View.VISIBLE);
//                        }
//                    }, 100);
//                } else {
//                    // Required to show/hide white background behind floating label during focus change
//                    if (bankIFSC.getText().length() > 0)
//                        Tbankifsc.setVisibility(View.VISIBLE);
//                    else
//                        Tbankifsc.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
//
//        bankSecurityCheqNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if (hasFocus) {
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // Show white background behind floating label
//                            Tchequeno.setVisibility(View.VISIBLE);
//                        }
//                    }, 100);
//                } else {
//                    // Required to show/hide white background behind floating label during focus change
//                    if (bankSecurityCheqNo.getText().length() > 0)
//                        Tchequeno.setVisibility(View.VISIBLE);
//                    else
//                        Tchequeno.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
//
//        bankPersonalName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if (hasFocus) {
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            // Show white background behind floating label
//                            TpName.setVisibility(View.VISIBLE);
//                        }
//                    }, 100);
//                } else {
//                    // Required to show/hide white background behind floating label during focus change
//                    if (bankPersonalName.getText().length() > 0)
//                        TpName.setVisibility(View.VISIBLE);
//                    else
//                        TpName.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
//
//
//    }

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

        if(ck1.isChecked()) {
            c1 = ck1.getText().toString();
        }
        else {
            c1="null";
        }
        if (ck2.isChecked()) {
            c2 = ck2.getText().toString();
        }
        else {
            c2="null";
        }
        if (ck3.isChecked()) {
            c3 = ck3.getText().toString();
        }
        else {
            c3="null";
        }
        if (ck4.isChecked()) {
            c4 = ck4.getText().toString();
        }
        else {
            c4="null";
        }
        if (ck5.isChecked()) {
            c5 = ck5.getText().toString();
        }
        else {
            c5="null";
        }

        HUid=User_id.toString();

        Hdate=Sdate;
        Hiname=Siname;
        Hiaddress=Siaddress;
        Hicity=Sicity;
        Hipincode=Sipincode;
        Hidistrict=Sidistrict;
        Hiemail=Siemail;
        Hilandline=Silandline;
        Himobileno=Simobileno;
        HiFpan_no=SiFpan_no;
        HiGST_no=SiGST_no;
        HiFparty_name=SiFparty_name;
        HiName_conPerson=SiName_conPerson;
        HiMobile_conPerson=SiMobile_conPerson;
        HiYearlyTarg=SiYearlyTarg;
        HiTrans_name=SiTrans_name;
        Hcompanyname=Scompanyname;
        Hstateid=Sstateid;
        Hpartyname=Spartyname;
        Hdaysname=Sdaysname;


        HareaCovered=areaCovered.getText().toString().trim();
        HnoOfStaff=noOfStaff.getText().toString().trim();
        HnoOfParties=noOfParties.getText().toString().trim();

        HoutletCovered=radioButton.getText().toString();
        Hsecuritycheque=radioButton1.getText().toString();
        Hdocument=c1+","+c2+","+c3+","+c4+","+c5;

        Hreason=reason.getText().toString().trim();

        HbankName=bankName.getText().toString().trim();
        HbankACno=bankACno.getText().toString().trim();
        HbankBranchname=bankBranchname.getText().toString().trim();
        HbankCity=bankCity.getText().toString().trim();
        HbankIFSC=bankIFSC.getText().toString().trim();
        HbankSecurityCheqNo=bankSecurityCheqNo.getText().toString().trim();
        HbankPersonalName=bankPersonalName.getText().toString().trim();


    }

    public void Add_distributor()
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
                                .setTitle("Distributor Added Successfully")
                                .setMessage("Do you want to Add Another")
                                .setNegativeBtnText("No")
                                .setPositiveBtnBackground("#FF4081")
                                .setPositiveBtnText("Yes")
                                .setNegativeBtnBackground("#FFA9A7A8")
                                .setGifResource(R.drawable.giphy)   //Pass your Gif here
                                .isCancellable(true)
                                .OnPositiveClicked(new FancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {
//                                        Toast.makeText(getActivity(),"Ok",Toast.LENGTH_SHORT).show();
                                        android.support.v4.app.FragmentTransaction transection=getFragmentManager().beginTransaction();
                                        fragment=new DistributorFragment();
                                        transection.replace(R.id.content_frame,fragment);
                                        transection.addToBackStack(null).commit();
                                        getActivity().finish();
                                    }
                                })
                                .OnNegativeClicked(new FancyGifDialogListener() {
                                    @Override
                                    public void OnClick() {
//
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
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
                params.put("user_id", HUid);

                params.put("current_date", Hdate);
                params.put("name_firm", Hiname);
                params.put("address_firm", Hiaddress);
                params.put("city", Hicity);
                params.put("pincode", Hipincode);
                params.put("district", Hidistrict);
                params.put("email", Hiemail);
                params.put("landline_no", Hilandline);
                params.put("mobile_no", Himobileno);
                params.put("PAN_NO", HiFpan_no);
                params.put("GST_NO", HiGST_no);
                params.put("name_pro_per", HiFparty_name);
                params.put("name_contact_person", HiName_conPerson);
                params.put("mobile_contact_person", HiMobile_conPerson);
                params.put("yearly_target", HiYearlyTarg);
                params.put("transport_name", HiTrans_name);
                params.put("company_name", Hcompanyname);
                params.put("state", Hstateid);
                params.put("nature_firm", Hpartyname);
                params.put("market_closing_day", Hdaysname);

                params.put("area_party", HareaCovered);
                params.put("no_staff", HnoOfStaff);
                params.put("no_party_counter", HnoOfParties);
                params.put("outlet_covered_party", HoutletCovered);
                params.put("security_cheque", Hsecuritycheque);

                params.put("documents", Hdocument);

                params.put("reason", Hreason);

                params.put("bank_name", HbankName);
                params.put("bank_account_number", HbankACno);
                params.put("bank_branch_name", HbankBranchname);
                params.put("bank_city", HbankCity);
                params.put("bank_ifsc_code", HbankIFSC);
                params.put("security_cheque_no", HbankSecurityCheqNo);
                params.put("name_security_cheque", HbankPersonalName);

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
        final String VareaCovered,VnoOfStaff,VnoOfParties,Vreason,
                VbankName,VbankACno,VbankBranchname,VbankCity,VbankIFSC,VbankSecurityCheqNo,VbankPersonalName;
        VareaCovered = areaCovered.getText().toString();
        VnoOfStaff = noOfStaff.getText().toString();
        VnoOfParties = noOfParties.getText().toString();
        Vreason = reason.getText().toString();

        VbankName = bankName.getText().toString();
        VbankACno = bankACno.getText().toString();
        VbankBranchname = bankBranchname.getText().toString();
        VbankCity = bankCity.getText().toString();
        VbankIFSC = bankIFSC.getText().toString();
        VbankSecurityCheqNo = bankSecurityCheqNo.getText().toString();
        VbankPersonalName = bankPersonalName.getText().toString();

        if(VareaCovered.length()==0)
        {
            areaCovered.requestFocus();
            areaCovered.setError("Please Enter Area Covered By Party");
        }
        else if (VareaCovered.contains(" ")){
            areaCovered.requestFocus();
            areaCovered.setError("Enter Valid Area Covered By Party");
        }

        else if(VnoOfStaff.length()==0)
        {
            noOfStaff.requestFocus();
            noOfStaff.setError("Please Enter No of Staff");
        }
        else if (VnoOfStaff.contains(" ")){
            noOfStaff.requestFocus();
            noOfStaff.setError("Enter Valid No of Staff");
        }

        else if(VnoOfParties.length()==0)
        {
            noOfParties.requestFocus();
            noOfParties.setError("Please Enter No of Parties");
        }
        else if (VnoOfParties.contains(" ")){
            noOfParties.requestFocus();
            noOfParties.setError("Enter Valid No of Parties");
        }

        else if (VradioNo.isChecked()){
            if(Vreason.length()==0)
            {
                reason.requestFocus();
                reason.setError("Please Enter Reason");
            }
            else {
                Add_distributor();
            }
        }

        else if (VradioYes.isChecked()){

            if(VbankName.length()==0)
            {
                bankName.requestFocus();
                bankName.setError("Please Enter Bank Name");
            }
            else if (!VbankName.matches("^[a-zA-Z ]+$")){
                bankName.requestFocus();
                bankName.setError("Enter Valid Bank Name");
            }

            else if(VbankACno.length()==0)
            {
                bankACno.requestFocus();
                bankACno.setError("Please Enter Bank Account No");
            }
            else if (!VbankACno.matches("^\\d{9,18}$")){
                bankACno.requestFocus();
                bankACno.setError("Enter Valid Bank Account No");
            }

            else if(VbankBranchname.length()==0)
            {
                bankBranchname.requestFocus();
                bankBranchname.setError("Please Enter Bank Branch Name");
            }
            else if (VbankBranchname.contains(" ")){
                bankBranchname.requestFocus();
                bankBranchname.setError("Enter Valid Bank Branch Name");
            }

            else if(VbankCity.length()==0)
            {
                bankCity.requestFocus();
                bankCity.setError("Please Enter Bank City Name");
            }
            else if (!VbankCity.matches("^[a-zA-Z ]+$")){
                bankCity.requestFocus();
                bankCity.setError("Enter Valid Bank City Name");
            }

            else if(VbankIFSC.length()==0)
            {
                bankIFSC.requestFocus();
                bankIFSC.setError("Please Enter Bank IFSC Code");
            }
            else if (!VbankIFSC.matches("^[A-Za-z]{4}[a-zA-Z0-9]{7}$")){
                bankIFSC.requestFocus();
                bankIFSC.setError("Enter Valid Bank IFSC Code");
            }

            else if(VbankSecurityCheqNo.length()==0)
            {
                bankSecurityCheqNo.requestFocus();
                bankSecurityCheqNo.setError("Please Enter Bank Security Cheque No");
            }
            else if (VbankSecurityCheqNo.contains(" ")){
                bankSecurityCheqNo.requestFocus();
                bankSecurityCheqNo.setError("Enter Valid Bank Security Cheque No");
            }

            else if(VbankPersonalName.length()==0)
            {
                bankPersonalName.requestFocus();
                bankPersonalName.setError("Please Enter Person Name");
            }
            else if (!VbankPersonalName.matches("^[a-zA-Z ]+$")){
                bankPersonalName.requestFocus();
                bankPersonalName.setError("Enter Valid Person Name");
            }

            else {
                Add_distributor();
            }


        }


        else {
            Add_distributor();
        }
    }




}
