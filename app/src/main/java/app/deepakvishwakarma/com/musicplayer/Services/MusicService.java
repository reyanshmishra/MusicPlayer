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
import app.deepakvishwakarma.com.musicplayer.Model.Album;
import app.deepakvishwakarma.com.musicplayer.Model.AlbumSong;
import app.deepakvishwakarma.com.musicplayer.Model.Artist;
import app.deepakvishwakarma.com.musicplayer.Model.ArtistSong;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.PlayBackStarter;


public class MusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
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
        mp.setOnPreparedListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d("Start", "service start commond");
        setPrepareServiceListener(mApp.getPlayBackStarter());
        getPrepareServiceListener().onServiceRunning(this);

        return START_NOT_STICKY;
    }

    public PrepareServiceListener getPrepareServiceListener() {
        return mPrepareServiceListener;
    }

    public void setPrepareServiceListener(PrepareServiceListener prepareServiceListener) {
        mPrepareServiceListener = prepareServiceListener;
    }


    //Service class not regitered in manifest without constructor
    public MusicService() {
    }

    public MusicService(Context context) {
        mContext = context;
        mp.setOnErrorListener(this);
        mp.setOnPreparedListener(this);
    }

    public void playsong(Song song) {
        try {
            mp.reset();
            //   setmId(song.get_ID());
            mp.setDataSource(song.getDATA());
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playsong(Album song) {
        try {
            mp.reset();
            mp.setDataSource(String.valueOf(song.get_Id()));
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playsong(Artist song) {
        try {
            mp.reset();
            mp.setDataSource(String.valueOf(song.get_artistId()));
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playsong(AlbumSong song) {
        try {
            mp.reset();
            mp.setDataSource(song.getDATA());
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playsong(ArtistSong song) {
        try {
            mp.reset();
            mp.setDataSource(song.getDATA());
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPrepared(MediaPlayer player) {
        Toast.makeText(mContext, "MediaPlayer prepared", Toast.LENGTH_SHORT).show();
        mp.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.d("TAG", what + "+" + extra);
        return false;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {

    }

    //creating interface
    public interface PrepareServiceListener {
        void onServiceRunning(MusicService musicService);
    }
}