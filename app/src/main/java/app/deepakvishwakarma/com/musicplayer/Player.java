package app.deepakvishwakarma.com.musicplayer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

import app.deepakvishwakarma.com.musicplayer.Model.Song;

public class Player extends AppCompatActivity {
    Common mApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
        mApp = (Common)getApplicationContext();
        Intent intent = getIntent();
        Serializable song = intent.getExtras().getSerializable("Song");
        Song str= (Song) song;
        mApp.getService().playsong(str);


    }
}
