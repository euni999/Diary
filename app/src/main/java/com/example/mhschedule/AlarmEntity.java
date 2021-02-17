package com.example.mhschedule;

import androidx.room.*;

@Entity(tableName = "alarms")
public class AlarmEntity {
    // autoGenerate 는 새로 생길때마다 id값을 자동으로 올라가게 만들어줌
    @PrimaryKey(autoGenerate = true)
    public int alarmId;

    // @ColumnInfo(name = "hours")  ==>컬럼명 변수명과 다르게 사용 가능
    private int hour,minute;
    //private int minute;

    public AlarmEntity(int alarmId,int hour,int minute)
    {
        this.alarmId = alarmId;
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() { return hour; }
    public int getMinute() { return minute; }
    public int getAlarmId() { return alarmId; }

    public void setHour(int hour) { this.hour = hour; }
    public void setMinute(int minute) { this.minute = minute; }

    @Override
    public String toString()
    {
        return "alarmId : " + alarmId + " hour : " + hour + " minute : " + minute;
    }
}
