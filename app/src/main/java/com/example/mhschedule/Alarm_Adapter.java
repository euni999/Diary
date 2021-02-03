package com.example.mhschedule;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class Alarm_Adapter extends BaseAdapter {
    static final String TAG = "Alarm_Adapter";

    public ArrayList<Time> listviewitem = new ArrayList<Time>();
    private ArrayList<Time> arrayList = listviewitem;   //백업 arrayList

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.round_theme, parent, false);

            TextView hourText = (TextView)convertView.findViewById(R.id.text_hour);
            TextView minuteText = (TextView)convertView.findViewById(R.id.text_minute);
            Switch switchBtn = convertView.findViewById(R.id.switch_btn);
            // 알람이 생성이 됬으므로 당연히 기본값으로 checked 된 상태로 해줌
            switchBtn.setChecked(true);

            holder.hourText = hourText;
            holder.minuteText = minuteText;

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder)convertView.getTag();

        Time time = arrayList.get(position);
        holder.hourText.setText(time.getHour()+ "시");
        holder.minuteText.setText(time.getMinute()+ "분");

        return convertView;
    }

    private Time setTime(int hour,int minute)
    {
        Time time = new Time();
        time.setHour(hour);
        time.setMinute(minute);

        return time;
    }


    public void addItem(int hour, int minute) {
        Time time = setTime(hour,minute);

        listviewitem.add(time);
    }

    // 수정
    public void modifyItem(int position,int hour,int minute)
    {
        Time time = setTime(hour,minute);

        listviewitem.set(position,time);
    }

    //List 삭제 method
    public void removeItem(int position) {
        if(listviewitem.size() >= 1)
            listviewitem.remove(position);
    }

    public void removeItem() {
        if(listviewitem.size() >= 1)
            listviewitem.remove(listviewitem.size()-1);
    }

    static class ViewHolder {
        TextView hourText, minuteText;
    }
}
