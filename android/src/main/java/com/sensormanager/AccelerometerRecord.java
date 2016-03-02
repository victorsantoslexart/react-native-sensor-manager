package com.sensormanager;

import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.support.annotation.Nullable;

import java.io.*;
import java.util.Date;
import java.util.Timer;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.bridge.ReactApplicationContext;

public class AccelerometerRecord implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private long lastUpdate = 0;
    private int i = 0, n = 0;
	private int delay;

	private ReactContext mReactContext;
	private Arguments mArguments;


    public AccelerometerRecord(ReactApplicationContext reactContext, int delay) {
		this.delay = delay;
        mSensorManager = (SensorManager)reactContext.getSystemService(reactContext.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
		mReactContext = reactContext;
    }

    public void stop() {
        mSensorManager.unregisterListener(this);
    }

	private void sendEvent(String eventName, @Nullable WritableMap params)
	{
		try {
			mReactContext 
				.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class) 
				.emit(eventName, params);
		} catch (RuntimeException e) {
			Log.e("ERROR", "java.lang.RuntimeException: Trying to invoke JS before CatalystInstance has been set!");
		}
	}

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
		WritableMap map = mArguments.createMap();

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            i++;
            if ((curTime - lastUpdate) > delay) {
                i = 0;
				map.putDouble("x", sensorEvent.values[0]);
				map.putDouble("y", sensorEvent.values[1]);
				map.putDouble("z", sensorEvent.values[2]);
				sendEvent("accValue", map);
                lastUpdate = curTime;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
