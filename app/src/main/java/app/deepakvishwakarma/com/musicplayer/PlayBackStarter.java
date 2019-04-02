package app.deepakvishwakarma.com.musicplayer;

import android.content.Context;

import app.deepakvishwakarma.com.musicplayer.Interface.PrepareServiceListener;
import app.deepakvishwakarma.com.musicplayer.Services.MusicService;

public class PlayBackStarter implements PrepareServiceListener {
    Context mContext;
    Common mApp;


    @Override
    public void onServiceRunning(MusicService musicService) {
        mApp = (Common) mContext.getApplicationContext();
        mApp.getService().setPrepareServiceListener(this);

    }
    public PlayBackStarter(Context context) {
        mContext = context;
        mApp = (Common) mContext.getApplicationContext();
    }
}
