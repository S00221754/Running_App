package com.example.running_app;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class StepCounter implements SensorEventListener {

    //interface used to display the step count
    public interface StepUpdate {
        void stepDetected(int stepCount);
    }

    private SensorManager sensorManager; //allows access to the sensors of the device
    private Sensor accelerometer;
    private float[] acceleration = new float[3]; //contains the co-ordinates x,y and z.
    private float rmsThreshold = 20; //Threshold to know if the phone is moving.
    private int stepCount = 0;
    private StepUpdate UpdateSteps;



    public StepCounter(Context context, StepUpdate callback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //assigns the accelerometer
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL); // assigning event listener
        this.UpdateSteps = callback; //calls the steps detected
    }

    //when the sensor values change this is called to calculate if a step has been taken or not.
    @Override
    public void onSensorChanged(SensorEvent event) {
        acceleration[0] = event.values[0]; //x
        acceleration[1] = event.values[1]; //y
        acceleration[2] = event.values[2]; //z

        // Calculating if a step is taken
        double rms = Math.sqrt(acceleration[0] * acceleration[0] + acceleration[1] * acceleration[1] + acceleration[2] * acceleration[2]);

        //if the rms is greater than the threshold it is considered a step
        if (rms > rmsThreshold ) {
            // Step goes up by 1
            stepCount++;
            //updates the interface
            UpdateSteps.stepDetected(stepCount);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    //returns the step count when called
    public int getStepCount() {
        return stepCount;
    }
    //Stops the tracker
    public void stopTracking() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }
    //resets the step counter
    public void resetStepCount() {
        stepCount = 0;
    }
}

