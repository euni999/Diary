package com.example.mhschedule;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mhschedule.decorators.EventDecorator;
import com.example.mhschedule.decorators.OneDayDecorator;
import com.example.mhschedule.decorators.SaturdayDecorator;
import com.example.mhschedule.decorators.SundayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

//import org.threeten.bp.LocalDate;


public class Calendar_ extends Fragment {
    static final String TAG = "Calendar_";

    String time,kcal,menu;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    Cursor cursor;
    MaterialCalendarView materialCalendarView;
//    private TextView Month_day;
//    private DatePickerDialog.OnDateSetListener callbackMethod;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cal_add, container, false);
        materialCalendarView = (MaterialCalendarView)view.findViewById(R.id.calendarView);

//
//        this.InitializeView();
//        this.InitializeListener();
//
//        public void InitializeView()
//        {
//            Month_day = (TextView)findViewById(R.id.Month_day);
//        }
//
//        public void InitializeListener()
//        {
//            callbackMethod = new DatePickerDialog.OnDateSetListener()
//            {
//                @Override
//                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
//                {
//                    Month_day.setText(year + "???" + monthOfYear + "???" + dayOfMonth + "???");
//                }
//            };
//        }
//
//        public void OnClickHandler(View view)
//        {
//            DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod, 2019, 5, 24);
//
//            dialog.show();
//        }

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1)) // ????????? ??????
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // ????????? ???
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();


        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator);

        String[] result = {"2017,03,18","2017,04,18","2017,05,18","2017,06,18"};

        new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();


                Log.i("Year test", Year + "");
                Log.i("Month test", Month + "");
                Log.i("Day test", Day + "");

                String shot_Day = Year + "," + Month + "," + Day;

                Log.i("shot_Day test", shot_Day + "");
                materialCalendarView.clearSelection();



                Intent intent = new Intent(getActivity() , Cal_list_MainActivity.class);
                intent.putExtra("monthday", Integer.toString(Month)+"???"+" "+Integer.toString(Day)+"???");
                startActivity(intent); // ???????????? ??????.

            }
        });

        return view;
    }



    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        String[] Time_Result;

        ApiSimulator(String[] Time_Result){
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            /*???????????? ????????? ?????????????????????*/
            /*?????? 0??? 1??? ???,?????? ?????????*/
            //string ???????????? Time_Result ??? ???????????? ,??? ????????????????????? string??? int ??? ??????
            for(int i = 0 ; i < Time_Result.length ; i ++){
                CalendarDay day = CalendarDay.from(calendar);
                String[] time = Time_Result[i].split(",");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                dates.add(day);
                calendar.set(year,month-1,dayy);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isRemoving()) {
                return;
            }

            materialCalendarView.addDecorator(new EventDecorator(Color.GREEN, calendarDays,getActivity()));

        }
    }
}