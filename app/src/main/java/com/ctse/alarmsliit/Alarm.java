package com.ctse.alarmsliit;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Alarm extends Activity {
    private ListAdapter todoListAdapter;
    private SQLHelper SQLHelper;
    private MainActivity mainActivity;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm);
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.6));

        final Spinner dropdown = findViewById(R.id.spinner);
        final String[] items = new String[]{"Please Select a Tone","alarm1", "alarm2", "alarm3", "alarm4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setSelected(false);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                int resID;
                String i = dropdown.getSelectedItem().toString();

                if ("alarm1".equals(i)) {
                    resID = R.raw.alarm1;
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), resID);
                    mediaPlayer.start();

                } else if ("alarm2".equals(i)) {
                    resID = R.raw.alarm2;
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), resID);
                    mediaPlayer.start();

                } else if ("alarm3".equals(i)) {
                    resID = R.raw.alarm3;
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), resID);
                    mediaPlayer.start();

                } else if ("alarm4".equals(i)) {
                    resID = R.raw.alarm4;
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), resID);
                    mediaPlayer.start();

                } else {
                    resID = R.raw.alarm;
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), resID);
                    mediaPlayer.start();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });

        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                TimePicker tpTime = (TimePicker) findViewById(R.id.timePicker);
                mainActivity = new MainActivity();
                String todoTaskInput = dropdown.getSelectedItem().toString();

                SharedPreferences.Editor store = getSharedPreferences("music", MODE_PRIVATE).edit();
                store.putString("sound", todoTaskInput);
                store.apply();


                int hour=tpTime.getCurrentHour();
                int min =tpTime.getCurrentMinute();

                String time = hour+":"+min;
                SQLHelper = new SQLHelper(Alarm.this);
                SQLiteDatabase sqLiteDatabase = SQLHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.clear();

                values.put(SQLHelper.COL1_TASK, todoTaskInput);
                values.put(SQLHelper.COL2_TASK, time);
                sqLiteDatabase.insertWithOnConflict(SQLHelper.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                        hour, min, 0);
                setAlarm(calendar.getTimeInMillis(), time);
                Log.d("tm: ", String.valueOf(hour));
                Log.d("tm: ", String.valueOf(min));
                Intent newIntent = new Intent(Alarm.this, MainActivity.class);
                startActivity(newIntent);
                onPause();
                finish();
            }
        });

        Button btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(Alarm.this, MainActivity.class);
                startActivity(newIntent);
            }
        });
    }


    private void setAlarm(long time, String clock) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(this, MyAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(this, "Alarm is set to "+ clock, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();


    }



}
