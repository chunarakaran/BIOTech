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

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder> {

    Context context;

    List<DataAdapter2> dataAdapters;

    ImageLoader imageLoader;

    public RecyclerViewAdapter2(List<DataAdapter2> getDataAdapter, Context context){

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inquiry_status_view, parent, false);


        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        DataAdapter2 dataAdapterOBJ =  dataAdapters.get(position);


        Viewholder.PartyName.setText(dataAdapterOBJ.getPartyName());

        Viewholder.Idate.setText(dataAdapterOBJ.getInqdate());

        Viewholder.Istatus.setText(dataAdapterOBJ.getstatus());



    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{




        public TextView PartyName,Idate,Istatus;
        public NetworkImageView VollyImageView;


        public ViewHolder(View itemView) {

            super(itemView);

            PartyName = (TextView) itemView.findViewById(R.id.party_title) ;
            Idate = (TextView) itemView.findViewById(R.id.inquiry_date) ;
            Istatus = (TextView) itemView.findViewById(R.id.status) ;

        }


    }
}
