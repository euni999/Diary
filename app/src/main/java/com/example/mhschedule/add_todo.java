package com.example.mhschedule;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class add_todo extends Fragment {

    public static final int REQUEST_CODE1 = 500;
    private static final String TAG = "add_todo";
    String rabel = "todo";

    public add_todo() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.add_todo, container, false);

       TextView replay = view.findViewById(R.id.replay);  // 요일 반복 선택
       TextView title = view.findViewById(R.id.todocontent); // 제목


        // 터치색 변환
        replay.setOnTouchListener(new View.OnTouchListener(){
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    replay.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.touch_color));
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    replay.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.back));
                }
                return false;
            }
        });

        // 반복 요일 선택
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), week.class);
                startActivityForResult(intent, REQUEST_CODE1);
            }
        });

        // db 연결
        AppDatabase db = AppDatabase.getAppDatabase(getActivity());
        TodoRespository repository = new TodoRespository(getContext());
        Button save = (Button) view.findViewById(R.id.todosave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodoEntity entity;
                if(title.getText().toString().trim().length() <= 0) {  // 제목 부재
                    Toast.makeText(getActivity(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else{
                    if (replay.getText().toString().trim().length() <=0) {  // 반복 요일 없을 때
                       entity = new TodoEntity(rabel, title.getText().toString(), "today");
                    }
                    else {
                        entity = new TodoEntity(rabel, title.getText().toString(), replay.getText().toString());
                    }
                    repository.insert(entity);
                    Log.i(TAG, entity.toString());
                    getActivity().finish();
                }
            }
        });
        return view;
    }


    // 요일 값 받기
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String getstr = data.getStringExtra("send");
        TextView weekbtn = (TextView) getView().findViewById(R.id.replay);
        weekbtn.setText(getstr);
    }
}




