package com.example.tema3;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.Calendar;

public class AlarmFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button date;
    Button time;
    Button  notification;
    View v;
    TextView data;
    TextView timp;
    Calendar alarm = Calendar.getInstance();
    String titlu;

    public AlarmFragment(String titlu) {
        this.titlu = titlu;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_alarm, container, false);
        date = v.findViewById(R.id.date);
        time = v.findViewById(R.id.time);
        notification = v.findViewById(R.id.notification);
        data = v.findViewById(R.id.tvdate);
        timp = v.findViewById(R.id.tvtime);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDatePicker();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimePicker();
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarm();
            }
        });
        return v;
    }

    private void startDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void startTimePicker(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                false
        );
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String text = dayOfMonth + " " + month + " " + year;
        data.setText(text);
        alarm.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        alarm.set(Calendar.MONTH, month);
        alarm.set(Calendar.YEAR,year);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String text = hourOfDay + " " + minute;
        timp.setText(text);
        alarm.set(Calendar.HOUR_OF_DAY, hourOfDay);
        alarm.set(Calendar.MINUTE, minute);
    }

    private void startAlarm(){
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getContext(), AlertReceiver.class);
        i.putExtra("Cheie",titlu);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),1,i,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getTimeInMillis(), pendingIntent);
    }

    private void cancerAlarm(){
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),1,i,0);

        alarmManager.cancel(pendingIntent);
    }
}

