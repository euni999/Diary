package com.example.mhschedule;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Alarm_Adapter extends BaseAdapter {
    static final String TAG = "Alarm_Adapter";

    public ArrayList<AlarmEntity> listviewitem = new ArrayList<AlarmEntity>();
    private ArrayList<AlarmEntity> arrayList = listviewitem;   //백업 arrayList

    @Override
    public int getCount() { return arrayList.size(); }

    @Override
    public Object getItem(int position) { return arrayList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

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

        AlarmEntity time = arrayList.get(position);
        holder.hourText.setText(time.getHour()+ "시");
        holder.minuteText.setText(time.getMinute()+ "분");

        return convertView;
    }

    private AlarmEntity setTime(int hour,int minute)
    {
        AlarmEntity time = new AlarmEntity(hour,minute);
        return time;
    }

    public void addItem(int hour, int minute) {
        AlarmEntity time = setTime(hour,minute);
        listviewitem.add(time);
    }

    // DB 값을 읽어 추가하는 add
    public void addAll(List<AlarmEntity> entities) {
        for(AlarmEntity entity : entities)
        {
            AlarmEntity time = setTime(entity.getHour(),entity.getMinute());
            listviewitem.add(time);
        }
    }

    // 수정
    public AlarmEntity modifyItem(int position,int hour,int minute)
    {
        AlarmEntity time = (AlarmEntity) getItem(position);
        time.setHour(hour);
        time.setMinute(minute);

        listviewitem.set(position,time);

        return time;
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

    public void removeItemAll()
    {
        listviewitem.clear();
    }

    static class ViewHolder {
        TextView hourText, minuteText;
    }
}
