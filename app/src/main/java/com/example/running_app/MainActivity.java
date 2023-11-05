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
     Button btnReset,btnStart, btnPause;
        int seconds = 40, minutes = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnReset = findViewById(R.id.btnReset);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        timerTextView = findViewById(R.id.tblkTimer);

        btnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // Increment the counter and update the TextView with the timer value
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                if (minutes != 5){
                                    seconds++;
                                    if (seconds == 60) {
                                        minutes++;
                                        seconds = 0;
                                    }
                                    String timeString = String.format("%02d:%02d", minutes, seconds);
                                    timerTextView.setText(timeString);
                                }
                                else {
                                    timer.stopTimer();
                                    Toast.makeText(MainActivity.this, "Max Time", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                    }
                };


                // Create an instance of the Timer class
                timer = new Timer(myRunnable, 1000, true); // 1000 milliseconds interval, start the timer immediately
            }
        });

        // Implement your Runnable

    }
    public void PauseTimer(View v){
        timer.stopTimer();
    }
    /*
    public void StartTimer(View v){
        timer.startTimer();
    }

    public void StartOrPause(View v){
        String currentMode = btnStartPause.getText().toString();

        if (currentMode == "Start"){
            timer.startTimer();
            btnStartPause.setText("Pause");
        }
        else
        {
            timer.stopTimer();
            btnStartPause.setText("Start");
        }
    }
    */
    public void ResetTimer(View v){
        timer.stopTimer();
        seconds = 0;
        minutes = 0;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeString);
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

