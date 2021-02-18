package com.example.mhschedule;

import java.util.List;

// 요일에 대한 알람정보를 관리하기 위한 클래스
public class WeekendsManager {
    // Weekends 설정 및 반환
    public static boolean[] getWeekends(List<Integer> checkedIds)
    {
        boolean[] weekends = {false,false,false,false,false,false,false};

        for(int id : checkedIds)
        {
            switch (id)
            {
                case R.id.sun:
                    weekends[0] = true;
                    break;
                case R.id.mon:
                    weekends[1] = true;
                    break;
                case R.id.tue:
                    weekends[2] = true;
                    break;
                case R.id.wed:
                    weekends[3] = true;
                    break;
                case R.id.thu:
                    weekends[4] = true;
                    break;
                case R.id.fri:
                    weekends[5] = true;
                    break;
                case R.id.sat:
                    weekends[6] = true;
                    break;
                default:
            }
        }

        return weekends;
    }

    // index 정보로 id값 얻기
    public static int getWeekDayId(int index)
    {
        switch (index)
        {
            case 0:
                return R.id.sun;
            case 1:
                return R.id.mon;
            case 2:
                return R.id.tue;
            case 3:
                return R.id.wed;
            case 4:
                return R.id.thu;
            case 5:
                return R.id.fri;
            case 6:
                return R.id.sat;
            default:
                return -1;
        }
    }
}
