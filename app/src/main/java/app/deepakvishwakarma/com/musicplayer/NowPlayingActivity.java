package app.deepakvishwakarma.com.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Adapters.RecyclerNowplayingAdapter;
import app.deepakvishwakarma.com.musicplayer.Interface.OnStartDragListener;
import app.deepakvishwakarma.com.musicplayer.Interface.SimpleItemTouchHelperCallback;
import app.deepakvishwakarma.com.musicplayer.Model.Song;

public class NowPlayingActivity extends AppCompatActivity implements View.OnClickListener, OnStartDragListener {
    Toolbar mToolbar;
    TextView mTextviewTitle;
    RecyclerView mRecyclerview;
    ImageButton mNow_playing_back;
    RecyclerNowplayingAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    ArrayList<Song> mSongList;

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
        mAdapter = new RecyclerNowplayingAdapter(mContext,this);
        mSongList = mApp.getService().getmSongs();
        mRecyclerview.setAdapter(mAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerview);
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

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        Intent intentbackpressed = new Intent(getApplicationContext(), PlayingActivity.class);
        startService(intentbackpressed);
        finish();
    }
}
