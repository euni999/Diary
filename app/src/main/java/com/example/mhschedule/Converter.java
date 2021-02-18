package com.example.mhschedule;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

// Room으로 저장할때는 json으로 바꿔주고 불러올때는 배열로 가져옴
public class Converter {
    // Used by AlarmEntity Boolean[] mDaysOfWeek

    @TypeConverter
    public static boolean[] fromString(String value) {
        Type listType = new TypeToken<boolean[]>() {
        }.getType();
        Log.e("Converter: ", "fromString Called");
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromBoolean(boolean[] list) {
        Gson gson = new Gson();
        Log.e("Converter: ", "fromString Called");
        return gson.toJson(list);
    }
}