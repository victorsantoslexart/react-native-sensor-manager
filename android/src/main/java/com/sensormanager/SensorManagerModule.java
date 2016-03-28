package com.sensormanager;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import android.util.Log;

public class SensorManagerModule extends ReactContextBaseJavaModule {
    private static final String		REACT_CLASS = "SensorManager";
    private AccelerometerRecord		mAccelerometerRecord;
	private GyroscopeRecord 		mGyroscopeRecord;
	private MagnetometerRecord		mMagnetometerRecord;
	private StepCounterRecord		mStepCounterRecord;
	private ThermometerRecord		mThermometerRecord;
	private MotionValueRecord		mMotionValueRecord;

	private ReactApplicationContext	mReactContext;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public SensorManagerModule(ReactApplicationContext reactContext) {
		super(reactContext);
		mReactContext = reactContext;
    }

    @ReactMethod
    public int startAccelerometer(int delay) {
    	mAccelerometerRecord = new AccelerometerRecord(mReactContext);
		return (mAccelerometerRecord.start(delay));
    }

    @ReactMethod
    public void stopAccelerometer() {
		mAccelerometerRecord.stop();
    }

    @ReactMethod
    public int startGyroscope(int delay) {
    	mGyroscopeRecord = new GyroscopeRecord(mReactContext);
		return (mGyroscopeRecord.start(delay));
    }

    @ReactMethod
    public void stopGyroscope() {
		mGyroscopeRecord.stop();
    }

    @ReactMethod
    public int startMagnetometer(int delay) {
    	mMagnetometerRecord = new MagnetometerRecord(mReactContext);
		return (mMagnetometerRecord.start(delay));
    }

    @ReactMethod
    public void stopMagnetometer() {
		mMagnetometerRecord.stop();
    }

    @ReactMethod
    public int startStepCounter(int delay) {
    	mStepCounterRecord = new StepCounterRecord(mReactContext);
		return (mStepCounterRecord.start(delay));
    }

    @ReactMethod
    public void stopStepCounter() {
		mStepCounterRecord.stop();
    }

    @ReactMethod
    public int startThermometer(int delay) {
    	mThermometerRecord = new ThermometerRecord(mReactContext);
		return (mThermometerRecord.start(delay));
    }

    @ReactMethod
    public void stopThermometer() {
		mThermometerRecord.stop();
    }

    @ReactMethod
    public int startMotionValue(int delay) {
    	mMotionValueRecord = new MotionValueRecord(mReactContext);
		return (mMotionValueRecord.start(delay));
    }

    @ReactMethod
    public void stopMotionValue() {
		mMotionValueRecord.stop();
    }


	/*
    @Override
    public ReactBarcodeScannerView createViewInstance(ThemedReactContext context) {
    }

    @Override
    public void onDropViewInstance(ReactBarcodeScannerView view) {
    }

    @Override
    public void onHostResume() {
    }

    @Override
    public void onHostPause() {
    }

    @Override
    public void onHostDestroy() {
    }
    */
}
