package com.exportershouse.biotech.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exportershouse.biotech.Adapter.DataAdapter2;
import com.exportershouse.biotech.Adapter.RecyclerViewAdapter1;
import com.exportershouse.biotech.Adapter.RecyclerViewAdapter2;
import com.exportershouse.biotech.MainActivity;
import com.exportershouse.biotech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shrey on 24-04-2018.
 */

public class InquryStatusFragment extends Fragment {

    View rootview;

    private ProgressDialog pDialog;


    private SwipeRefreshLayout swipeLayout;



    List<DataAdapter2> ListOfdataAdapter;

    RecyclerView recyclerView;

    String Inquryid;
    final ArrayList<DataAdapter2> Inq_id = new ArrayList<>();
    int RecyclerViewItemPosition ;
    LinearLayoutManager layoutManagerOfrecyclerView;

    RecyclerView.Adapter recyclerViewadapter;

    ArrayList<String> ImageTitleNameArrayListForClick;

    String User_id;
    public static final String PREFS_NAME = "login";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview= inflater.inflate(R.layout.fragment_inqurystatus, container, false);

        SharedPreferences sp = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        User_id = sp.getString("User", "");

        getActivity().setTitle("Inquiry Status");
//        ((MainActivity) getActivity()).hideBottomNavigationButton();

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        String Url = getResources().getString(R.string.url);

        ImageTitleNameArrayListForClick = new ArrayList<>();

        ListOfdataAdapter = new ArrayList<DataAdapter2>();

        recyclerView = (RecyclerView)rootview.findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);


        layoutManagerOfrecyclerView=new LinearLayoutManager(getActivity());
        layoutManagerOfrecyclerView.setReverseLayout(true);
        layoutManagerOfrecyclerView.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        String HTTP_JSON_URL = Url+"api/enquiry_view?send_person_id="+User_id;

        JSON_HTTP_CALL(HTTP_JSON_URL);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });


            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent)
            {


                rootview = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(rootview != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting RecyclerView Clicked Item value.
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(rootview);

                    Inquryid=Inq_id.get(RecyclerViewItemPosition).getId();

                    FragmentTransaction transection=getFragmentManager().beginTransaction();
                    InquiryDetailFragment mfragment=new InquiryDetailFragment();

                    Bundle bundle=new Bundle();
                    bundle.putString("Inquryid",Inquryid);
                    mfragment.setArguments(bundle);

                    transection.replace(R.id.content_frame, mfragment);
                    transection.addToBackStack(null).commit();


//                    Toast.makeText(getActivity(), "You clicked " + Inquryid, Toast.LENGTH_SHORT).show();


                }



                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        swipeLayout = (SwipeRefreshLayout)rootview.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(R.color.colorAccent);


        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //your method to refresh content

                swipeLayout.setRefreshing(false);
            }
        });

        if(swipeLayout.isRefreshing()) {
            swipeLayout.setRefreshing(false);
        }


//        rootview.setFocusableInTouchMode(true);
//        rootview.requestFocus();
//        rootview.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    // DO WHAT YOU WANT ON BACK PRESSED
//                    getFragmentManager().popBackStack();
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });


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
                    JSONArray jsonArray=jsonObject.getJSONArray("view_enquiry");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        DataAdapter2 GetDataAdapter2 = new DataAdapter2();
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);


                        GetDataAdapter2.setId(jsonObject1.getString("id"));
                        GetDataAdapter2.setPartyName(jsonObject1.getString("party_name"));
                        GetDataAdapter2.setInqdate(jsonObject1.getString("date"));
                        GetDataAdapter2.setOrderNo(jsonObject1.getString("enquiry_status"));

                        Inq_id.add(GetDataAdapter2);

                        ListOfdataAdapter.add(GetDataAdapter2);
                    }

                    recyclerViewadapter = new RecyclerViewAdapter2(ListOfdataAdapter,getActivity());
                    recyclerView.setAdapter(recyclerViewadapter);

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
