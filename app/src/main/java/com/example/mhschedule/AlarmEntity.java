package com.example.mhschedule;

import android.util.Log;

import androidx.room.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Entity(tableName = "alarms")
public class AlarmEntity implements Serializable {
    // autoGenerate 는 새로 생길때마다 id값을 자동으로 올라가게 만들어줌
    @PrimaryKey(autoGenerate = true)
    public int alarmId;

    // @ColumnInfo(name = "hours")  ==>컬럼명 변수명과 다르게 사용 가능
    private int hour,minute;

    // Room DB는 배열형태를 지원안하므로 json으로 저장해주기떄문에
    // Convert를 만들고 TypeConverters로 적용시킴
    @TypeConverters({Converter.class})
    private boolean[] weekends;

    public AlarmEntity(int alarmId,int hour,int minute,boolean[] weekends)
    {
        this.alarmId = alarmId;
        this.hour = hour;
        this.minute = minute;
        this.weekends = weekends;
    }

    public int getHour() { return hour; }
    public int getMinute() { return minute; }
    public int getAlarmId() { return alarmId; }
    public boolean[] getWeekends() { return weekends; }

    public void setHour(int hour) { this.hour = hour; }
    public void setMinute(int minute) { this.minute = minute; }
    public void setWeekends(boolean[] weekends) { this.weekends = weekends; }

    @Override
    public String toString()
    {
        return "alarmId : " + alarmId + " hour : " + hour + " minute : " + minute;
    }


}
