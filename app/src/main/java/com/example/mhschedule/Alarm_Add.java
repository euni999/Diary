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

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Alarm_Add extends AppCompatActivity {
    static final String TAG = "Alarm_Add";

    public static final int REQUEST_CODE = 200;

    int alarmId;
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

        String test = getIntent().getAction();

        if(test == "MODIFY")
            alarmId = getIntent().getIntExtra("id", 1);
    }

    public void setAlarm(View view)
    {
        final Calendar calendar = Calendar.getInstance();

        // 요일 값 초기화
        boolean[] weekends = setWeekends();

        // 시간 가져오기
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        // calendar에 시간 세팅
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Toast.makeText(Alarm_Add.this,"알람 예정 " + hour + "시 " + minute + "분",Toast.LENGTH_SHORT).show();

        Log.i("STATE","예약 시간 : " + calendar.getTime().toString());

        setPendingIntent(weekends);

        long selectTime = calendar.getTimeInMillis();
        long currentTime = System.currentTimeMillis();
        // 하루의 시간을 나타냄
        long intervalDay = 1000 * 60 * 60  * 24;

        //만일 내가 설정한 시간이 현재 시간보다 작다면 알람이 바로 울려버리기 때문에 이미 시간이 지난 알람은 다음날 울려야 한다.
        if(currentTime >= selectTime)
            selectTime += intervalDay;

        /*
        sdk 23 이상부터는 Doze 모드가 있어 디바이스가 잠들면 알람매니저가 안먹힘

        https://developer.android.com/training/monitoring-device-state/doze-standby
        참조
        */

        // 알람설정
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,selectTime,intervalDay,pendingIntent);

        /*
        // 한번만 실행됨
        // set 함수보다 setExact 함수가 더 정확하게 스케쥴링 됨
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);*/
        //alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        Log.i(TAG,"알람 설정 성공!");
        // 알람에 대한 정보를 전달해주고 종료함
        processEnd(hour,minute);
    }

    public void cancelAlarm(View view)
    {
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        // 해제하면 DB에서 삭제가 되어야함
    }

    private boolean[] setWeekends()
    {
        // 요일 값 가져오기 위한 데이터
        MaterialButtonToggleGroup buttonToggleGroup = findViewById(R.id.toggle_button_group);

        // 요일 값 초기화
        boolean[] weekends = {false,false,false,false,false,false,false};
        Log.i(TAG,buttonToggleGroup.getCheckedButtonIds().toString());

        int[] ButtonsIds = buttonToggleGroup.getCheckedButtonIds().stream().mapToInt(i->i).toArray();

        for(int number : ButtonsIds)
        {
            number = number % 7 != 0 ? number%7 : 7;
            Log.d(TAG,Integer.toString(number));
            weekends[number - 1] = true;
        }

        return weekends;
    }

    private void processEnd(int hour, int minute)
    {
        // 결과값 전달
        Intent sendIntent = new Intent(this, MainActivity.class);
        sendIntent.putExtra("hour", hour);
        sendIntent.putExtra("minute", minute);
        sendIntent.putExtra("id",alarmId);
        setResult(RESULT_OK, sendIntent);
        finish();
    }

    private void setPendingIntent(boolean[] weekends)
    {
        // 알람 리시버 intent 생성
        final Intent alarm_intent = new Intent(context, Alarm_Receiver.class);
        alarm_intent.setAction(Alarm_Receiver.ACTION_RESTART_SERVICE);
        // 선택한 요일 값들 넘겨줌
        alarm_intent.putExtra("weekend",weekends);

        // 알람 다중 등록을 위한 alarmId 설정
        alarmId = (int)(System.currentTimeMillis()/1000);
        alarm_intent.putExtra("id",alarmId);

        // pendingIntent 설정
        pendingIntent = PendingIntent.getBroadcast(Alarm_Add.this,alarmId,alarm_intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
