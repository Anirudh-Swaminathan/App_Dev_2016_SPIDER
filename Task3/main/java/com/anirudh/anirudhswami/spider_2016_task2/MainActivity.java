package com.anirudh.anirudhswami.spider_2016_task2;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static int[] images = {R.mipmap.green_circle,R.mipmap.orange_triangle,R.mipmap.blue_pentagon,R.mipmap.red_square,R.mipmap.yellow_hexagon};
    ImageView img;
    int i = 0;
    String song;
    Button play,stop;

    PlaySong p;// = new PlaySong(MainActivity.this,play,stop);

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            //final MainActivity m = (MainActivity) msg.obj;
            //final int i = m.i;
            img.setImageResource(images[i]);
            Toast.makeText(MainActivity.this,"i here is "+i,Toast.LENGTH_SHORT).show();
            if(i == images.length-1) ((Button) findViewById(R.id.slideBtn)).setEnabled(true);
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

        Spinner spinner = (Spinner) findViewById(R.id.songs);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.Songs,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        img = (ImageView) findViewById(R.id.slideShow);

        ((Button) findViewById(R.id.slideBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Button) findViewById(R.id.slideBtn)).setEnabled(false);
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
                Thread t = new Thread(r);
                t.start();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Playing song " + song, Toast.LENGTH_SHORT).show();
                p  = new PlaySong(MainActivity.this,play,stop);
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
                Toast.makeText(MainActivity.this,"The song isCancelled value is "+p.isCancelled(),Toast.LENGTH_SHORT).show();
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
}
