package app.deepakvishwakarma.com.musicplayer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;

public class PlayingActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    ImageButton mImageButtonBack, mPlayPrevious, mPlayNext;
    TextView mSongName, mArtistName;
    FloatingActionButton mPlayPause;
    ImageView mImage;
    Common mApp;
    Context mContext;
    Song mSong;
    ArrayList<Song> mSongs;
    int mSongPos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playing);
        mToolbar = findViewById(R.id.playing_toolbar);
        setSupportActionBar(mToolbar);
        mContext = getApplicationContext();
        mApp = (Common) getApplicationContext();
        mImage = findViewById(R.id.playing_img);
        mSongs = mApp.getService().getmSongs(); //get the songs list
        mSongPos = mApp.getService().getmSongPos(); //get the songs position
        mSong = mSongs.get(mSongPos); //assign the position of thatsong to song object
        // Now the song has reference of that purticular song so we can access there properties and methods.
        //we can fetch the position and songlist from playbackstarter class also.
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.placeholder)
                .showImageOnFail(R.drawable.placeholder)
                .build();
        ImageLoader.getInstance().displayImage(String.valueOf(CentraliseMusic.getAlbumArtUri(mSong.getALBUM_ID())), mImage, options);
        mImageButtonBack = findViewById(R.id.playing_back);
        mSongName = findViewById(R.id.playing_song_name);
        mArtistName = findViewById(R.id.playing_artist_name);
        mSongName.setText(mSong.getTitle());
        mArtistName.setText(mSong.getArtist());
        mPlayPause = findViewById(R.id.playing_play_pause);
        mPlayPrevious = findViewById(R.id.playing_previous);
        mPlayNext = findViewById(R.id.playing_next);
        mImageButtonBack.setOnClickListener(this);//Back Button of toolbar
        mPlayPause.setOnClickListener(this);
        mPlayNext.setOnClickListener(this);
        mPlayPrevious.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.playing_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.now_playing) {
            Toast.makeText(this, "Now Playing Comming Soon", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_one) {
            Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_two) {
            Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_three) {
            Toast.makeText(this, "Item 3", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_four) {
            Toast.makeText(this, "Item 4", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.playing_back) {
            onBackPressed();
        } else if (id == R.id.playing_play_pause) {
            mApp.getPlayBackStarter().Stopsong();
            //Toast.makeText(this, "Play", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.playing_next) {
            Toast.makeText(this, "Play Next", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.playing_previous) {
            Toast.makeText(this, "Play Previous", Toast.LENGTH_SHORT).show();
        }
    }
}
