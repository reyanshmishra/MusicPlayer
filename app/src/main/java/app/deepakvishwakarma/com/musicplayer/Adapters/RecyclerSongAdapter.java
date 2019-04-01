package app.deepakvishwakarma.com.musicplayer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.Player;
import app.deepakvishwakarma.com.musicplayer.R;
import app.deepakvishwakarma.com.musicplayer.Services.MusicService;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;

public class RecyclerSongAdapter extends RecyclerView.Adapter<RecyclerSongAdapter.ViewHolder> {
    private ArrayList<Song> mSongList;
    private Context mContext;
    private Common mApp;
    MusicService mMusicPlayer;

    public RecyclerSongAdapter(Context context) {
        mContext = context;
        mApp = (Common) mContext.getApplicationContext();
        mSongList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_song, viewGroup, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Song model = mSongList.get(i);
        viewHolder.mTitle.setText(model.mTitle);
        viewHolder.mAlbumName.setText(model.mArtist);
        viewHolder.mDuration.setText(CentraliseMusic.makeShortTimeString(mContext, (model.getDURATION() / 1000)));
        // viewHolder.mSongImage.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(String.valueOf(CentraliseMusic.getAlbumArtUri(model.getALBUM_ID())), viewHolder.mSongImage);
    }


    @Override
    public int getItemCount() {
        Log.d("DEEPAK", "" + mSongList.size());
        return mSongList.size();
    }

    public void update(ArrayList<Song> data) {
        mSongList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitle, mAlbumName, mDuration;
        CircularImageView mSongImage;
        CardView mCardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSongImage = itemView.findViewById(R.id.track_image);
            mTitle = itemView.findViewById(R.id.title);
            mAlbumName = itemView.findViewById(R.id.album_name);
            mDuration = itemView.findViewById(R.id.duration);
            mCardView = itemView.findViewById(R.id.cardView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mApp.getService() == null) {
                startservice();
                //   mApp.getService().playsong(mSongList.get(getAdapterPosition()));
            } else {
                mApp.getService().playsong(mSongList.get(getAdapterPosition()));
            }

          /*  if (mMusicPlayer == null) {
                mMusicPlayer = new MusicService(mContext);
                mMusicPlayer.playsong(mSongList.get(getAdapterPosition()));
            } else {
                mMusicPlayer.playsong(mSongList.get(getAdapterPosition()));
            } */
        }
    }

    public void startservice() {
        Intent service = new Intent(mContext, MusicService.class);
        mContext.startService(service);
    }
}
