package app.deepakvishwakarma.com.musicplayer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.MusicService;
import app.deepakvishwakarma.com.musicplayer.OnAdapterItemClicked;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.R;

public class RecyclerSongAdapter extends RecyclerView.Adapter<RecyclerSongAdapter.ViewHolder> {
    private ArrayList<Song> songList;
    private Context mContext;
    ImageLoader imageLoader = ImageLoader.getInstance();
    private OnAdapterItemClicked mAdapterClickListener;
    DisplayImageOptions options;
    Common mApp;
    Song model;
    int id;

    public RecyclerSongAdapter(Context context, ArrayList<Song> songList, OnAdapterItemClicked listener) {
        this.mContext = context;
        this.songList = songList;
        mApp = (Common) mContext.getApplicationContext();
        mAdapterClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextView2, mTextView3, mTextView4;
        CircularImageView track_image;
        CardView view_song;


        //constructor of viewholder class
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            track_image = (CircularImageView) itemView.findViewById(R.id.track_image);
         //   mTextView1 = (TextView) itemView.findViewById(R.id.textview1);
            mTextView2 = (TextView) itemView.findViewById(R.id.textview2);
            mTextView3 = (TextView) itemView.findViewById(R.id.textview3);
            mTextView4 = (TextView) itemView.findViewById(R.id.textview4);
            view_song = (CardView)itemView.findViewById(R.id.view_song);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            MusicService ms = new MusicService();
            ms.onItemClicked(songList,getAdapterPosition());
            //ms.playSongs(songList, getAdapterPosition());
        }
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
        model = songList.get(i);
        id = Integer.parseInt(String.valueOf(model.get_ID()));
      //  viewHolder.mTextView1.setText(String.valueOf(model.get_ID()));
        // viewHolder.mTextView1.setText(model.getALBUM());

        viewHolder.mTextView2.setText(model.getTITLE());
        viewHolder.mTextView3.setText(model.getARTIST());
        viewHolder.mTextView4.setText(CentraliseMusic.makeShortTimeString(mContext,(model.getDURATION()/1000)));
        viewHolder.track_image.setVisibility(View.VISIBLE);
     /*   viewHolder.view_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicService ms =new MusicService();
                ms.start(id);
            }
        });  */

       options = new DisplayImageOptions.Builder().cacheInMemory(true)
               .resetViewBeforeLoading(true)
               .showImageForEmptyUri(R.drawable.placeholder)
               .showImageOnFail(R.drawable.placeholder)
               .showImageOnLoading(R.drawable.placeholder)
               .build();
        imageLoader.displayImage(String.valueOf(CentraliseMusic.getAlbumArtUri(model.getALBUM_ID())), viewHolder.track_image, options);
    }


    @Override
    public int getItemCount() {
        return songList.size();
    }
}
