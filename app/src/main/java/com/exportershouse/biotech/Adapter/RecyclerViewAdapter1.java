package com.exportershouse.biotech.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.exportershouse.biotech.R;

import java.util.List;

/**
 * Created by Juned on 2/8/2017.
 */

public class RecyclerViewAdapter1 extends RecyclerView.Adapter<RecyclerViewAdapter1.ViewHolder> {

    Context context;

    List<DataAdapter1> dataAdapters;

    ImageLoader imageLoader;

    public RecyclerViewAdapter1(List<DataAdapter1> getDataAdapter, Context context){

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_status_view, parent, false);


        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        DataAdapter1 dataAdapterOBJ =  dataAdapters.get(position);


        Viewholder.PartyName.setText(dataAdapterOBJ.getPartyName());

        Viewholder.Odate.setText(dataAdapterOBJ.getOdate());

        Viewholder.OrderNo.setText(dataAdapterOBJ.getOrderNo());



    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{




        public TextView PartyName,Odate,OrderNo;
        public NetworkImageView VollyImageView;


        public ViewHolder(View itemView) {

            super(itemView);

            PartyName = (TextView) itemView.findViewById(R.id.party_name) ;
            Odate = (TextView) itemView.findViewById(R.id.date) ;
            OrderNo = (TextView) itemView.findViewById(R.id.order_no) ;

        }


    }
}
