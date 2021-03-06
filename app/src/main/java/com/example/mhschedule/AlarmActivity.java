package com.example.mhschedule;

import android.app.AppComponentFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {
    Calendar calendar;
    SwipeButton swipeButton;
    TextView timeText;
    MediaPlayer mediaPlayer;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i("STATE","알람 울림!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        calendar = Calendar.getInstance();
        swipeButton = (SwipeButton) findViewById(R.id.swipe_btn);
        timeText = (TextView) findViewById(R.id.time);

        // getWindow()는 화면이 잠겨있거나 꺼져있어도 알람이 실행되면서 화면을 띄울 수 있게 해줍니다.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        // 잠금 화면 위로 activity 띄워줌

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.beep);   // 소리를 재생할 MediaPlayer
        mediaPlayer.setLooping(true);   // 무한반복
        mediaPlayer.start();

        // 실시간으로 시계 출력
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                while (flag == true)
                {
                    try
                    {
                        // 현재 시간을 가져와 Text에 표시
                        calendar = Calendar.getInstance();
                        if (calendar.get(Calendar.HOUR_OF_DAY) > 0 && calendar.get(Calendar.HOUR_OF_DAY) < 12)
                            timeText.setText("AM " + calendar.get(Calendar.HOUR_OF_DAY) + "시 " + calendar.get(Calendar.MINUTE) + "분 " + calendar.get(Calendar.SECOND) + "초");
                        else if (calendar.get(Calendar.HOUR_OF_DAY) == 12)
                            timeText.setText("PM " + calendar.get(Calendar.HOUR_OF_DAY) + "시 " + calendar.get(Calendar.MINUTE) + "분 " + calendar.get(Calendar.SECOND) + "초");
                        else if (calendar.get(Calendar.HOUR_OF_DAY) > 12 && calendar.get(Calendar.HOUR_OF_DAY) < 24)
                            timeText.setText("PM " + (calendar.get(Calendar.HOUR_OF_DAY) - 12) + "시 " + calendar.get(Calendar.MINUTE) + "분 " + calendar.get(Calendar.SECOND) + "초");
                        else if (calendar.get(Calendar.HOUR_OF_DAY) == 0)
                            timeText.setText("AM 0시 " + calendar.get(Calendar.MINUTE) + "분 " + calendar.get(Calendar.SECOND) + "초");

                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {}
                }
            }
        }).start();

        swipeButton.setOnStateChangeListener(new OnStateChangeListener()
        {
            // 상태가 바뀌었다는건 알림을 해제했다는 소리이므로 알람해제 해줌
            @Override
            public void onStateChange(boolean active)
            {
                mediaPlayer.stop();
                flag=false;
                finish();
            }
        }); // Swipe Button 밀어서 해제
    }

}
