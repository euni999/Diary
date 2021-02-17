package com.example.mhschedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/*
BroadcastReceiver는
단말기 안에서 행해지는 수 많은 일들을 대신해서 알려주는 방송? 이라고 생각하면 쉬울 듯 합니다.

 */

public class Alarm_Receiver extends BroadcastReceiver {
    static final String TAG = "Alarm";
    Context context;
    public static final String ACTION_RESTART_SERVICE = "Restart";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d(TAG,"리시버 동작 시작!");
        boolean[] weekends = intent.getBooleanArrayExtra("weekend");

        int alarmId = intent.getIntExtra("id",1);

        Calendar calendar = Calendar.getInstance();

        Log.i(TAG,"id : " + Integer.toString(alarmId) + " today : " + calendar.get(Calendar.DAY_OF_WEEK) + " isAlarm : " + Boolean.toString(weekends[calendar.get(Calendar.DAY_OF_WEEK) - 1]));
        Log.i(TAG,"Action : " + intent.getAction());
        // 해당 요일이 알람이 안맞춰져있으면
        if(!weekends[calendar.get(Calendar.DAY_OF_WEEK) - 1])
        {
            Log.d(TAG,"오늘은 알람울리는 날 아님");
            return;
        }

        if(intent.getAction().equals(ACTION_RESTART_SERVICE))
        {
            Log.i(TAG,"알람 작동 부분 실행!");
            Intent alarmIntent = new Intent(context,AlarmActivity.class);
            // alarmIntent에 FLAG_ACTIVITY_NEW_TASK 라는 flag를 추가해주는 이유는 액티비티 밖에 있어도 실행이 될수 있도록 해줌
            context.startActivity(alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

            /*
            Intent in = new Intent(context, Alarm_Service.class);
            //context.startService(in);
            // foreground 서비스
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                context.startForegroundService(in);
            else    // 백그라운드 서비스
                context.startService(in);*/
        }

        Log.i("STATE","리시브 동작끝!");
    }

}

