package com.example.running_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
     Button btnReset,btnStart, btnStop, btnShowRun;
     boolean timerRunning = false; //to see if the timer is running
        int seconds = 0, minutes = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnReset = findViewById(R.id.btnReset);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnShowRun = findViewById(R.id.btnShowRun);
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
                                    seconds++;
                                    if (seconds == 60) {
                                        minutes++;
                                        seconds = 0;
                                    }
                                    String timeString = String.format("%02d:%02d", minutes, seconds);
                                    timerTextView.setText(timeString);
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
                    //makes buttons visible
                    btnReset.setVisibility(View.VISIBLE);
                    btnStop.setVisibility(View.VISIBLE);

                } else {
                    //when pause is clicked timer stops
                    timer.stopTimer();
                    timerRunning = false;
                    stepCounter.setTimerRunning(false);
                    btnStart.setText("Start");
                }
            }
        });
    }
    //stops the timer and makes button to show run visible
    public void StopRun(View v){
        timer.stopTimer();
        btnStart.setVisibility(View.INVISIBLE);
        btnShowRun.setVisibility(View.VISIBLE);
    }
    //resets everything to default
    public void ResetTimer(View v){
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
        btnReset.setVisibility(View.INVISIBLE);
        btnStop.setVisibility(View.INVISIBLE);
        btnShowRun.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.VISIBLE);
    }
    public void ShowRun(View v){


        int totalStep = Integer.parseInt(stepCountTextView.getText().toString());

        //starting new activity and passing values from this activity to the other
        Intent ShowRunActivity = new Intent(getApplicationContext(), RunDetails.class);
        ShowRunActivity.putExtra("Steps", totalStep);
        ShowRunActivity.putExtra("Minutes", minutes);
        ShowRunActivity.putExtra("Seconds", seconds);
        startActivity(ShowRunActivity);
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

