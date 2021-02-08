package com.example.mhschedule;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.lang.reflect.Array;

public class week extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    // 반복 요일 선택
    public void backtodo(View view) {
        MaterialButtonToggleGroup buttonToggleGroup = findViewById(R.id.toggle_button_group);

        boolean[] weekends = {false,false,false,false,false,false,false};
        String[] week = {"일", "월", "화", "수", "목", "금", "토"};
        //Log.i(TAG,buttonToggleGroup.getCheckedButtonIds().toString());
        int[] ButtonsIds = buttonToggleGroup.getCheckedButtonIds().stream().mapToInt(i->i).toArray();
        for(int number : ButtonsIds)
            weekends[number-1] = true;

        String result = new String();
        for(int i=0; i<7; i++) {
            if (weekends[i] == true) {
                String day = week[i];
                result += day;
            }
        }

        // 요일 조건문
        int day = 0;
        for (int i=0; i<7; i++) {
            if (weekends[i] == true) day += 1;
        }
        if (day == 7) {
            result = null;
            result = "매일";
        }
        if(day == 2 && weekends[0] == true && weekends[6] == true) {
            result = null;
            result = "주말";
        }
        if (day == 5 && weekends[0] == false && weekends[6] == false) {
            result = null;
            result = "주중";
        }

        // 요일 값 보내기
        Intent sendIntent = new Intent(this, MainActivity.class);
        sendIntent.putExtra("send",result);
        setResult(RESULT_OK, sendIntent);
        finish();
    }

}

