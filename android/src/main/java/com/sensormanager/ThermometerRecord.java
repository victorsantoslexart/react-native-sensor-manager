package com.sensormanager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import androidx.annotation.Nullable;

public class ThermometerRecord implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mThermometer;
    private long lastUpdate = 0;
    private int i = 0, n = 0;
    private int delay;

    private ReactContext mReactContext;
    private Arguments mArguments;

    public ThermometerRecord(ReactApplicationContext reactContext) {
        mSensorManager = (SensorManager) reactContext.getSystemService(reactContext.SENSOR_SERVICE);
        mReactContext = reactContext;
    }

    public int start(int delay) {
        this.delay = delay;
        if ((mThermometer = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)) != null) {
            mSensorManager.registerListener(this, mThermometer, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            return (0);
        }
        return (1);
    }

    public void stop() {
        mSensorManager.unregisterListener(this);
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
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

        if (mySensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            long curTime = System.currentTimeMillis();
            i++;
            if ((curTime - lastUpdate) > delay) {
                i = 0;
                map.putDouble("temp", sensorEvent.values[0]);
                sendEvent("Thermometer", map);
                lastUpdate = curTime;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
