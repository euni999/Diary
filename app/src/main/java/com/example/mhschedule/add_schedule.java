package com.example.mhschedule;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    String rabel = "schedule";
    String stime;

    private void onDate(int year, int month, int dayOfMonth)
    {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }
    private void Datepicker(DatePickerDialog.OnDateSetListener datePicker)   // 달력 표시
    {
        DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDatePicker.show();
    }
    private void touchColor(TextView textview)  // 터치 색
    {
        textview.setOnTouchListener(new View.OnTouchListener(){
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    textview.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.touch_color));
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    textview.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.back));
                }
                return false;
            }
        });
    }
    private void Timepicker(TextView textView, Boolean start)   // 시간 설정
    {
        textView.setOnClickListener(new View.OnClickListener() {
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
                        // TextView에 출력할 형식 지정
                        textView.setText(hourOfDay + " : " + minute + " " + state);

                        if (start == true) {   // textView가 starttime인 경우 (알람 때문에 설정)
                            stime = hourOfDay + ":" + minute;
                            Log.i(TAG, stime);

                            myCalendar.setTimeInMillis(System.currentTimeMillis());
                            myCalendar.set(Calendar.HOUR_OF_DAY, hour);
                            myCalendar.set(Calendar.MINUTE, minute);
                            myCalendar.set(Calendar.SECOND, 0);
                        }
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현 // mTimePicker.setTitle("Choose hour:");
                mTimePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                mTimePicker.show();
            }
        });
    }

    // 날짜 설정
    DatePickerDialog.OnDateSetListener startDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            onDate(year,month,dayOfMonth);
            stupdateLabel();
        }
    };
    DatePickerDialog.OnDateSetListener endDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
        {
            onDate(year,month,dayOfMonth);
            endupdateLabel();
        }
    };

    // 날짜 표시
    private void stupdateLabel() {   // 시작 날짜 표시
        String myFormat = "yyyy-MM-dd";    // 출력형식   2018년 3월 22일
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView startdate = (TextView) getView().findViewById(R.id.startDate);
        startdate.setText(sdf.format(myCalendar.getTime()));
    }
    private void endupdateLabel() {   // 종료 날짜 표시
        String myFormat = "yyyy-MM-dd";    // 출력형식   2018년 3월 22일
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        TextView enddate = (TextView) getView().findViewById(R.id.endDate);
        enddate.setText(sdf.format(myCalendar.getTime()));
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_schedule, container, false);

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

        startdate.setOnClickListener(new View.OnClickListener()   // 시작 날짜
        {
            @Override
            public void onClick(View v ) {
                Datepicker(startDatePicker);
            }
        });
        enddate.setOnClickListener(new View.OnClickListener()   // 종료 날짜
        {
            @Override
            public void onClick(View view) {
                Datepicker(endDatePicker);
            }
        });

        // 시간 변환
        TextView starttime = (TextView) view.findViewById(R.id.startTime);
        TextView endtime = (TextView) view.findViewById(R.id.endTime);

        Timepicker(starttime, true);
        Timepicker(endtime, false);

        // 터치시 색 변환
        touchColor(title);
        touchColor(startdate);
        touchColor(enddate);
        touchColor(starttime);
        touchColor(endtime);

        // 알람 여부
        Switch alarm_switch = (Switch) view.findViewById(R.id.switch_btn);
        alarm_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){ alarm = true; }
                else { alarm = false; }
            }
        });

        // db 연동
        ScheduleRespository repository = new ScheduleRespository(getContext());

        // insert 하기
         Button savebtn = (Button) view.findViewById(R.id.dailysave);
         savebtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ScheduleEntity entity;
                 int title_length = title.getText().toString().trim().length();  // 제목 유무
                 int sdate_length = startdate.getText().toString().trim().length();  // 시작 날짜 유무
                 int stime_length = starttime.getText().toString().trim().length();
                 int edate_length = enddate.getText().toString().trim().length();

                 if(title_length <= 0) {  // 제목 부재
                     Toast.makeText(getActivity(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                 }
                 if(sdate_length <= 0) {  // 시작 날짜 부재
                     Toast.makeText(getActivity(), "날짜를 입력해주세요.", Toast.LENGTH_SHORT).show();
                 }
                 if (title_length > 0 && sdate_length >0) {
                     if (alarm == true) {  // 알람 ON
                         if (stime_length <= 0) {  // 알람 시작 시간 비었을 때
                             Toast.makeText(getActivity(), "시간을 입력해주세요.", Toast.LENGTH_SHORT).show();
                         }
                         else {  // 알람 시작 시간 설정함
                             diaryNotification( title.getText().toString(), (String) startdate.getText(), stime);
                         }
                     }
                     if(edate_length <= 0) {  // 종료 날짜가 없을 시
                        entity = new ScheduleEntity(rabel, title.getText().toString(), color, alarm, (String) startdate.getText(), starttime.getText().toString(), startdate.getText().toString(), endtime.getText().toString());
                     }
                     else {
                         entity = new ScheduleEntity(rabel, title.getText().toString(), color, alarm, (String) startdate.getText(), starttime.getText().toString(), enddate.getText().toString(), endtime.getText().toString());
                     }
                     repository.insert(entity);
                     Log.i(TAG, entity.toString());
                     getActivity().finish();
                 }
             }
         });
            return view;
        }

    // 상단바 알림
    private void diaryNotification(String content, String sdate, String stime)
    {
        NotificationManager notificationMgr = (NotificationManager)getActivity().getSystemService(getContext().NOTIFICATION_SERVICE);

        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent receiverIntent = new Intent(getActivity(), Notification_Receiver.class);
        receiverIntent.putExtra("title", content);  // 제목 전달
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), (int)(System.currentTimeMillis()/1000), receiverIntent, 0);

        String from = sdate + " " + stime; //날짜와 시간을 지정

        //날짜 포맷을 바꿔주는 소스코드
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date datetime = null;
        try {
            datetime = dateFormat.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datetime);

        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(),pendingIntent);
    }
}




