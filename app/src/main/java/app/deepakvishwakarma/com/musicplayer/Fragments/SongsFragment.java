package app.deepakvishwakarma.com.musicplayer.Fragments;

import android.content.Context;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Adapters.RecyclerSongAdapter;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;
import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.R;

public class SongsFragment extends Fragment {
    private RecyclerSongAdapter mAdapter;
    RecyclerView mRecyclerView;
    ArrayList<Song> mSongList;
    Common mApp;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mSongview = inflater.inflate(R.layout.song_fragment, container, false);
        mRecyclerView = mSongview.findViewById(R.id.recyclerview_song);
        mContext = getContext();
        mApp = (Common) mContext.getApplicationContext();
        mSongList = CentraliseMusic.getSongs();
        mAdapter = new RecyclerSongAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.update(mSongList);
        return mSongview;
    }
}
