package com.example.mhschedule;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class recycle_adapter extends RecyclerView.Adapter<recycle_adapter.CustomViewHolder> {

    private ArrayList<recycle_data> arrayList;
    private ScheduleRespository respository;

    public recycle_adapter(ArrayList<recycle_data> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public recycle_adapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull recycle_adapter.CustomViewHolder holder, int position) {

        holder.schedule_content.setText(arrayList.get(position).getSchedule_content());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.schedule_content.getText().toString();
                //Toast.makeText(v.getContext(), curName, Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(holder.getAdapterPosition());

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position) {
        try{
            arrayList.remove(position);
            notifyItemRemoved(position);

        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView schedule_content;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.schedule_content = (TextView) itemView.findViewById(R.id.schedule_content);
        }
    }



}


