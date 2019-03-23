package app.deepakvishwakarma.com.musicplayer.Fragments;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import app.deepakvishwakarma.com.musicplayer.Adapters.RecyclerSongAdapter;
import app.deepakvishwakarma.com.musicplayer.OnAdapterItemClicked;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;
import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.R;

public class SongsFragment extends Fragment implements OnAdapterItemClicked {
    private RecyclerSongAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Song> songList = new ArrayList<Song>();
    Common mApp;
    private MediaPlayer mMediaPlayer;
  //  private Handler myHandler = new Handler();

    public SongsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View songview = inflater.inflate(R.layout.song_fragment, container, false);
        recyclerView = (RecyclerView) songview.findViewById(R.id.recyclerview_song);
        mApp = (Common) Common.getInstance().getApplicationContext();
        songList=CentraliseMusic.buildSongtLibrary();
        adapter = new RecyclerSongAdapter(getContext(),songList,  this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    //    adapter.update(songList);
        adapter.notifyDataSetChanged();
       /*  adapter.setOnItemClickListener(new RecyclerSongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Song obj, int position) {

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mMediaPlayer = new MediaPlayer();
                                mMediaPlayer.setDataSource(obj.getDATA());
                                mMediaPlayer.prepareAsync();
                                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
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
            });
            */




        return songview;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void OnPopUpMenuClicked(View view, int position) {

    }

    @Override
    public void OnShuffledClicked() {

    }
}
