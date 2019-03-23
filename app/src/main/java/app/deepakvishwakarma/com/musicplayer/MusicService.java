package app.deepakvishwakarma.com.musicplayer;

import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Model.Song;

public class MusicService extends Service {
    private boolean mMediaPlayerPrepared = false;
    private int mSongPos = 0;
    private Service mService;
    private Context mContext;
    private ArrayList<Song> songList;
    private ArrayList<Song> mSongs;
    private int mPos;
    private MediaPlayer mp;
    private Common mApp;
    private Handler myHandler;
    private PowerManager.WakeLock mWakeLock;
    private MediaPlayer mMediaPlayer1;
    private boolean isServiceRunning = false;
    private AudioManager mAudioManager;
    private AudioManagerHelper mAudioManagerHelper;

    //In Service it called first
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mService = this;
        mApp = (Common) Common.getInstance().getApplicationContext();
        songList = new ArrayList<Song>();
        mp = new MediaPlayer();
        mWakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(1, getClass().getName());
        mWakeLock.setReferenceCounted(false);
        myHandler = new Handler();
        mMediaPlayer1 = new MediaPlayer();
        mMediaPlayer1.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer1.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public void onItemClicked(ArrayList<Song> songList , int adapterPosition) {
        mSongs = songList;
        mPos = adapterPosition;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    mp = new MediaPlayer();
                    mp.setDataSource(String.valueOf(songList.get(adapterPosition).get_ID()));
                    mp.prepareAsync();
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myHandler.postDelayed(runnable, 100);

    }
    public Uri getUri(long audioId) {
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, audioId);
    }


    //In Service it called second
    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    //In Service it called last
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
