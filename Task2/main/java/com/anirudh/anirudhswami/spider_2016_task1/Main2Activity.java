package com.anirudh.anirudhswami.spider_2016_task1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Timestamp;


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent a = getIntent();
        String name  = a.getStringExtra("name");
        ((TextView) findViewById(R.id.resp)).setText("Thank you "+name+" for your response");

        ((Button) findViewById(R.id.anotherResp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this,MainActivity.class);
                startActivity(i);
            }
        });

        long time = System.currentTimeMillis();
        Timestamp tsTemp = new Timestamp(time);
        String ts =  tsTemp.toString();
        ((TextView) findViewById(R.id.timeStamp)).setText(ts);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
