package app.deepakvishwakarma.com.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Interface.PrepareServiceListener;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.Services.MusicService;

public class PlayBackStarter implements PrepareServiceListener {
    Context mContext;
    Common mApp;
    Boolean isRepeat = false;
    Boolean isShuffle = false;
    private ArrayList<Song> mSongs;
    private int mPos;

    public PlayBackStarter(Context context) {
        mContext = context;
        mApp = (Common) mContext.getApplicationContext();
    }

    //After service has started this method will be invoked by service from onStartCommand
    //Now you are sure that service has started so now go ahead and play that song
    @Override
    public void onServiceRunning(MusicService musicService) {
        //This method is called only once ie. after service has been initialized.
        //Either do this
//        musicService.playsong(mSong);
        //or do this both will work.
        mApp.getPlayBackStarter().playSong(mSongs, mPos);

    }

    /*From here we will start to play the song and will start service if its not already start instead of from
    the adapters and now this method will be called from the adapter.*/
    public void playSong(ArrayList<Song> songs, int position) {
        //Make the clicked song globally available so we can access it anywhere in PlayBackStarter class.
        mSongs = songs;
        mPos = position;
       /* If getService returns null that means service has not started and we need to start and wait for it to start
        Once started onStartCommand will invoke above method ie. onServiceRunning from there we will play the song
        if getService doesn't return null that means service has already started and we can start playing the song
        without waiting like below*/
        if (mApp.getService() == null) {
            Log.d("DEEPAK", "This is being called for the first time after clicking on the song as service is null, this should only be call once in the entire lifetime of the app.");
            /*Below code will start the service async way so we need to play the song from onServiceRunning after this.*/
            mApp.startService(new Intent(mApp, MusicService.class));
        } else {
            Log.d("DEEPAK", "Now the service has started only this block will execute everytime you click on a song.");
            //This will be called if the service has started and is available because once service has started we need not to start it again.
            mApp.getService().playSong(mSongs, mPos);
        }
    }

    public void Stopsong() {
        if (mApp.getService().getmIsServicePlaying() == true) {
            mApp.getService().stopSong();
            Toast.makeText(mContext, "Pause", Toast.LENGTH_SHORT).show();
        } else {
            mApp.getService().stopSong();
        }
    }

    public void NextSong() {
        if (mPos < (mSongs.size() - 1)) {
            mPos = mPos + 1;
            mApp.getService().playSong(mSongs, mPos);
        } else {
            // play first song
            mApp.getService().playSong(mSongs, 0);
            mPos = 0;
        }
    }

    public void PreviousSong() {
        if (mPos > 0) {
            mPos = mPos - 1;
            mApp.getService().playSong(mSongs, mPos);
        } else {
            // play last song
            mPos = mSongs.size() - 1;
            mApp.getService().playSong(mSongs, mPos);
        }
    }
    public void RepeatSong() {
        if (isRepeat) {
            isRepeat = false;
            Toast.makeText(mContext, "Repeat is OFF", Toast.LENGTH_SHORT).show();
          //  btnRepeat.setImageResource(R.drawable.btn_repeat);
        } else {
            // make repeat to true
            isRepeat = true;
            Toast.makeText(mContext, "Repeat is ON", Toast.LENGTH_SHORT).show();
            // make shuffle to false
            isShuffle = false;
          //  btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
          //  btnShuffle.setImageResource(R.drawable.btn_shuffle);
        }
    }
    public void ShuffleSong() {
        if(isShuffle){
            isShuffle = false;
            Toast.makeText(mContext, "Shuffle is OFF", Toast.LENGTH_SHORT).show();
           // btnShuffle.setImageResource(R.drawable.btn_shuffle);
        }else{
            // make repeat to true
            isShuffle= true;
            Toast.makeText(mContext, "Shuffle is ON", Toast.LENGTH_SHORT).show();
            // make shuffle to false
            isRepeat = false;
         //   btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
          //  btnRepeat.setImageResource(R.drawable.btn_repeat);
        }
    }
}
