package app.deepakvishwakarma.com.musicplayer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class PlayingActivity extends AppCompatActivity {
    ImageView mImage;
    Common mApp;
    Context mContext;
    long mSongId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playing);
        mContext = getApplicationContext();
        mApp = (Common) getApplicationContext();
        mImage = findViewById(R.id.playing_img);
/*        mSongId = mApp.getService().getSong();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.placeholder)
                .showImageOnFail(R.drawable.placeholder)
                .build();
        ImageLoader.getInstance().displayImage(String.valueOf(CentraliseMusic.getAlbumArtUri(mSongId)), mImage, options);*/

    }
}
