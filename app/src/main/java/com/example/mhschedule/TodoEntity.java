package com.example.mhschedule;
import androidx.room.*;

import java.util.Calendar;


@Entity//(tableName = "Todo")
public class TodoEntity {
    @PrimaryKey(autoGenerate = true)
    public int todoId;
    private String rabel, content, week;

    public TodoEntity(String rabel, String content, String week) {
        this.rabel = rabel;
        this.content = content;
        this.week = week;
    }

    public int getId() {return todoId;}
    public void setId(int todoId) {this.todoId = todoId;}

    public String getRabel() {return rabel;}
    public String getContent() {return content;}
    public String getWeek() {return week;}

    public void setContent(String content) {this.content = content;}
    public void setWeek(String week) {this.week = week;}

    public String toString() {
        return rabel + " / content : " + content +" / 요일 : " + week;
    }
}






