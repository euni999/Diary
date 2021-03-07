package com.example.mhschedule;
import androidx.room.*;

@Entity (tableName = "schedule")
public class ScheduleEntity {
    @PrimaryKey(autoGenerate = true)
    public int scheduleId;
    private String rabel, content, color, sdate, edate, stime, etime;
    private Boolean alarm;

    public ScheduleEntity( String rabel, String content, String color, Boolean alarm, String sdate, String stime, String edate, String etime) {
        this.rabel = rabel;
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
    public String getRabel() {return rabel;}
    public String getColor() {return color;}
    public String getContent() {return content;}
    public String getSdate() {return sdate;}
    public String getEdate() {return edate;}
    public String getStime() {return stime;}
    public String getEtime() {return etime;}

    public void setContent(String content) {this.content = content;}

    public String toString() {
        return rabel + " / alarm : "+ alarm + " / content : " + content + " / color : " + color + " <" + sdate + " " + stime + " ~ " + edate+ " " + etime + ">";
    }
}


