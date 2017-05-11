package rk.diraj.spiritlevel;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    final float LOW_PASS_ALPHA = (float)0.1;

    private SensorManager manager;
    private Sensor orientation;
    private float[] result;

    final int ACCE_FILTER_DATA_MIN_TIME = 10;
    private long lastSaved = System.currentTimeMillis();

    private MySurfaceView mySurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        mySurfaceView = new MySurfaceView(this);
        layout.addView(mySurfaceView);


        manager=(SensorManager)getSystemService(SENSOR_SERVICE);
        orientation = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        result = new float[3];
        for(int i = 0; i < 3; i++) {
            result[i] = 0;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            result = event.values.clone();

            // Get result on period to avoid too frequent getting results
            if ((System.currentTimeMillis() - lastSaved) > ACCE_FILTER_DATA_MIN_TIME) {
                lastSaved = System.currentTimeMillis();

                // Low pass to filter jitters
                lowPass(result);

                int y = (int) result[1]*10; // Pitch
                int x = (int) result[2]*10; // Roll

                // Update view
                mySurfaceView.update(x, y);
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, orientation, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }

    private void lowPass(float[] oldResult) {
        for (int i = 0; i < 3; i++) {
            result[i] = result[i] + LOW_PASS_ALPHA * (oldResult[i] - result[i]);
        }
    }

}
