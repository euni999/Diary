package com.example.mhschedule;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRespository {
    private static final String TAG = "TodoRespository";
    private final TodoDAO todoDAO;
    private final List<TodoEntity> todo;


    TodoRespository(Context context) {
        AppDatabase db = AppDatabase.getAppDatabase(context);
        todoDAO = db.TodoDAO();
        todo = todoDAO.getAll();
    }

    public void insert(TodoEntity todo) {
        new AsyncTask<TodoEntity, Void, Void>() {
            @Override
            protected Void doInBackground(TodoEntity... todoEntities)
            {
                if (todoDAO != null)
                    todoDAO.insert(todoEntities[0]);
                return null;
            }
        }.execute(todo);
    }

    public void update(TodoEntity todo) {
        new AsyncTask<TodoEntity, Void, Void>() {
            @Override
            protected Void doInBackground(TodoEntity... todoEntities)
            {
                if (todoDAO != null)
                    todoDAO.insert(todoEntities[0]);
                return null;
            }
        }.execute(todo);
    }

    public void delete() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids)
            {
                if (todoDAO != null)
                    todoDAO.delete();
                return null;
            }
        }.execute();
    }

    public List<TodoEntity> getAll() {return todo;}


}
