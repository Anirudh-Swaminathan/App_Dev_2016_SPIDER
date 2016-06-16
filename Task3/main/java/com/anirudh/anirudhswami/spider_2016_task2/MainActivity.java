package com.anirudh.anirudhswami.spider_2016_task2;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static int[] images = {R.mipmap.green_circle,R.mipmap.orange_triangle,R.mipmap.blue_pentagon,R.mipmap.red_square,R.mipmap.yellow_hexagon};
    ImageView img;
    int i = 0;

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
    }
}
