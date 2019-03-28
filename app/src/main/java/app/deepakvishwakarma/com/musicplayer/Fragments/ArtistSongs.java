package app.deepakvishwakarma.com.musicplayer.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Adapters.RecyclerArtistSongAdapter;
import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Model.ArtistSong;
import app.deepakvishwakarma.com.musicplayer.R;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;

public class ArtistSongs extends AppCompatActivity {
    private RecyclerArtistSongAdapter mAdapter;
    RecyclerView mRecyclerView;
    ArrayList<ArtistSong> mArtistSongList;
    Common mApp;
    int ArtistID;
    ImageView mImageArtist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_song);
        mRecyclerView = findViewById(R.id.recyclerview_artist_song);
        mImageArtist = findViewById(R.id.artist_song_top_image);
        mApp = (Common) Common.getInstance().getApplicationContext();
        Intent receveAlbumID = getIntent();
        if (receveAlbumID != null) {
            String ID = receveAlbumID.getStringExtra("ArtistID");
            ArtistID = Integer.parseInt(ID);
            ImageLoader.getInstance().displayImage(String.valueOf(CentraliseMusic.getAlbumArtUri(ArtistID)), mImageArtist);
        }
        mArtistSongList = CentraliseMusic.getArtistSong(ArtistID);
        mAdapter = new RecyclerArtistSongAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.update(mArtistSongList);

    }
}
