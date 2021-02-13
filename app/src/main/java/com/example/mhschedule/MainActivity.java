package com.example.mhschedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Alarm home = new Alarm();
    private Calendar_ schedule = new Calendar_();
    private test3 challenge = new test3();

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
                case R.id.challenge :
                    replaceFragment(challenge);
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