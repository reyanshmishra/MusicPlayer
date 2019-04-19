package app.deepakvishwakarma.com.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;

public class PlayingActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    Toolbar mToolbar;
    ImageButton mImageButtonBack, mPlayPrevious, mPlayNext, mShuffle, mRepeat;
    TextView mSongName;
    TextView mArtistName;
    TextView mSongCurrentTime, mSongTotalTime;
    FloatingActionButton mPlayPause;
    SeekBar mSeekBar;
    ImageView mImage;
    Common mApp;
    Context mContext;
    Song mSong;
    ArrayList<Song> mSongs;
    int mSongPos;
    private Handler mHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playing);
        mToolbar = findViewById(R.id.playing_toolbar);
        setSupportActionBar(mToolbar);
        mContext = getApplicationContext();
        mApp = (Common) getApplicationContext();
        mApp.setmPlayingActivity(this);
        mSongs = mApp.getService().getmSongs(); //get the songs list
        mSongPos = mApp.getService().getmSongPos(); //get the songs position
        //assign the position of thatsong to song object
        // Now the song has reference of that purticular song so we can access there properties and methods.
        //we can fetch the position and songlist from playbackstarter class also.
        mSongName = findViewById(R.id.playing_song_name);
        mArtistName = findViewById(R.id.playing_artist_name);
        mImage = findViewById(R.id.playing_img);
        displayImage(mSongs,mSongPos);
        mSongCurrentTime = findViewById(R.id.playing_current_time);
        mSongTotalTime = findViewById(R.id.playing_total_time);
        mImageButtonBack = findViewById(R.id.playing_back);
        mPlayPause = findViewById(R.id.playing_play_pause);
        mPlayPrevious = findViewById(R.id.playing_previous);
        mPlayNext = findViewById(R.id.playing_next);
        mShuffle = findViewById(R.id.playing_shuffle);
        mRepeat = findViewById(R.id.playing_repeat);
        mSeekBar = findViewById(R.id.playing_seekbar);
       /* mSeekBar.setProgress(0);// To set initial progress, i.e zero in starting of the song
        mSeekBar.setMax((int) (long) mSong.getDURATION());*/
        mSeekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        //------setting OnClickListenter on below views---------
        mImageButtonBack.setOnClickListener(this);//Back Button of toolbar
        mPlayPause.setOnClickListener(this);
        mPlayNext.setOnClickListener(this);
        mPlayPrevious.setOnClickListener(this);
        mRepeat.setOnClickListener(this);
        mShuffle.setOnClickListener(this);
        mHandler = new Handler();
        mSeekBar.setOnSeekBarChangeListener(this);
        updateProgressBar(); //once call this method then he initialised progressbar.
    }

    public void displayImage(ArrayList<Song> mSongs,int mSongPos) {
        mSong = mSongs.get(mSongPos);
        mSongName.setText(mSong.getTitle());
        mArtistName.setText(mSong.getArtist());
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.placeholder)
                .showImageOnFail(R.drawable.placeholder)
                .build();
        ImageLoader.getInstance().displayImage(String.valueOf(CentraliseMusic.getAlbumArtUri(mSong.getALBUM_ID())), mImage, options);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.playing_back) {
            onBackPressed();
        } else if (id == R.id.playing_play_pause) {
            mApp.getPlayBackStarter().Stopsong();
            Drawable drawable = mPlayPause.getDrawable();
            if (drawable.getConstantState().equals(getResources().getDrawable(R.drawable.pause).getConstantState())) {
                mPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.play));
            } else {
                mPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.pause));
            }
        } else if (id == R.id.playing_next) {
            Toast.makeText(this, "Play Next", Toast.LENGTH_SHORT).show();
            playnext();
        } else if (id == R.id.playing_previous) {
            Toast.makeText(this, "Play Previous", Toast.LENGTH_SHORT).show();
            playprevious();
        } else if (id == R.id.playing_shuffle) {
            mApp.getPlayBackStarter().ShuffleSong();
            Drawable drawable = mShuffle.getDrawable();
            if (drawable.getConstantState().equals(getResources().getDrawable(R.drawable.shuffle).getConstantState())) {
                mShuffle.setImageDrawable(getResources().getDrawable(R.drawable.shuffle_on));
            } else {
                mShuffle.setImageDrawable(getResources().getDrawable(R.drawable.shuffle));
            }
            Toast.makeText(this, "Play Shuffle", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.playing_repeat) {
            mApp.getPlayBackStarter().RepeatSong();
            Drawable drawable = mRepeat.getDrawable();
            if (drawable.getConstantState().equals(getResources().getDrawable(R.drawable.repeat).getConstantState())) {
                mRepeat.setImageDrawable(getResources().getDrawable(R.drawable.repeat_one));
            } else {
                mRepeat.setImageDrawable(getResources().getDrawable(R.drawable.repeat));
            }
            Toast.makeText(this, "Play Repeat", Toast.LENGTH_SHORT).show();
        }
    }

    public void playnext() {
        mApp.getPlayBackStarter().NextSong();
        mSongs = mApp.getService().getmSongs(); //get the songs list
        mSongPos = mApp.getService().getmSongPos(); //get the songs position
        mSong = mSongs.get(mSongPos);
        mSongName.setText(mSong.getTitle());
        mArtistName.setText(mSong.getArtist());
        displayImage(mSongs, mSongPos);
    }

    public void playprevious() {
        mApp.getPlayBackStarter().PreviousSong();
        mSongs = mApp.getService().getmSongs(); //get the songs list
        mSongPos = mApp.getService().getmSongPos(); //get the songs position
        mSong = mSongs.get(mSongPos);
        mSongName.setText(mSong.getTitle());
        mArtistName.setText(mSong.getArtist());
        displayImage(mSongs, mSongPos);
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     * A Handler allows you to send and process Message and Runnable objects associated with a thread's MessageQueue. Each Handler instance     * is associated with a single thread and that thread's message queue. When you create a new Handler, it is bound to
     * the thread /  message queue of the thread that is creating it -- from that point on, it will deliver messages and runnables
     * to that  message queue and    execute them as they come out of the message queue.
     * <p> There are two main uses for a Handler:
     * (1) to schedule messages and runnables to be executed at some point in the future; and
     * (2) to enqueue an action to be performed on a different thread than your own.
     * <p> Scheduling messages is accomplished with the post(Runnable),postDelayed(Runnable) and more...
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        @SuppressLint("SetTextI18n")
        public void run() {
            int totalDuration = (int) (long) mSong.getDURATION();
            long currentDuration = mApp.getService().getMp().getCurrentPosition();
            int currentposition = (int) currentDuration / 1000;
            // Displaying Total Duration time
            mSongTotalTime.setText("" + CentraliseMusic.makeShortTimeString(mContext, mSong.getDURATION() / 1000));
            // Displaying time completed playing
            mSongCurrentTime.setText("" + CentraliseMusic.makeShortTimeString(mContext, currentposition));
            int currentPosition = CentraliseMusic.progressToTimer(mSeekBar.getProgress(), totalDuration);
            mHandler.postDelayed(this, 100);
        }
    };


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        long currentSongDuration = mApp.getService().getMp().getDuration();
        seekBar.setMax((int) currentSongDuration / 1000);
        int currentposition = (int) currentSongDuration / 1000;
        mApp.getService().getMp().seekTo(currentposition);
        mSeekBar.setProgress(currentposition);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int seekBarPosition = seekBar.getProgress();
        // update timer progress again
        mApp.getService().getMp().seekTo(seekBarPosition * 1000);
    }

    //----------------------------------------------------  Menu ---------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.playing_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.now_playing) {
            Intent nowplaying = new Intent(mContext, NowPlayingActivity.class);
            startActivity(nowplaying);
            finish();
            // Toast.makeText(this, "Now Playing Comming Soon", Toast.LENGTH_SHORT).show();
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
}
