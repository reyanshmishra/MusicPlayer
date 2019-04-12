package app.deepakvishwakarma.com.musicplayer.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Interface.PrepareServiceListener;
import app.deepakvishwakarma.com.musicplayer.Model.Song;


public class MusicService extends Service implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private Context mContext;
    private MediaPlayer mp;
    private Common mApp;
    private PrepareServiceListener mPrepareServiceListener;
    ArrayList<Song> mSongs;
    int mSongPos;
    Boolean mIsServicePlaying;

    public ArrayList<Song> getmSongs() {
        return mSongs;
    }

    public int getmSongPos() {
        return mSongPos;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mApp = (Common) getApplicationContext();
        mApp.setService(this);
        mp = new MediaPlayer();
        mp.setOnErrorListener(this);
        mp.setOnCompletionListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d("Start", "service start commond");

        setPrepareServiceListener(mApp.getPlayBackStarter());
        //Either do this
        getPrepareServiceListener().onServiceRunning(this);
        //or do this
        //mPrepareServiceListener.onServiceRunning(this);
        return START_NOT_STICKY;
    }


    public void playSong(ArrayList<Song> songs, int position) {
        try {
            mSongs = songs;
            mSongPos = position;
            mp.reset();
            mp.setDataSource(songs.get(position).getDATA());
            mp.prepare();
            /*After preparing we can directly start the player as we are not streaming and not using prepareAsync,
            so we won't need onPrepareListener we can remove that as well.
            */
            mp.start();
            setmIsServicePlaying(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stopSong()
    {
        if(!mp.isPlaying())
        {
            mp.start();
        }
        else {
            mp.pause();
        }
    }




    public Boolean getmIsServicePlaying() {
        return mIsServicePlaying;
    }

    public void setmIsServicePlaying(Boolean mIsServicePlaying) {
        this.mIsServicePlaying = mIsServicePlaying;
    }

    public MediaPlayer getMp() {
        return mp;
    }

    public void setMp(MediaPlayer mp) {
        this.mp = mp;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.d("TAG", what + "+" + extra);
        return false;
    }


    public PrepareServiceListener getPrepareServiceListener() {
        return mPrepareServiceListener;
    }

    public void setPrepareServiceListener(PrepareServiceListener prepareServiceListener) {
        mPrepareServiceListener = prepareServiceListener;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}