package app.deepakvishwakarma.com.musicplayer.Fragments;

import android.content.Context;
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
import app.deepakvishwakarma.com.musicplayer.Adapters.RecyclerArtistAdapter;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;
import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Model.Artist;
import app.deepakvishwakarma.com.musicplayer.R;

public class ArtistFragment extends Fragment {
    private RecyclerArtistAdapter adapter;
    RecyclerView recyclerView;
    private ArrayList<Artist> artistList;
    Common mApp;
    public ArtistFragment()
    {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View artistview = inflater.inflate(R.layout.artist_fragment, container, false);
        recyclerView = (RecyclerView) artistview.findViewById(R.id.recyclerview_artist);
        mApp = (Common) Common.getInstance().getApplicationContext();
        artistList=CentraliseMusic.buildArtistLibrary();
        adapter = new RecyclerArtistAdapter(getContext(), artistList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(1), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
      //  adapter.update(artistList);
        adapter.notifyDataSetChanged();




        return artistview;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
