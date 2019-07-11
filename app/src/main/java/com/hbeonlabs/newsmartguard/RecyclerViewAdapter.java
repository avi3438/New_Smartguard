package com.hbeonlabs.newsmartguard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<DataManager> dataManagers;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public RecyclerViewAdapter(Context c , ArrayList<DataManager> d)
    {
        context=c;
        dataManagers=d;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

       myViewHolder.title.setText(dataManagers.get(i).getTitle());
       myViewHolder.mob.setText(dataManagers.get(i).getMob());
       Picasso.with(context).load(dataManagers.get(i).getImage()).into(myViewHolder.image);
       //myViewHolder.status.setText(dataManagers.get(i).getDevice_status().toString());

    }

    @Override
    public int getItemCount() {
        return dataManagers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        
        TextView title,mob,status;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            
            title=(TextView)itemView.findViewById(R.id.hubtitle);
            mob=(TextView)itemView.findViewById(R.id.hubmobno);
            image=(ImageView)itemView.findViewById(R.id.hubimage);
            //status=(TextView)itemView.findViewById(R.id.hubstatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }

                }
            });
            
            
        }
    }
}
