package com.example.mhschedule;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AlarmRepository {
    private static final String TAG = "AlarmRepository";
    private final AlarmDAO alarmDAO;
    private final LiveData<List<AlarmEntity>> allAlarms;

    AlarmRepository(Context context)
    {
        AppDatabase db = AppDatabase.getAppDatabase(context);
        alarmDAO = db.alarmDAO();
        allAlarms = alarmDAO.getAll();
    }

    public void insert(AlarmEntity alarm)
    {
        new AsyncTask<AlarmEntity, Void, Void>() {
            @Override
            protected Void doInBackground(AlarmEntity... alarmEntities)
            {
                if (alarmDAO != null)
                    alarmDAO.insertAll(alarmEntities);
                return null;
            }
        }.execute(alarm);
    }

    public void update(AlarmEntity entity)
    {
        new AsyncTask<AlarmEntity, Void, Void>() {
            @Override
            protected Void doInBackground(AlarmEntity... alarmEntities) {
                if (alarmDAO != null)
                    alarmDAO.update(alarmEntities[0]);
                return null;
            }
        }.execute(entity);
    }

    public void deleteAll() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids)
            {
                if (alarmDAO != null)
                    alarmDAO.deleteAll();
                return null;
            }
        }.execute();
    }

    public LiveData<List<AlarmEntity>> getAll() { return allAlarms; }
}
