package com.exportershouse.biotech.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class DistributorFragment extends Fragment {

    View rootview;

    Spinner spinner,statespinner,partyspinner,daysspinner,nameFirm,nameParty;
    TextView cdate;
    
    EditText iname,iaddress,icity,ipincode,idistrict,iemail,ilandline,imobileno,iFpan_no,iGST_no,iFparty_name,iName_conPerson,iMobile_conPerson,iYearlyTarg,iTrans_name;

    String SnameFirm,SnameParty,Scompanyname,Sstateid,Spartyname,Sdaysname;

    String Hdate,Hiname,Hiaddress,Hicity,Hipincode,Hidistrict,Hiemail,Hilandline,Himobileno,HiFpan_no,HiGST_no,HiFparty_name,HiName_conPerson,HiMobile_conPerson,HiYearlyTarg,HiTrans_name,
            Hcompanyname,Hstateid,Hpartyname,Hdaysname,HnameFirm,HnameParty;

    final ArrayList<GetStateDataAdapter> statedatalist = new ArrayList<>();


    String URL;


    Fragment fragment = null;
    Button next;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_distributor, container, false);

        getActivity().setTitle("New Distributer");
        ((MainActivity) getActivity()).hideBottomNavigationButton();

        URL = getString(R.string.url);

        initialize();




        loadState_SpinnerData(URL);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long row_id)
            {
//                com_name=   statespinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                Scompanyname = spinner.getSelectedItem().toString();
//                Toast.makeText(getContext(),"Id   " +Hcompanyname , Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        statespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long row_id)
            {
//                com_name=   statespinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                Sstateid = statedatalist.get(position).getId();
//                Toast.makeText(getContext(),"Id   " +Hstateid , Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        partyspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long row_id)
            {
//                com_name=   statespinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                Spartyname = partyspinner.getSelectedItem().toString();
//                Toast.makeText(getContext(),"Id   " +Hpartyname , Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        daysspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long row_id)
            {
//                com_name=   statespinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                 Sdaysname = daysspinner.getSelectedItem().toString();
//                Toast.makeText(getContext(),"Id   " +Hdaysname , Toast.LENGTH_LONG).show();

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
//                com_name=   statespinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                SnameFirm = nameFirm.getSelectedItem().toString();
//                Toast.makeText(getContext(),"Id   " +SnameFirm , Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        nameParty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long row_id)
            {
//                com_name=   statespinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                SnameParty = nameParty.getSelectedItem().toString();
//                Toast.makeText(getContext(),"Id   " +SnameParty , Toast.LENGTH_LONG).show();

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

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetValueFromEditText();

                FragmentTransaction transection=getFragmentManager().beginTransaction();
                NextDistributorFragment mfragment=new NextDistributorFragment();

                Bundle bundle=new Bundle();
                bundle.putString("Hdate",Hdate);
                bundle.putString("HnameFirm",HnameFirm+" "+Hiname);
                bundle.putString("Hiaddress",Hiaddress);
                bundle.putString("Hicity",Hicity);
                bundle.putString("Hipincode",Hipincode);
                bundle.putString("Hidistrict",Hidistrict);
                bundle.putString("Hiemail",Hiemail);
                bundle.putString("Hilandline",Hilandline);
                bundle.putString("Himobileno",Himobileno);
                bundle.putString("HiFpan_no",HiFpan_no);
                bundle.putString("HiGST_no",HiGST_no);
                bundle.putString("HnameParty",HnameParty+" "+HiFparty_name);
                bundle.putString("HiName_conPerson",HiName_conPerson);
                bundle.putString("HiMobile_conPerson",HiMobile_conPerson);
                bundle.putString("HiYearlyTarg",HiYearlyTarg);
                bundle.putString("HiTrans_name",HiTrans_name);
                bundle.putString("Hcompanyname",Hcompanyname);
                bundle.putString("Hstateid",Hstateid);
                bundle.putString("Hpartyname",Hpartyname);
                bundle.putString("Hdaysname",Hdaysname);


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


    private void loadState_SpinnerData(String url)
    {
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

    public void initialize()
    {
        next=(Button)rootview.findViewById(R.id.btn_next);

        spinner=(Spinner)rootview.findViewById(R.id.company_spinner);
        statespinner=(Spinner)rootview.findViewById(R.id.state_spinner);
        partyspinner=(Spinner)rootview.findViewById(R.id.natureoffirm_spinner);
        daysspinner=(Spinner)rootview.findViewById(R.id.days_spinner);

        nameFirm=(Spinner)rootview.findViewById(R.id.name_firm);
        nameParty=(Spinner)rootview.findViewById(R.id.name_party);

        iname=(EditText)rootview.findViewById(R.id.input_name);
        iaddress=(EditText)rootview.findViewById(R.id.input_address);
        icity=(EditText)rootview.findViewById(R.id.input_city);
        ipincode=(EditText)rootview.findViewById(R.id.input_pincode);
        idistrict=(EditText)rootview.findViewById(R.id.input_dis);
        iemail=(EditText)rootview.findViewById(R.id.input_email);
        ilandline=(EditText)rootview.findViewById(R.id.input_lanlineno);
        imobileno=(EditText)rootview.findViewById(R.id.input_mobno);
        iFpan_no=(EditText)rootview.findViewById(R.id.input_panno);
        iGST_no=(EditText)rootview.findViewById(R.id.input_gstno);
        iFparty_name=(EditText)rootview.findViewById(R.id.input_nameofpro);
        iName_conPerson=(EditText)rootview.findViewById(R.id.input_nameofcontper);
        iMobile_conPerson=(EditText)rootview.findViewById(R.id.input_mboofcontper);
        iYearlyTarg=(EditText)rootview.findViewById(R.id.input_yearlytarget);
        iTrans_name=(EditText)rootview.findViewById(R.id.input_transportname);


    }

    public void GetValueFromEditText()
    {
        Hdate=cdate.getText().toString();

        Hcompanyname=Scompanyname;
        Hstateid=Sstateid;
        Hpartyname=Spartyname;
        Hdaysname=Sdaysname;

        HnameFirm=SnameFirm;
        HnameParty=SnameParty;

        Hiname=iname.getText().toString();
        Hiaddress=iaddress.getText().toString();
        Hicity=icity.getText().toString();
        Hipincode=ipincode.getText().toString();
        Hidistrict=idistrict.getText().toString();
        Hiemail=iemail.getText().toString();
        Hilandline=ilandline.getText().toString();
        Himobileno=imobileno.getText().toString();
        HiFpan_no=iFpan_no.getText().toString();
        HiGST_no=iGST_no.getText().toString();
        HiFparty_name=iFparty_name.getText().toString();
        HiName_conPerson=iName_conPerson.getText().toString();
        HiMobile_conPerson=iMobile_conPerson.getText().toString();
        HiYearlyTarg=iYearlyTarg.getText().toString();
        HiTrans_name=iTrans_name.getText().toString();
    }


}
