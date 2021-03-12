package com.example.mhschedule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {



    //private challenge home = new challenge();
    //private add_schedule schedule = new add_schedule();
    private Calendar_ schedule = new Calendar_();
    private Alarm home = new Alarm();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 초기 값을 Home 화면으로 해줌
        replaceFragment(home);


        BottomNavigationView bottomNavBar = (BottomNavigationView) findViewById(R.id.bottomNavBar);
        bottomNavBar.setOnNavigationItemSelectedListener( (item) -> {
            switch (item.getItemId())
            {
                case R.id.home :
                    replaceFragment(home);
                    return true;
                 case R.id.schedule :
                     replaceFragment(schedule);
                     return true;

                default:
                    return false;
            }
        });



    }

    // 실제 fragment 가 바꾸는 함수
    private void replaceFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // 기존의 fragment 랑 새로운 fragment 랑 교체
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

}