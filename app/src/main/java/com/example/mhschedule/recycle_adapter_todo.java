package com.example.mhschedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycle_adapter_todo extends RecyclerView.Adapter<recycle_adapter_todo.CustomViewHolder> {

    private ArrayList<recycle_data_todo> arrayList;

    public recycle_adapter_todo(ArrayList<recycle_data_todo> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override


    public recycle_adapter_todo.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_todo,parent,false);
        CustomViewHolder holder2 = new CustomViewHolder(view2);

        return holder2;
    }

    @Override
    public void onBindViewHolder(@NonNull recycle_adapter_todo.CustomViewHolder holder, int position) {

        holder.todo_content.setText(arrayList.get(position).getTodo_content());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curName = holder.todo_content.getText().toString();
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

        protected TextView todo_content;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.todo_content = (TextView) itemView.findViewById(R.id.todo_content);
        }
    }

    public void removeItemAll() { arrayList.clear(); }

}


