package com.example.mhschedule;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class Alarm extends Fragment {
    static final String TAG = "Alarm";

    // request code
    public static final int REQUEST_CODE1 = 1000;
    public static final int REQUEST_CODE2 = 1001;

    private Alarm_Adapter arrayAdapter;
    private int adapterPosition;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // xml안에 원하는 레이아웃들을 처리할려고 View를 만들어줘서 관리함
        View view = inflater.inflate(R.layout.alarm_main, container, false);

        // 어댑터 생성
        arrayAdapter = new Alarm_Adapter();

        ListView listView = view.findViewById(R.id.list_view);
        listView.setAdapter(arrayAdapter);      // Adapter 설정

        // 알람수정
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                adapterPosition = position;
                Intent intent = new Intent(getActivity(), Alarm_Add.class);
                startActivityForResult(intent,REQUEST_CODE2);
            }
        });

        // 알람 추가
        Button add_button = (Button) view.findViewById(R.id.add_alarm);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // Fragment 에서는 getActivity()로 현재 액티비티를 가져옴
                Intent intent = new Intent(getActivity() ,Alarm_Add.class);
                // 알람을 설정하면 설정한 결과를 반환해줌
                startActivityForResult(intent,REQUEST_CODE1);
            }
        });

        return view;
    }

    // startActivityForResult 의 결과를 받는 함수이다.
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        Log.d(TAG,Integer.toString(requestCode));

        if(data != null)
        {
            int hour,minute;
            hour = data.getIntExtra("hour", 1);
            minute = data.getIntExtra("minute", 2);

            //시간 리스트 추가하는 경우
            if(requestCode == REQUEST_CODE1)
            {
                Log.d(TAG,"알람 목록 추가");
                arrayAdapter.addItem(hour, minute);
            }

            //시간 리스트 터치 시 변경된 시간값 저장(수정 할경우)
            if(requestCode == REQUEST_CODE2)
            {
                Log.d(TAG,"알람 목록 수정");
                arrayAdapter.modifyItem(adapterPosition,hour,minute);
            }
            // 변경사항 갱신 (listView 를 새로고침 해줌)
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
