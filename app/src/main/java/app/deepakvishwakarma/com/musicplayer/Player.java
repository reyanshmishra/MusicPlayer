package app.deepakvishwakarma.com.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;

import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.Services.MusicService;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;

public class Player extends AppCompatActivity {
    Common mApp;
    ImageView mPlayingImg;
    ImageButton mPlay, mNext, mPrevious;
    // long id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
        mPlayingImg = findViewById(R.id.player_img);
        mPrevious = findViewById(R.id.previous);
        mPlay = findViewById(R.id.play);
        mNext = findViewById(R.id.next);
        mApp = (Common) getApplicationContext();
        Intent intent = getIntent();
        long id = Integer.parseInt(intent.getExtras().getString("SongID"));
        //id = MusicService.getmId();
        ImageLoader.getInstance().displayImage(String.valueOf(CentraliseMusic.getAlbumArtUri(id)), mPlayingImg);
        //  mApp.getService().playsong((Song) id);


    }
}
