package com.anirudh.anirudhswami.spider_2016_task2;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Anirudh Swami on 17-06-2016.
 */
public class PlaySong extends AsyncTask<String,Void,String> implements MediaPlayer.OnCompletionListener{

    //Declaring the variables
    MediaPlayer mp;
    Context context;
    Button button1;
    Button button2;

    //Constructor
    public PlaySong(Context ctx,Button btn1,Button btn2){
        this.context = ctx;
        this.button1 = btn1;
        this.button2 = btn2;
    }

    @Override
    protected String doInBackground(String... params) {
        String song = params[0];

        //Choose track
        switch (song){
            case "Awake":
                mp = MediaPlayer.create(context,R.raw.awake);
                break;
            case "Boulevard":
                mp = MediaPlayer.create(context,R.raw.boulevard);
                break;
            case "Hero":
                mp = MediaPlayer.create(context,R.raw.hero);
                break;
            case "Numb":
                mp = MediaPlayer.create(context,R.raw.numb);
                break;
            case "Roads":
                mp = MediaPlayer.create(context,R.raw.roads);
                break;
            default:
                Toast.makeText(context,"The song is not right",Toast.LENGTH_SHORT).show();
        }
        mp.start();
        mp.setOnCompletionListener(this);
        long progress = mp.getCurrentPosition();
        while(progress<mp.getDuration() && mp.isPlaying() && !isCancelled()){
            progress = mp.getCurrentPosition();
            if(isCancelled()){
                progress = mp.getDuration();
                //Toast.makeText(context,"Task is being  cancelled",Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context,"Started finished",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Toast.makeText(context,"Song finished ",Toast.LENGTH_SHORT).show();
        button1.setEnabled(true);
        button2.setEnabled(false);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Toast.makeText(context,"Song was cancelled",Toast.LENGTH_SHORT).show();
        button1.setEnabled(true);
        button2.setEnabled(false);
    }
}
