package com.example.mhschedule;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add_todo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_todo extends Fragment {

    public static final int REQUEST_CODE1 = 500;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public add_todo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment add_todo.
     */
    // TODO: Rename and change types and number of parameters
    public static add_todo newInstance(String param1, String param2) {
        add_todo fragment = new add_todo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //getActivity().setContentView(R.layout.add_todo);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_todo, container, false);

        TextView replay = view.findViewById(R.id.replay);
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


        add_schedule schedule = new add_schedule();
        RadioButton btn = view.findViewById(R.id.dailybtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,schedule);
                fragmentTransaction.commit();
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
       // weekbtn.setText(REQUEST_CODE1);
    }
}