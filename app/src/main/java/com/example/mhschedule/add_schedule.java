package com.example.mhschedule;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link add_schedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class add_schedule extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public add_schedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment add_schedule.
     */
    // TODO: Rename and change types and number of parameters
    public static add_schedule newInstance(String param1, String param2) {
        add_schedule fragment = new add_schedule();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener startDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            stupdateLabel();
        }
    };
    DatePickerDialog.OnDateSetListener endDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            endupdateLabel();
        }
    };

    // 날짜 표시
    private void stupdateLabel() {   // 시작 날짜 표시
        String myFormat = "yyyy년 MM월 dd일";    // 출력형식   2018년 3월 22일
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView startdate = (TextView) getView().findViewById(R.id.startDate);
        startdate.setText(sdf.format(myCalendar.getTime()));
    }
    private void endupdateLabel() {   // 종료 날짜 표시
        String myFormat = "yyyy년 MM월 dd일";    // 출력형식   2018년 3월 22일
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView enddate = (TextView) getView().findViewById(R.id.endDate);
        enddate.setText(sdf.format(myCalendar.getTime()));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreateView(savedInstanceState);
        View view = inflater.inflate(R.layout.add_schedule, container, false);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // To-do-List로 변환
        add_todo todo = new add_todo();
        RadioButton btn = view.findViewById(R.id.todobtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,todo);
                fragmentTransaction.commit();
            }
        });

        // 날짜 변환
        TextView startdate = (TextView) view.findViewById(R.id.startDate);
        TextView enddate = (TextView) view.findViewById(R.id.endDate);

        startdate.setOnClickListener(new View.OnClickListener() {   // 시작 날짜
            @Override
            public void onClick(View v ) {
                new DatePickerDialog(getActivity(), startDatePicker,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {    // 종료 날짜
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), endDatePicker,  myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // 시간 변환
        TextView starttime = (TextView) view.findViewById(R.id.startTime);
        TextView endtime = (TextView) view.findViewById(R.id.endTime);

        starttime.setOnClickListener(new View.OnClickListener() {   // 시작 시간
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            state = "PM";
                        }
                        if (view.isShown()) {
                            mcurrentTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            mcurrentTime.set(Calendar.MINUTE, minute);

                        }
                        // EditText에 출력할 형식 지정
                        starttime.setText(hourOfDay + " :" + minute + " " + state);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현 // mTimePicker.setTitle("Choose hour:");
                mTimePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                mTimePicker.show();
            }
        });
        endtime.setOnClickListener(new View.OnClickListener() {    // 종료 시간
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            state = "PM";
                        }
                        if (view.isShown()) {
                            mcurrentTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            mcurrentTime.set(Calendar.MINUTE, minute);
                        }
                        // EditText에 출력할 형식 지정
                        endtime.setText(state + " " +hourOfDay + " :" + minute);
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현 // mTimePicker.setTitle("Choose hour:");
                mTimePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                mTimePicker.show();
            }
        });


        // 터치시 색 변환
        startdate.setOnTouchListener(new View.OnTouchListener(){
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startdate.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.touch_color));
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    startdate.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.back));
                }
                return false;
            }
        });
        enddate.setOnTouchListener(new View.OnTouchListener(){
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    enddate.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.touch_color));
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    enddate.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.back));
                }
                return false;
            }
        });

        starttime.setOnTouchListener(new View.OnTouchListener(){
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    starttime.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.touch_color));
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    starttime.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.back));
                }
                return false;
            }
        });
        endtime.setOnTouchListener(new View.OnTouchListener(){
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    endtime.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.touch_color));
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    endtime.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.back));
                }
                return false;
            }
        });


        return view;
    }

}