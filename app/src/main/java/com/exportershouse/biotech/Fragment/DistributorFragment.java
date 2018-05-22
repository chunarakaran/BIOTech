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

    Spinner spinner,statespinner,partyspinner,daysspinner;
    TextView cdate;
    
    EditText iname,iaddress,icity,ipincode,idistrict,iemail,ilandline,imobileno,iFpan_no,iGST_no,iFparty_name,iName_conPerson,iMobile_conPerson,iYearlyTarg,iTrans_name;

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


        statespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long row_id)
            {
//                com_name=   statespinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                String id = statedatalist.get(position).getId();
                Toast.makeText(getContext(),"Id   " +id , Toast.LENGTH_LONG).show();

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
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragment = new NextDistributorFragment();
                fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit();
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


}
