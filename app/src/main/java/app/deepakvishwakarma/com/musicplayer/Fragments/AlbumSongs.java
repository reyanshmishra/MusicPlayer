package app.deepakvishwakarma.com.musicplayer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Adapters.RecyclerAlbumSongAdapter;
import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Model.Album;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.R;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;

public class AlbumSongs extends AppCompatActivity {
    private RecyclerAlbumSongAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Song> mAlbumSongList;
    private ImageView mImageAlbum;
    private Common mApp;
    int mAlbumID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_song);
        mImageAlbum = (ImageView) findViewById(R.id.album_song_top_image);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_album_song);
        mApp = (Common) Common.getInstance().getApplicationContext();
        Bundle bundle = getIntent().getExtras();
        //Extract the data from Bundle
        mAlbumID = (int)(long) bundle.get("AlbumID");
        if (getIntent().getExtras() != null) {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .showImageForEmptyUri(R.drawable.placeholder)
                    .showImageOnFail(R.drawable.placeholder)
                    .build();
            ImageLoader.getInstance().displayImage(String.valueOf(CentraliseMusic.getAlbumArtUri(mAlbumID)), mImageAlbum, options);
        }
        mAlbumSongList = CentraliseMusic.getAlbumSong(mAlbumID);
        mAdapter = new RecyclerAlbumSongAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.update(mAlbumSongList);
    }
}
