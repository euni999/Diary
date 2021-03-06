package com.example.mhschedule;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class Notification_Receiver extends BroadcastReceiver {

        NotificationManager manager;
        NotificationCompat.Builder builder;

        //오레오 이상은 반드시 채널을 설정해줘야 Notification이 작동함
        private static String CHANNEL_ID = "channel1";
        private static String CHANNEL_NAME = "Channel1";

        @Override
        public void onReceive (Context context, Intent intent){
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            String title = intent.getStringExtra("title");

            builder = null;
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(
                        new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
                );
                builder = new NotificationCompat.Builder(context, CHANNEL_ID);
            } else {
                builder = new NotificationCompat.Builder(context);
            }

            //알림창 클릭 시 activity 화면 부름
            Intent intent2 = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, (int)(System.currentTimeMillis()/1000), intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.schedule);

            //알림창 제목
            builder.setContentTitle("' " + title + " ' 일정이 있습니다.");
            //알림창 내용
            builder.setContentText("일정을 확인하세요");
            //알림창 아이콘
            builder.setSmallIcon(R.drawable.schedule2);
            builder.setLargeIcon(bitmap);
            //알림창 우선순위
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            //알림창 터치시 자동 삭제
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);

            Notification notification = builder.build();
            manager.notify((int)(System.currentTimeMillis()/1000), notification);

        }
    }

