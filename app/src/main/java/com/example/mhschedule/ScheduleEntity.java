package com.example.mhschedule;
import androidx.room.*;

import java.sql.Time;
import java.util.Date;

@Entity (tableName = "schedule")
public class ScheduleEntity {
    @PrimaryKey(autoGenerate = true)
    public int scheduleId;
    private String content, color, sdate, edate, stime, etime;
    private Boolean alarm;

    public ScheduleEntity( String content, String color, Boolean alarm, String sdate, String stime, String edate, String etime) {
        this.content = content;
        this.color = color;
        this.alarm = alarm;
        this.sdate = sdate;
        this.stime = stime;
        this.edate = edate;
        this.etime = etime;
    }

    public int getId() {return scheduleId;}
    public void setId(int scheduleId) {this.scheduleId = scheduleId;}

    public Boolean getAlarm() {return alarm;}
    public String getColor() {return color;}
    public String getContent() {return content;}
    public String getSdate() {return sdate;}
    public String getEdate() {return edate;}
    public String getStime() {return stime;}
    public String getEtime() {return etime;}

    public void setContent(String content) {this.content = content;}
    //public void setAlarm(String alarm) {this.alarm = alarm;}
    /*
    public void setSdate(String sdate) {this.sdate = sdate;}
    public void setEdate(String edate) {this.edate = edate;}
    public void setStime(String stime) {this.stime = stime;}
    public void setEtime(String etime) {this.etime = etime;}
    
     */

    public String toString() {
        return "alarm : "+ alarm + " / content : " + content + " / color : " + color + " <" + sdate + " " + stime + " ~ " + edate+ " " + etime + ">";
    }
}


