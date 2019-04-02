package app.deepakvishwakarma.com.musicplayer.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Interface.PrepareServiceListener;
import app.deepakvishwakarma.com.musicplayer.Model.Album;
import app.deepakvishwakarma.com.musicplayer.Model.AlbumSong;
import app.deepakvishwakarma.com.musicplayer.Model.Artist;
import app.deepakvishwakarma.com.musicplayer.Model.ArtistSong;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.PlayBackStarter;


public class MusicService extends Service implements MediaPlayer.OnErrorListener {
    private Context mContext;
    private MediaPlayer mp;
    private Common mApp;
    private PrepareServiceListener mPrepareServiceListener;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mApp = (Common) getApplicationContext();
        mApp.setService(this);
        mp = new MediaPlayer();
        mp.setOnErrorListener(this);
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


    public void playSong(Song song) {
        try {
            mp.reset();
            mp.setDataSource(song.getDATA());
            mp.prepare();
            /*After preparing we can directly start the player as we are not streaming and not using prepareAsync,
            so we won't need onPrepareListener we can remove that as well.
            */
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}