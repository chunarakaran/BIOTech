package com.exportershouse.biotech.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.exportershouse.biotech.Adapter.RecyclerAdapter;
import com.exportershouse.biotech.MainActivity;
import com.exportershouse.biotech.R;

/**
 * Created by Shrey on 24-04-2018.
 */

public class DashboardFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    GridLayoutManager mLayoutManager;

    int RecyclerViewItemPosition ;
    Fragment fragment = null;

    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        rootview=inflater.inflate(R.layout.fragment_dashboard, container, false);

        getActivity().setTitle("Home");
        ((MainActivity) getActivity()).showBottomNavigationButton();

        recyclerView = (RecyclerView)rootview.findViewById(R.id.recyclerview1);

        mLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        // Implementing Click Listener on RecyclerView.
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

                    android.support.v4.app.FragmentTransaction transection=getFragmentManager().beginTransaction();

                    switch (RecyclerViewItemPosition)
                    {
                        case 0:
                            fragment=new DistributorFragment();
                            transection.replace(R.id.container,fragment);
                            transection.addToBackStack(null).commit();
                            break;
                        case 1:
                            fragment=new NewOrderFragment();
                            transection.replace(R.id.container,fragment);
                            transection.addToBackStack(null).commit();
                            break;
                        case 2:
                            fragment=new LeaveRequestFragment();
                            transection.replace(R.id.container,fragment);
                            transection.addToBackStack(null).commit();
                            break;
                        case 3:
                            fragment=new ReportingFragment();
                            transection.replace(R.id.container,fragment);
                            transection.addToBackStack(null).commit();
                            break;

                    }

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

        return rootview;

    }
}
