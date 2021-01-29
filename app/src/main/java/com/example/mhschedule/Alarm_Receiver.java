package com.example.mhschedule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
BroadcastReceiver는
단말기 안에서 행해지는 수 많은 일들을 대신해서 알려주는 방송? 이라고 생각하면 쉬울 듯 합니다.

 */

public class Alarm_Receiver extends BroadcastReceiver {
    Context context;
    public static final String ACTION_RESTART_SERVICE = "Restart";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals(ACTION_RESTART_SERVICE))
        {
            Intent in = new Intent(context, Alarm_Service.class);
            context.startService(in);
        }

        /*
        // foreground 서비스
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            this.context.startForegroundService(service_intent);
        else    // 백그라운드 서비스
            this.context.startService(service_intent);*/

        Log.i("STATE","리시브 동작끝!");
    }

}

