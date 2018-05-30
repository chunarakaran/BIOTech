package com.exportershouse.biotech.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.exportershouse.biotech.R;

import java.util.List;

/**
 * Created by Juned on 2/8/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<DataAdapter> dataAdapters;

    ImageLoader imageLoader;

    public RecyclerViewAdapter(List<DataAdapter> getDataAdapter, Context context){

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leave_status_view, parent, false);


        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        DataAdapter dataAdapterOBJ =  dataAdapters.get(position);


        Viewholder.ImageTitleTextView.setText(dataAdapterOBJ.getImageTitle());

        Viewholder.Question_content.setText("From "+dataAdapterOBJ.getFrom_Date()+" To "+dataAdapterOBJ.getTo_Date());



    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{




        public TextView ImageTitleTextView,Question_content;
        public NetworkImageView VollyImageView;


        public ViewHolder(View itemView) {

            super(itemView);

//            VollyImageView = (NetworkImageView) itemView.findViewById(R.id.Profile_pic) ;
            ImageTitleTextView = (TextView) itemView.findViewById(R.id.Question_title) ;
            Question_content = (TextView) itemView.findViewById(R.id.Question_content) ;

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // get position
//                    int pos = getAdapterPosition();
//
//
//                        Toast.makeText(v.getContext(), "You clicked " + pos, Toast.LENGTH_SHORT).show();
//
//
//                }
//            });

        }


    }
}
