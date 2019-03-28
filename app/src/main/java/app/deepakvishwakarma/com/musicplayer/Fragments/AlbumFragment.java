package app.deepakvishwakarma.com.musicplayer.Fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Adapters.RecyclerAlbumAdapter;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;
import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Model.Album;
import app.deepakvishwakarma.com.musicplayer.R;

public class AlbumFragment extends Fragment  {
    private RecyclerAlbumAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Album> mAlbumList;
    Common mApp;

    public AlbumFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mAlbumview = inflater.inflate(R.layout.album_fragment, container, false);
        mApp = (Common) Common.getInstance().getApplicationContext();
        mRecyclerView = mAlbumview.findViewById(R.id.recyclerview_album);
        mAlbumList = CentraliseMusic.getAlbums();
        mAdapter = new RecyclerAlbumAdapter(getContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(1), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.update(mAlbumList);



        return mAlbumview;
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

