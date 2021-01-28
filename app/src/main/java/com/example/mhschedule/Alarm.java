package com.example.mhschedule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Alarm extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // xml안에 원하는 레이아웃들을 처리할려고 View를 만들어줘서 관리함
        View view = inflater.inflate(R.layout.alarm_main, container, false);

        // button onclick
        Button add_button = (Button) view.findViewById(R.id.add_alarm);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.i("STATE","추가 버튼 클릭됨.");
                // Fragment 에서는 getActivity()로 현재 액티비티를 가져옴
                Intent intent = new Intent(getActivity() ,Alarm_Add.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
