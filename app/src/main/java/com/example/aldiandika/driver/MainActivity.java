package com.example.aldiandika.driver;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public int bgState, status=1;
    public ImageView den;
    public SensorManager sensorManager;
    float ax,ay,az;
    float accelerationAkar;
    long lastUpdate;
    public MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer mp = MediaPlayer.create(this, R.raw.henshin);
        den = (ImageView)findViewById(R.id.den);
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener((SensorEventListener) this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

//        if(henshin) {
//        mp.start();
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int X = (int) event.getX();
        int Y = (int) event.getY();
        int eventaction = event.getAction();

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                if(bgState != 0){
                    bgState = 0;
                    status = 1;
                }
                else{
                    bgState = 1;
                }
                break;

            case MotionEvent.ACTION_MOVE:
//                Toast.makeText(this, "MOVE "+"X: "+X+" Y: "+Y, Toast.LENGTH_SHORT).show();
                break;

            case MotionEvent.ACTION_UP:
                if(bgState == 1){
                    den.setVisibility(View.VISIBLE);
                }
                else{
                    den.setVisibility(View.INVISIBLE);
                }
                break;
        }
        return true;
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            ax = event.values[0];
            ay = event.values[1];
            az = event.values[2];

            accelerationAkar = (ax*ax + ay*ay)/(SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH);
            long actTime = event.timestamp;
            if((accelerationAkar >= 8) && (bgState == 1)){
                if(actTime - lastUpdate < 200){
                    return;
                }
                lastUpdate = actTime;
//                        Log.d("stat",""+bgState);
                //        Log.d("acc",String.valueOf(accelerationAkar));
                if(status == 1){
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.henshin);
                    mp.start();
                    status = 0;
                }
            }
        }
//        Log.d("stat",""+henshin);
//        Log.d("ax",String.valueOf(ax));
//        Log.d("ay",String.valueOf(ay));
//        Log.d("acc",String.valueOf(accelerationAkar));
    }

}
