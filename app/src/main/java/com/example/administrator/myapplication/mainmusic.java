package com.example.administrator.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * Created by Administrator on 2017-12-17.
 * 지금은 사용 안하고 있음.
 */

public class mainmusic extends Service{
    private MediaPlayer mp;
    int music_position;
    public IBinder onBind(Intent arg0){
        return null;
    }
    public void onCreate(){
        mp = MediaPlayer.create(this, R.raw.main_music);
        mp.setLooping(true);
    }
    public void onStart(Intent intent, int startId){

        mp.start();
        super.onStart(intent, startId);
    }
    public void onDestroy(){
        mp.stop();
    }



}


