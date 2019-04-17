package app.deepakvishwakarma.com.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Adapters.RecyclerNowplayingAdapter;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import es.claucookie.miniequalizerlibrary.EqualizerView;

public class NowPlayingActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    TextView mTextviewTitle;
    RecyclerView mRecyclerview;
    ImageButton mNow_playing_back;
    RecyclerNowplayingAdapter mAdapter;
    ArrayList<Song> mSongList;
    int mSongPos;
    Context mContext;
    Common mApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nowplaying_activity);
        mContext = this.getApplicationContext();
        mApp = (Common) getApplicationContext();
        mToolbar = findViewById(R.id.now_playing_toolbar);
        mNow_playing_back = findViewById(R.id.now_playing_back);
        mTextviewTitle = findViewById(R.id.now_playing_toolbar_title);
        mRecyclerview = findViewById(R.id.now_playing_recyclerview);
        mAdapter = new RecyclerNowplayingAdapter(mContext);
        // Whenever you want to start the animation
       // equalizer.stopBars(); // When you want equalizer stops animating
//        mSongList = CentraliseMusic.getSongs("Song",0);
        mSongList = mApp.getService().getmSongs();
        mSongPos = mApp.getService().getmSongPos();
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.update(mSongList);
        mNow_playing_back.setOnClickListener(this);

       // equalizer.animateBars();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.now_playing_back)
        {
            Intent back=new Intent(mContext,PlayingActivity.class);
            startActivity(back);
            finish();
        }
    }
}
