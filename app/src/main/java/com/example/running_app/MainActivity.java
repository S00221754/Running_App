package com.example.running_app;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

     Timer timer;
     TextView timerTextView;
     Button btnReset,btnStart;
     boolean TimerRunning = false; //to see if the
        int seconds = 55, minutes = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnReset = findViewById(R.id.btnReset);
        btnStart = findViewById(R.id.btnStart);
        timerTextView = findViewById(R.id.tblkTimer);

        btnStart.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (TimerRunning == false) {
                    //Runnable used to execute the code with specific instructions
                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            // Increment the counter and update the TextView with the timer value
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //checks if the timer is at 5 minutes.
                                    if (minutes != 5) {
                                        seconds++;
                                        if (seconds == 60) {
                                            minutes++;
                                            seconds = 0;
                                        }
                                        String timeString = String.format("%02d:%02d", minutes, seconds);
                                        timerTextView.setText(timeString);
                                    } else {
                                        timer.stopTimer();
                                        TimerRunning = false;
                                        btnStart.setText("Start");
                                    }
                                }
                            });
                        }
                    };
                    //Making the timer and using the runnable
                    timer = new Timer(myRunnable, 1000, true); // 1000 milliseconds interval, start the timer immediately

                    TimerRunning = true;
                    btnStart.setText("Pause");
                } else {
                    timer.stopTimer();
                    TimerRunning = false;
                    btnStart.setText("Start");
                }
            }
        });
    }
    public void PauseTimer(View v){
        timer.stopTimer();
    }
    public void ResetTimer(View v){
        timer.stopTimer();
        seconds = 0;
        minutes = 0;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeString);
        TimerRunning = false;
        btnStart.setText("Start");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Stop the timer when the activity is destroyed
        if (timer != null) {
            timer.stopTimer();
        }
    }
}

