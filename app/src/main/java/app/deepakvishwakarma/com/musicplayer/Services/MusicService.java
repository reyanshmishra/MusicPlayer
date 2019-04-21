package app.deepakvishwakarma.com.musicplayer.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Interface.PrepareServiceListener;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.NowPlayingActivity;
import app.deepakvishwakarma.com.musicplayer.PlayingActivity;


public class MusicService extends Service implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private Context mContext;
    private MediaPlayer mp;
    private Common mApp;
    private PrepareServiceListener mPrepareServiceListener;
    ArrayList<Song> mSongs;
    int mSongPos;
    Boolean mIsServicePlaying;
    Boolean isRepeat = false;
    Boolean isShuffle = false;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mApp = (Common) getApplicationContext();
        mApp.setService(this);
        mp = new MediaPlayer();
        mp.setOnErrorListener(this);
        mp.setOnCompletionListener(this);
        mp.setOnCompletionListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        setPrepareServiceListener(mApp.getPlayBackStarter());
        getPrepareServiceListener().onServiceRunning(this);
        return START_NOT_STICKY;
    }


    public void playSong(ArrayList<Song> songs, int position) {
        try {
            mSongs = songs;
            mSongPos = position;
            mp.reset();
            mp.setDataSource(songs.get(position).getDATA());
            mp.prepare();
            mp.start();
            setmIsServicePlaying(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopSong() {
        if (!mp.isPlaying()) {
            mp.start();
        } else {
            mp.pause();
        }
    }

    public ArrayList<Song> getmSongs() {
        return mSongs;
    }

    public int getmSongPos() {
        return mSongPos;
    }

    public Song getSong(){
        return mSongs.get(mSongPos);
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
        mp.release();
    }

    public Boolean getRepeat() {
        return isRepeat;
    }

    public void setRepeat(Boolean repeat) {
        isRepeat = repeat;
    }

    public Boolean getShuffle() {
        return isShuffle;
    }

    public void setShuffle(Boolean shuffle) {
        isShuffle = shuffle;
    }

    /**
     * On Song Playing completed
     * if repeat is ON play same song again
     * if shuffle is ON play random song
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (isRepeat) {
            // repeat is on play same song again
            playSong(mSongs, mSongPos);
        } else if (isShuffle) {
            // shuffle is on - play a random song
            Random rand = new Random();
            mSongPos = rand.nextInt((mSongs.size() - 1) + 1);
            playSong(mSongs, mSongPos);
        } else {
            // no repeat or shuffle ON - play next song
                if (mSongPos < (mSongs.size() - 1)) {
                    mSongPos = mSongPos + 1;
                    playSong(mSongs, mSongPos);
                    mApp.getmPlayingActivity().displayImage(mSongs,mSongPos);
                } else {
                    // play first song
                    mSongPos = 0;
                    playSong(mSongs, mSongPos);
                    mApp.getmPlayingActivity().displayImage(mSongs,mSongPos);
                }
            }
    }
}