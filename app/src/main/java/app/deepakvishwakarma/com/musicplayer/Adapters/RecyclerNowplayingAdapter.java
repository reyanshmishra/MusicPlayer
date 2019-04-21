package app.deepakvishwakarma.com.musicplayer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Interface.ItemTouchHelperAdapter;
import app.deepakvishwakarma.com.musicplayer.Interface.OnStartDragListener;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.PlayingActivity;
import app.deepakvishwakarma.com.musicplayer.R;
import es.claucookie.miniequalizerlibrary.EqualizerView;


public class RecyclerNowplayingAdapter extends RecyclerView.Adapter<RecyclerNowplayingAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private ArrayList<Song> mSongList;
    private Context mContext;
    private Common mApp;
    private Song mSong;
    private final OnStartDragListener mDragStartListener;

    public RecyclerNowplayingAdapter(Context context, OnStartDragListener dragStartListener) {
        mContext = context;
        mDragStartListener = dragStartListener;
        mApp = (Common) mContext.getApplicationContext();
        mSongList = new ArrayList<>();
        mSong = mApp.getService().getSong();
    }

    @NonNull
    @Override
    public RecyclerNowplayingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_nowplaying, viewGroup, false);
        RecyclerNowplayingAdapter.ViewHolder viewholder = new RecyclerNowplayingAdapter.ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerNowplayingAdapter.ViewHolder viewHolder, int i) {
        Song model = mSongList.get(i);
        viewHolder.mTitle.setText(model.mTitle);
        viewHolder.mAlbumName.setText(model.mArtist);

        if (mSongList.get(i).mTitle.equalsIgnoreCase(mSong.mTitle)) {
            viewHolder.equalizer.setVisibility(View.VISIBLE);
            viewHolder.equalizer.animateBars();
        } else {
            viewHolder.equalizer.setVisibility(View.GONE);
        }
        //Drag and drop listener
        viewHolder.mReorder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    //We can use either MotionEvent.ACTION_UP || MotionEvent.ACTION_DOWN also
                    mDragStartListener.onStartDrag(viewHolder);
                    //here call the interafce onstartdrag method
                }
                notifyDataSetChanged();
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        Log.d("TotalListCount", "" + mSongList.size());
        return mSongList.size();
    }

    public void update(ArrayList<Song> data) {
        mSongList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitle, mAlbumName;
        ImageButton mReorder;
        CardView mCardView;
        private EqualizerView equalizer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mAlbumName = itemView.findViewById(R.id.album_name);
            mReorder = itemView.findViewById(R.id.nowplaying_dragicon);
            mCardView = itemView.findViewById(R.id.cardView);
            equalizer = itemView.findViewById(R.id.equalizer_view);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            /*PlayBackStarter will always be available as it gets started by the Application class as soon as app opens.*/
            // mApp.getPlayBackStarter().playSong(mSongList.get(getAdapterPosition()));
            mApp.getPlayBackStarter().playSong(mSongList, getAdapterPosition());
            Intent playing = new Intent(mContext, PlayingActivity.class);
            mContext.startActivity(playing);
        }
    }


    //ItemTouchHelperAdapter interface method
    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mSongList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mSongList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        //abstract method of Public abstract static class Adapter<VH extends RecyclerView.ViewHolder> {...}
        notifyDataSetChanged();
        return true;
    }

    //ItemTouchHelperAdapter interface method
    @Override
    public void onItemDismiss(int position) {
        mSongList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}



