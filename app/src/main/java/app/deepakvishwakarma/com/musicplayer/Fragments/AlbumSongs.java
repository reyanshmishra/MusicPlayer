package app.deepakvishwakarma.com.musicplayer.Fragments;

import android.content.Intent;
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
import app.deepakvishwakarma.com.musicplayer.Model.AlbumSong;
import app.deepakvishwakarma.com.musicplayer.R;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;

public class AlbumSongs extends AppCompatActivity {
    private RecyclerAlbumSongAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<AlbumSong> mAlbumSongList;
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
        Intent receveAlbumID = getIntent();
        if (receveAlbumID != null) {
            String ID = receveAlbumID.getStringExtra("AlbumID");
            mAlbumID = Integer.parseInt(ID);
            ImageLoader.getInstance().displayImage(String.valueOf(CentraliseMusic.getAlbumArtUri(mAlbumID)), mImageAlbum);
        }
        mAlbumSongList = CentraliseMusic.getAlbumSong(mAlbumID);
        mAdapter = new RecyclerAlbumSongAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.update(mAlbumSongList);


    }
}
