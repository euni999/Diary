package com.example.mhschedule;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

// exportSchema 를 false 로 해주므로 스키마를 추출안함
@Database(entities = {
            AlarmEntity.class,
            ScheduleEntity.class,
            TodoEntity.class
        },
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // 데이터베이스를 매번 생성하는건 리소스를 많이사용하므로 싱글톤이 권장된다고한다.
    private static AppDatabase INSTANCE;

    public abstract AlarmDAO alarmDAO();

    public abstract TodoDAO TodoDAO();

    public abstract ScheduleDAO ScheduleDAO();


    /*
    자바에서 지원하느 Synchronized 키워드는 여러개의 스레드가 한개의 자원을 사용하고자 할 때,
    현재 데이터를 사용하고 있는 해당 스레드를 제외하고 나머지 스레드들은 데이터에 접근 할 수 없도록 막는 개념입니다.
    -> 동시 접근을 막는다.
     */
    public synchronized static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            // 데이터베이스의 이름은 MH-db 이다
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "MH-db").allowMainThreadQueries().build();

        }

        return INSTANCE;
    }

    // DB 객체 제거
    public static void destroyInstance() {
        INSTANCE = null;
    }

}
