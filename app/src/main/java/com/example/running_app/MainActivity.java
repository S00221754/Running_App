package com.example.running_app;  // use your own package name

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Timer timer;
    private TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.textView1);

        // Implement your Runnable
        Runnable myRunnable = new Runnable() {
            int seconds = 0, minutes = 0, hours = 0;

            @Override
            public void run() {
                // Increment the counter and update the TextView with the timer value
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        seconds++;
                        if (seconds == 60) {
                            minutes++;
                            seconds = 0;
                        }
                        if (minutes == 60) {
                            hours++;
                            minutes = 0;
                        }
                        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                        timerTextView.setText(timeString);
                    }
                });
            }
        };


        // Create an instance of the Timer class
        timer = new Timer(myRunnable, 1000, true); // 1000 milliseconds interval, start the timer immediately
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

