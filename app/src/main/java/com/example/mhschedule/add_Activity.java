package com.example.mhschedule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class add_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        add_todo todo = new add_todo();
        add_schedule schedule = new add_schedule();

        replaceFragment(schedule);

        // To-do-List로 변환
        RadioButton btn = findViewById(R.id.todobtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(todo);
            }
        });

        // 일정으로 변환
        RadioButton btn_to_schedule = findViewById(R.id.dailybtn);
        btn_to_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(schedule);
            }
        });


    }

    // 실제 fragment 가 바꾸는 함수
    private void replaceFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // 기존의 fragment 랑 새로운 fragment 랑 교체
        fragmentTransaction.replace(R.id.frame_add,fragment);
        fragmentTransaction.commit();
    }

}