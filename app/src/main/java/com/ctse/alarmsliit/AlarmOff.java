package com.ctse.alarmsliit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AlarmOff extends Activity {
    private MediaPlayer mediaPlayer;
    private SQLHelper SQLHelper;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView txtQ;
    private RadioButton ans1, ans2, ans3, ans4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_off);

        SharedPreferences cameraStore = getSharedPreferences("music",MODE_PRIVATE);
        String alarmSound = cameraStore.getString("sound", null);

        SoundPlayer(getApplicationContext(), R.raw.alarm1);

        txtQ = (TextView)findViewById(R.id.txtQ);
        ans1 = (RadioButton)findViewById(R.id.rd1);
        ans2 = (RadioButton)findViewById(R.id.rd2);
        ans3 = (RadioButton)findViewById(R.id.rd3);
        ans4 = (RadioButton)findViewById(R.id.rd4);
        radioGroup = (RadioGroup) findViewById(R.id.rdg);

        ans3.setChecked(true);
        final List<String> qna = new ArrayList<>();
        qna.add("Sachin Tendulkar hit his 100th international century against which of the following team?");
        qna.add("Sri Lanka");
        qna.add("Bangladesh");
        qna.add("Australia");
        qna.add("England");

        final List<String> qna2 = new ArrayList<>();
        qna2.add("Who among the following is the first Indian to score a century in Indian Premier League (IPL)?");
        qna2.add("Gautam Gambhir");
        qna2.add("Rahul Dravid");
        qna2.add("Manish Pandey");
        qna2.add("Sachin Tendulkar");


        txtQ.setText(qna.get(0));
        ans1.setText(qna.get(1));
        ans2.setText(qna.get(2));
        ans3.setText(qna.get(3));
        ans4.setText(qna.get(4));

        Button btnOff = (Button) findViewById(R.id.btnOff);
        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);

                if(radioButton.getText().equals(qna.get(2))){
                    onPause();
                    finish();
                }
                else {
                    Toast.makeText(AlarmOff.this,
                            "Incorrect Answer! Try Again", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void SoundPlayer(Context context, int raw_id){
        mediaPlayer = MediaPlayer.create(context, raw_id);
        mediaPlayer.start();
        mediaPlayer.setLooping(true); // Set looping
    }
    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }
}
