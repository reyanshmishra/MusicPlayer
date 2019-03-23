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
    private RecyclerAlbumSongAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<AlbumSong> AlbumSongList;
    ImageView mImageAlbum;
    Common mApp;
    int AlbumID;
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_song);
        mImageAlbum = (ImageView) findViewById(R.id.album_song_top_image);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_album_song);
        mApp = (Common) Common.getInstance().getApplicationContext();
        Intent receveAlbumID = getIntent();
        if (receveAlbumID != null) {
            String ID = receveAlbumID.getStringExtra("AlbumID");
            AlbumID = Integer.parseInt(ID);
            options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .resetViewBeforeLoading(true)
                    .showImageForEmptyUri(R.drawable.placeholder)
                    .showImageOnFail(R.drawable.placeholder)
                    .showImageOnLoading(R.drawable.placeholder).build();
            imageLoader.displayImage(String.valueOf(CentraliseMusic.getAlbumArtUri(AlbumID)), mImageAlbum, options);
        }
        AlbumSongList = CentraliseMusic.buildAlbumSongtLibrary(AlbumID);
        adapter = new RecyclerAlbumSongAdapter(this, AlbumSongList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();


        // adapter.update(AlbumSongList);
        //  notifyDataSetChanged();
    }
}
