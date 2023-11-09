package com.example.running_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RunDetails extends AppCompatActivity {

    int totalSteps, minutes, seconds, totalTime;

    double totalMetres, caloriesBurned;
    Button btnReturn;
    TextView date, metres, calories, time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_details);
        Bundle runDetails = getIntent().getExtras();
        totalSteps = runDetails.getInt("Steps");
        minutes = runDetails.getInt("Minutes");
        seconds = runDetails.getInt("Seconds");

        date = findViewById(R.id.txtViewDate);
        metres = findViewById(R.id.txtViewMetres);
        calories = findViewById(R.id.txtViewCaloriesBurned);
        time = findViewById(R.id.txtViewTimeTaken);
        btnReturn = findViewById(R.id.btnReturn);

        //source https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android
        //Set current date of the run.
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        //get the total metres
        totalMetres = totalSteps * 0.8;
        String formattedMetres = String.format("%.1f", totalMetres);
        //calories burned
        caloriesBurned = totalSteps * 0.04;
        String formattedCalories = String.format("%.2f", caloriesBurned);
        //time taken
        totalTime = (minutes * 60) + seconds;

        date.setText(formattedDate);
        metres.setText(formattedMetres + " Metres");
        calories.setText(formattedCalories + " Burned");
        time.setText(String.valueOf(totalTime) + " Seconds");

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Return = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Return);
            }
        });
    }
}