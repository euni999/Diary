package com.example.mhschedule;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class add_schedule extends Fragment {
    static final String TAG = "add_schedule";

    // TODO: Rename and change types and number of parameters
    public static add_schedule newInstance(String param1, String param2) {
        add_schedule fragment = new add_schedule();
        return fragment;
    }

    Calendar myCalendar = Calendar.getInstance();
    String color = "transparency";  // 일정 색
    boolean alarm = false;  // 알람 여부

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
        View view = inflater.inflate(R.layout.add_schedule, container, false);

        Log.i("test2","입성");

        // 제목
        TextView title = (TextView) view.findViewById(R.id.content);

        // 일정 색상 고르기
        RadioGroup color_btn = view.findViewById(R.id.btn_color);
        color_btn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId) {
                    case R.id.btn_red:
                        color = "red";
                        break;
                    case R.id.btn_yellow:
                        color = "yellow";
                        break;
                    case R.id.btn_sky:
                        color = "sky";
                        break;
                }
            }
        });


        // 날짜 변환
        TextView startdate = (TextView) view.findViewById(R.id.startDate);
        TextView enddate = (TextView) view.findViewById(R.id.endDate);

        DatePickerDialog mDatePicker;
        startdate.setOnClickListener(new View.OnClickListener() {   // 시작 날짜
            @Override
            public void onClick(View v ) {
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog,startDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                mDatePicker.show();
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {    // 종료 날짜
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog,endDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                mDatePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                mDatePicker.show();
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
                        endtime.setText(hourOfDay + " :" + minute + " " + state);
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

    // 알람 여부
    Switch alarm_switch = (Switch) view.findViewById(R.id.switch_btn);
    alarm_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if(isChecked){
                alarm_switch.setChecked(true);
                alarm = true;
            }
            else {
                alarm_switch.setChecked(false);
                alarm = false;
            }
        }
    });

    // db 연동
    AppDatabase db = AppDatabase.getAppDatabase(getActivity());
    ScheduleRespository repository = new ScheduleRespository(getContext());

    // insert 하기
    challenge challenge = new challenge();
     Button savebtn = (Button) view.findViewById(R.id.dailysave);
     savebtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             ScheduleEntity entity;
             if(title.getText().toString().trim().length() <= 0) {
                 Toast.makeText(getActivity(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
             }
             else {
                 if(alarm == true && starttime.getText().toString().trim().length() <=0) {
                     Toast.makeText(getActivity(), "시간을 입력해주세요.", Toast.LENGTH_SHORT).show();
                 }
                 if(enddate.getText().toString().trim().length() <= 0) {
                    entity = new ScheduleEntity(title.getText().toString(), color, alarm, (String) startdate.getText(), starttime.getText().toString(), startdate.getText().toString(), endtime.getText().toString());
                 }
                 else {
                     entity = new ScheduleEntity(title.getText().toString(), color, alarm, (String) startdate.getText(), starttime.getText().toString(), enddate.getText().toString(), endtime.getText().toString());
                 }
                 repository.insert(entity);
                 //fragmentTransaction.replace(R.id.frameLayout,challenge);  // 메인으로 돌아가기
                 //fragmentTransaction.commit();
                 Log.i(TAG, entity.toString());
             }
         }
     });


        return view;
    }

}





