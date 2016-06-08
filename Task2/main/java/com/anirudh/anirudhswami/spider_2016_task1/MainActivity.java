package com.anirudh.anirudhswami.spider_2016_task1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String name,roll,dept;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.departMent);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,R.array.Departments,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ((Button) findViewById(R.id.submBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = ((EditText) findViewById(R.id.name)).getText().toString();
                roll = ((EditText) findViewById(R.id.roll)).getText().toString();

                boolean alg,app,web,tron,ment;
                alg = ((CheckBox) findViewById(R.id.algoChek)).isChecked();
                app = ((CheckBox) findViewById(R.id.appChek)).isChecked();
                web = ((CheckBox) findViewById(R.id.webChek)).isChecked();
                tron = ((CheckBox) findViewById(R.id.tronChek)).isChecked();
                ment = ((Switch) findViewById(R.id.mentor)).isChecked();

                if(name.equals("")){
                    Toast.makeText(MainActivity.this,"Enter a name",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(roll.equals("")){
                        Toast.makeText(MainActivity.this,"Enter Your roll number",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(!alg && !app && !web && !tron){
                            Toast.makeText(MainActivity.this,"Select at least 1 profile to apply for the inductions",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            List<String> l = new ArrayList();
                            if(alg) l.add("Algos");
                            if(app) l.add("App Dev");
                            if(web) l.add("Web Dev");
                            if(tron) l.add("Tronix");

                            StringBuffer buff = new StringBuffer();
                            buff.append("The entered details are as follows\nName: "+name+"\nRoll Number: "+roll+"\nDepartment: "+dept+"\nProfiles: ");
                            for(int i=0; i<l.size(); ++i){
                                if(i!=l.size()-1) buff.append(l.get(i)+", ");
                                else buff.append(l.get(i));
                            }
                            if(ment) buff.append("\nMentorship: Required");
                            else buff.append("\nMentorship: NOT Required");
                            buff.append("\nDo you wish to continue?");
                            AlertDialog.Builder abu= new AlertDialog.Builder(MainActivity.this);
                            abu.setMessage(buff.toString()).setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(MainActivity.this,Main2Activity.class);
                                    i.putExtra("name",name);
                                    startActivity(i);
                                }
                            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alert = abu.create();
                            alert.setTitle("Check Details");
                            alert.show();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dept = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(MainActivity.this,"You haven't selected a department",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
