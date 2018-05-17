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
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exportershouse.biotech.Adapter.GetColorDataAdapter;
import com.exportershouse.biotech.MainActivity;
import com.exportershouse.biotech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shrey on 24-04-2018.
 */

public class DistributorFragment extends Fragment {

    View rootview;

    Spinner spinner;

    final ArrayList<GetColorDataAdapter> datalist = new ArrayList<>();

    String strComp_name,com_name;
    String URL;
    ArrayList<String> Company;

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

        next=(Button)rootview.findViewById(R.id.btn_next);
        spinner=(Spinner)rootview.findViewById(R.id.company_spinner);
        Company=new ArrayList<>();
        loadSpinnerData(URL);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long row_id)
            {
//                com_name=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                String id = datalist.get(position).getId();
                Toast.makeText(getContext(),"Id   " +id , Toast.LENGTH_LONG).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });


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

    private void loadSpinnerData(String url)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url+"api/color", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                final ArrayList<String> list = new ArrayList<>();

                list.clear();

                try{
                    GetColorDataAdapter GetDatadp ;
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject jsonObject1;
                    JSONArray jsonArray=jsonObject.getJSONArray("colors");
                    for(int i=0;i<jsonArray.length();i++){
                         jsonObject1=jsonArray.getJSONObject(i);

                        GetDatadp = new GetColorDataAdapter();
                        GetDatadp.setName(jsonObject1.getString("name"));
                        GetDatadp.setId(jsonObject1.getString("id"));
                        datalist.add(GetDatadp);

                        list.add(jsonObject1.getString("name"));

                    }
                    spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list));
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

}
