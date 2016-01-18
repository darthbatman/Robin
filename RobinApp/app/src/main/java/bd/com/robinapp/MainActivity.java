package bd.com.robinapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            getAccelData();

            SendDataTask task = new SendDataTask();
            task.execute();

        }

        private void getAccelData() {

            final TextView tv = (TextView) findViewById(R.id.latlng);

            SensorManager mSensorManager;
            Sensor mSensor;

            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

            mSensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    tv.setText(Float.toString(sensorEvent.values[0])
                            + "\n" + Float.toString(sensorEvent.values[1])
                            + "\n" + Float.toString(sensorEvent.values[2]));
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            }, mSensor, 10000);

        }


    }


    class SendDataTask extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... voids) {


            try {
                URL url = new URL("http://7d58ba52.ngrok.io");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // output an ack
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("User-Agent", "ROBIN");
                connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                connection.setRequestProperty("Content-Type", "text/html");

                PrintWriter out = new PrintWriter(connection.getOutputStream());

                out.println("do you hear me?");
                out.println("break"); // to tell it to stop reading here

                out.flush();

                Log.d("Response", connection.getResponseMessage());

//                BufferedReader in = new BufferedReader(connection.getInputStream());
//
//                String inputLine = " ";
//
//                // get the response
//                while (!in.readLine().equals(null))
//                    inputLine += in.readLine();
//
//                Log.d("More Response", inputLine);

                // send accelerometer data!

            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }



