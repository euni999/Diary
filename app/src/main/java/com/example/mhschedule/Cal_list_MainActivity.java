package com.example.mhschedule;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Cal_list_MainActivity extends AppCompatActivity {



    private ArrayList<recycle_data> arrayList;
    private ArrayList<recycle_data_todo> arrayList_todo;
    private recycle_adapter mainAdapter;
    private recycle_adapter_todo mainAdapter_todo;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView_todo;
    private LinearLayoutManager linearLayoutManager;
    private ScheduleRespository scheduleRespository;
    private TodoRespository todoRespository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView_todo = (RecyclerView) findViewById(R.id.rv_todo);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        arrayList_todo = new ArrayList<>();

        mainAdapter = new recycle_adapter(arrayList);
        recyclerView.setAdapter(mainAdapter);
        mainAdapter_todo = new recycle_adapter_todo(arrayList_todo);
        recyclerView_todo.setAdapter(mainAdapter_todo);

        TextView textView = findViewById(R.id.Month_day);
        textView.setText(getIntent().getStringExtra("monthday"));


        Button btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                recycle_data mainData = new recycle_data(R.mipmap.ic_launcher, "홍드로이드", "리사이클러뷰");
//                arrayList.add(mainData);
//                mainAdapter.notifyDataSetChanged();

                Intent intent = new Intent( Cal_list_MainActivity.this, add_Activity.class);
                startActivity(intent); // 액티비티 이동.
            }
        });

        scheduleRespository = new ScheduleRespository(Cal_list_MainActivity.this);
        todoRespository = new TodoRespository(Cal_list_MainActivity.this);


        LiveData<List<ScheduleEntity>> entities_schedule = scheduleRespository.getAll();
        LiveData<List<TodoEntity>> entities_todo = todoRespository.getAll();

        entities_schedule.observe(this, new Observer<List<ScheduleEntity>>() {
            @Override
            public void onChanged(List<ScheduleEntity> scheduleEntities) {
                arrayList.clear();
                Add_schedule(scheduleEntities);
                mainAdapter.notifyDataSetChanged();
            }
        });

        entities_todo.observe(this, new Observer<List<TodoEntity>>() {
            @Override
            public void onChanged(List<TodoEntity> todoEntities) {

                arrayList_todo.clear();
//                recycle_data mainData = new recycle_data(R.mipmap.ic_launcher, "홍드로이드", "리사이클러뷰");
                Add_todo(todoEntities);
                mainAdapter_todo.notifyDataSetChanged();
            }

        });

    }

    private void Add_schedule(List<ScheduleEntity> scheduleEntities){

        for(ScheduleEntity scheduleEntity : scheduleEntities ) {

            recycle_data mainData = new recycle_data( scheduleEntity.getContent());
            arrayList.add(mainData);

        }
    }

    private void Add_todo(List<TodoEntity> todoEntities ){

        for(TodoEntity todoEntity : todoEntities ) {

            recycle_data_todo mainData_todo = new recycle_data_todo( todoEntity.getContent());
            arrayList_todo.add(mainData_todo);

        }

    }


}

