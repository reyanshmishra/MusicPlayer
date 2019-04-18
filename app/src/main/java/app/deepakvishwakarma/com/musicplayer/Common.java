package app.deepakvishwakarma.com.musicplayer;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import app.deepakvishwakarma.com.musicplayer.Services.MusicService;

public class Common extends Application {
    private static Context mContext;
    private MusicService mService;
    PlayBackStarter mPlayBackStarter;
    PlayingActivity mPlayingActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mPlayBackStarter =new PlayBackStarter(mContext);
        imageloader();
    }

    public static Context getInstance() {
        return mContext;
    }

    public MusicService getService() {
        return mService;
    }

    public void setService(MusicService service) {
        mService = service;
    }

    public PlayBackStarter getPlayBackStarter() {
        return mPlayBackStarter;
    }

    public void imageloader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .build();

        ImageLoader.getInstance().init(config);
    }
    public PlayingActivity getmPlayingActivity() {
        return mPlayingActivity;
    }
    public void setmPlayingActivity(PlayingActivity playingActivity) {
        mPlayingActivity = playingActivity;
    }

}



