package com.anirudh.anirudhswami.spider_2016_task2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,SensorEventListener{

    private static int[] images = {R.mipmap.green_circle,R.mipmap.orange_triangle,R.mipmap.blue_pentagon,R.mipmap.red_square,R.mipmap.yellow_hexagon};
    private ImageView img;
    private int i = 0;
    private String song;
    private Button play,stop,enable,disable;
    private TextView time_text;
    private long timer_time = 0;
    private long start_time = 0;
    private boolean ended = false;
    private boolean enabled_slide = false;
    private float dist = 0;

    PlaySong p;// = new PlaySong(MainActivity.this,play,stop);

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            //final MainActivity m = (MainActivity) msg.obj;
            //final int i = m.i;
            img.setImageResource(images[i]);
            Toast.makeText(MainActivity.this,"i here is "+i,Toast.LENGTH_SHORT).show();
            if(i == images.length-1){
                ((Button) findViewById(R.id.slideBtn)).setEnabled(true);
                enable.setEnabled(true);
                //timeHandle.removeCallbacks(timeR);
                //timer_time = 0;
                ended = true;
            }
        }
    };
    private Handler timeHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            timer_time = SystemClock.uptimeMillis() - start_time;
            int ms = (int) timer_time%1000;
            int secs = (int) (timer_time/1000)%60;
            int mins = (int) (timer_time/60000)%60;
            time_text.setText(Integer.toString(mins) + " : " + String.format("%02d", secs) + " : " + String.format("%03d", ms));
        }
    };


    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stop = (Button) findViewById(R.id.stop);
        stop.setEnabled(false);
        play = (Button) findViewById(R.id.play);
        time_text = (TextView) findViewById(R.id.timer);
        enable = (Button) findViewById(R.id.enable);
        disable = (Button) findViewById(R.id.disable);
        disable.setEnabled(false);

        Spinner spinner = (Spinner) findViewById(R.id.songs);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.Songs,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        img = (ImageView) findViewById(R.id.slideShow);

        final SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sm.getSensorList(Sensor.TYPE_PROXIMITY).size()!=0){
            //Do something if sensor is found
            //Here set the sensot
            Sensor s = sm.getSensorList(Sensor.TYPE_PROXIMITY).get(0);
            Toast.makeText(MainActivity.this,"Maximum range is "+Float.toString(s.getMaximumRange()),Toast.LENGTH_SHORT).show();
            sm.registerListener(this,s,SensorManager.SENSOR_DELAY_NORMAL);
        }

        ((Button) findViewById(R.id.slideBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Button) findViewById(R.id.slideBtn)).setEnabled(false);
                enable.setEnabled(false);
                disable.setEnabled(false);
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        synchronized (this) {
                            for (i = -1; i < images.length - 1; ++i) {
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                handler.sendEmptyMessage(0);
                            }
                        }
                    }
                };
                start_time = SystemClock.uptimeMillis();
                ended = false;
                //timeHandle.postDelayed(timeR,0);
                Thread t = new Thread(r);
                t.start();

                Runnable timeR = new Runnable() {
                    @Override
                    public void run() {
                        synchronized (this) {
                            while (!ended) {
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this, "InterruptedWxception caught", Toast.LENGTH_SHORT).show();
                                }
                                timeHandle.sendEmptyMessage(0);
                            }
                        }
                    }
                };

                Thread time = new Thread(timeR);
                time.start();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Playing song " + song, Toast.LENGTH_SHORT).show();
                p = new PlaySong(MainActivity.this, play, stop);
                p.execute(song);
                play.setEnabled(false);
                stop.setEnabled(true);
                Toast.makeText(MainActivity.this, "Cancelled is " + p.isCancelled(), Toast.LENGTH_SHORT).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"The song isCancelled value was "+p.isCancelled(),Toast.LENGTH_SHORT).show();
                p.cancel(true);
                p.mp.stop();
                p.mp.release();
                play.setEnabled(true);
                stop.setEnabled(false);
                sm.unregisterListener(MainActivity.this);
                Toast.makeText(MainActivity.this,"The song isCancelled value is "+p.isCancelled(),Toast.LENGTH_SHORT).show();
            }
        });

        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enabled_slide = true;
                enable.setEnabled(false);
                ((Button) findViewById(R.id.slideBtn)).setEnabled(false);
                disable.setEnabled(true);
            }
        });
        disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enabled_slide = false;
                enable.setEnabled(true);
                ((Button) findViewById(R.id.slideBtn)).setEnabled(true);
                disable.setEnabled(false);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        song = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(MainActivity.this,"Nothing is selected",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(MainActivity.this,"The song isCancelled value was "+p.isCancelled(), Toast.LENGTH_SHORT).show();
        p.cancel(true);
        p.mp.stop();
        p.mp.release();
        Toast.makeText(MainActivity.this,"The song isCancelled value is "+p.isCancelled(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        dist = event.values[0];
        if(enabled_slide) {
            Toast.makeText(MainActivity.this, "Dist now is " + Float.toString(dist), Toast.LENGTH_SHORT).show();
            if(dist<1.0) {
                i++;
                i = i % images.length;
                img.setImageResource(images[i]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
