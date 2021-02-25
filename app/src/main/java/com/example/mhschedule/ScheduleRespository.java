package com.example.mhschedule;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ScheduleRespository {
    private static final String TAG = "ScheduleRespository";
    private final ScheduleDAO scheduleDAO;
    private final LiveData<List<ScheduleEntity>> schedule;


    /*
    public ScheduleRespository(ScheduleDAO scheduleDAO, LiveData<List<ScheduleEntity>> schedule) {
        this.scheduleDAO = scheduleDAO;
        this.schedule = schedule;
    }*/


    ScheduleRespository(Context context) {
        AppDatabase db = AppDatabase.getAppDatabase(context);
        scheduleDAO = db.ScheduleDAO();
        schedule = scheduleDAO.getAll();
    }

    public void insert(ScheduleEntity schedule) {
        new AsyncTask<ScheduleEntity, Void, Void>() {
            @Override
            protected Void doInBackground(ScheduleEntity... scheduleEntities)
            {
                if (scheduleDAO != null)
                    scheduleDAO.insert(scheduleEntities[0]);
                return null;
            }
        }.execute(schedule);
    }

    public void update(ScheduleEntity schedule) {
        new AsyncTask<ScheduleEntity, Void, Void>() {
            @Override
            protected Void doInBackground(ScheduleEntity... scheduleEntities)
            {
                if (scheduleDAO != null)
                    scheduleDAO.update(scheduleEntities[0]);
                return null;
            }
        }.execute(schedule);
    }

    public void delete() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids)
            {
                if (scheduleDAO != null)
                    scheduleDAO.deleteAll();
                return null;
            }
        }.execute();
    }

    public LiveData<List<ScheduleEntity>> getAll() {return schedule;}
}
