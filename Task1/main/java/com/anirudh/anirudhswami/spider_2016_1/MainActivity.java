package com.anirudh.anirudhswami.spider_2016_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.inc)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) findViewById(R.id.op);
                String s = tv.getText().toString();
                int a = Integer.parseInt(s);
                a++;
                s = Integer.toString(a);
                tv.setText(s);
            }
        });
    }
}
