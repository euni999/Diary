package com.example.mhschedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Alarm_Add extends AppCompatActivity {
    AlarmManager alarmManager;
    TimePicker timePicker;
    Context context;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_add);

        this.context = this;

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        timePicker = findViewById(R.id.time_picker);

        final Calendar calendar = Calendar.getInstance();

        // 알람 시작? 버튼
        Button button = (Button) findViewById(R.id.set_alarm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 시간 가져오기
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                // calendar에 시간 세팅
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.SECOND,0);
                Toast.makeText(Alarm_Add.this,"알람 예정 " + hour + "시 " + minute + "분",Toast.LENGTH_SHORT).show();

                Log.i("STATE","예약 시간 : " + calendar.getTime().toString());

                // 알람 리시버 intent 생성
                final Intent alarm_intent = new Intent(context, Alarm_Receiver.class);
                // 액션 설정
                alarm_intent.setAction(Alarm_Receiver.ACTION_RESTART_SERVICE);

                // receiver에 string 값 넘겨주기
               // alarm_intent.putExtra("state","alarm on");

                // pendingIntent 설정
                pendingIntent = PendingIntent.getBroadcast(Alarm_Add.this,0,alarm_intent,PendingIntent.FLAG_UPDATE_CURRENT);


                /*
                sdk 23 이상부터는 Doze 모드가 있어 디바이스가 잠들면 알람매니저가 안먹힘

                https://developer.android.com/training/monitoring-device-state/doze-standby
                참조
                * */

                // 알람설정
                // set 함수보다 setExact 함수가 더 정확하게 스케쥴링 됨
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                //alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                Log.i("STATE","알람 설정 성공!");
            }
        });
    }
}
