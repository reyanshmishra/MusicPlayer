package app.deepakvishwakarma.com.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Interface.PrepareServiceListener;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.Services.MusicService;

public class PlayBackStarter implements PrepareServiceListener {
    Context mContext;
    Common mApp;
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
        mApp.getPlayBackStarter().playSong(mSongs,mPos);

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
            mApp.getService().playSong(mSongs,mPos);
        }
    }


}
