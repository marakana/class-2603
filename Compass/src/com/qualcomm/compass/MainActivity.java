package com.qualcomm.compass;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
	TextView output;
	RoseView rose;
	SensorManager sensorManager;
	Sensor sensor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// UI stuff
		setContentView(R.layout.activity_main);
		output = (TextView) findViewById(R.id.output);
		rose = (RoseView) findViewById(R.id.rose);

		// Sensor stuff
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		
		for( Sensor s: sensorManager.getSensorList(Sensor.TYPE_ALL) ) {
			Log.d("Compass", "Sensor: "+ s.getName());
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, sensor,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
	}

	// --- SensorEventListener callbacks ---
	@Override
	public void onSensorChanged(SensorEvent event) {
		rose.setOrientation( (int) event.values[0] );
		
		output.setText(String.format("%d azimuth\n%d pitch\n%d roll",
				(int) event.values[0], (int) event.values[1],
				(int) event.values[2]));
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
}
