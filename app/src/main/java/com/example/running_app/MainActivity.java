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

public class MainActivity extends AppCompatActivity implements StepCounter.StepUpdate {

     StepCounter stepCounter;
     Timer timer;
     TextView timerTextView, stepCountTextView;
     Button btnReset,btnStart;
     boolean timerRunning = false, paused = false; //to see if the
        int seconds = 0, minutes = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnReset = findViewById(R.id.btnReset);
        btnStart = findViewById(R.id.btnStart);
        timerTextView = findViewById(R.id.tblkTimer);
        stepCountTextView = findViewById(R.id.stepCountTextView);
        stepCounter = new StepCounter(this, this); // call the step tracker class with the context and callback.

        btnStart.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (timerRunning == false) {
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
                                        paused = false;
                                        if (seconds == 60) {
                                            minutes++;
                                            seconds = 0;
                                        }
                                        String timeString = String.format("%02d:%02d", minutes, seconds);
                                        timerTextView.setText(timeString);
                                    } else {
                                        timer.stopTimer();
                                        timerRunning = false;
                                        btnStart.setText("Start");
                                    }
                                }
                            });
                        }
                    };
                    //Making the timer and using the runnable
                    timer = new Timer(myRunnable, 1000, true); // 1000 milliseconds interval, start the timer immediately

                    //changes start button to pause
                    timerRunning = true;
                    stepCounter.setTimerRunning(true);
                    btnStart.setText("Pause");
                } else {
                    timer.stopTimer();
                    timerRunning = false;
                    stepCounter.setTimerRunning(false);
                    btnStart.setText("Start");
                    paused = true;
                }
            }
        });
    }
    public void PauseTimer(View v){
        timer.stopTimer();
    }
    public void ResetTimer(View v){
        if (timerRunning == true || paused == true)
        {
            timer.stopTimer();
            seconds = 0;
            minutes = 0;
            String timeString = String.format("%02d:%02d", minutes, seconds);
            timerTextView.setText(timeString);
            timerRunning = false;
            stepCounter.resetStepCount();
            btnStart.setText("Start");
            stepCounter.setTimerRunning(false);
            stepCountTextView.setText(String.valueOf(0));
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stepCounter.stopTracking();
        // Stop the timer when the activity is destroyed
        if (timer != null) {
            timer.stopTimer();
        }
    }
    @Override
    public void stepDetected(int stepCount) {
        runOnUiThread(() -> stepCountTextView.setText(String.valueOf(stepCount))); //runOnUiThread is used to update steps when a step has been taken.
    }
}

