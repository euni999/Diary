package com.example.mhschedule;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/*
Service 는 메인 쓰레드 안에서 실행이됨!
많은 쓰레드 작업이 필요한경우 별도의 쓰레드를 생성하여 작업을 해야한다.

서비스의 생명주기는
https://sh-itstory.tistory.com/64
참조
 */

public class Alarm_Service extends Service {
    String TAG = "TAG+Service";

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    // 알람 Intent 생성후 실행
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Intent alarmIntent = new Intent(getApplicationContext(),AlarmActivity.class);

        // alarmIntent에 FLAG_ACTIVITY_NEW_TASK 라는 flag를 추가해주는 이유는 액티비티 밖에 있어도 실행이 될수 있도록 해줌
        startActivity(alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        return super.onStartCommand(intent,flags,startId);
    }

    // 만약 onDestory라는 함수를 선언하고 Destroy 해줄경우 서비스 종료함
    // 하지만 우리는 어플이 종료되도 서비스가 살아있는 것을 원함으로 onDestroy 처리를 해줄 필요가 없음

}
