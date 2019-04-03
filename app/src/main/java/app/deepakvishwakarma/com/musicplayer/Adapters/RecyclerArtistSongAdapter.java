package app.deepakvishwakarma.com.musicplayer.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.R;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;

public class RecyclerArtistSongAdapter extends RecyclerView.Adapter<RecyclerArtistSongAdapter.ViewHolder> {
    private ArrayList<Song> mArtistSongList;
    private Context mContext;
    Common mApp;


    public RecyclerArtistSongAdapter(Context context) {
        mContext = context;
        mArtistSongList = new ArrayList<>();
        mApp = (Common) mContext.getApplicationContext();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mSongTitle, mSongArtist, mDuration;
        ImageButton mThree_dot_artist_song;

        //constructor of viewholder class
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mThree_dot_artist_song = itemView.findViewById(R.id.three_dot_artist_song);
            mSongTitle = itemView.findViewById(R.id.artist_song_title);
            mSongArtist = itemView.findViewById(R.id.artist_song_artist);
            mDuration = itemView.findViewById(R.id.artist_song_duration);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //To fetch the songs of album or artist use  the same Song Model instead of creating new on,
            //as song data is going to be same.
//            mApp.getService().playsong(mArtistSongList.get(getAdapterPosition()));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_artist_song, viewGroup, false);
        ViewHolder viewholder = new ViewHolder(view);

        return viewholder;
    }

    public void update(ArrayList<Song> data) {
        mArtistSongList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Song model = mArtistSongList.get(i);
        viewHolder.mSongTitle.setText(model.mTitle);
        viewHolder.mSongArtist.setText(model.mArtist);
        viewHolder.mDuration.setText(CentraliseMusic.makeShortTimeString(mContext, (model.getDURATION() / 1000)));
        viewHolder.mThree_dot_artist_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(viewHolder.mThree_dot_artist_song);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArtistSongList.size();
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new RecyclerArtistSongAdapter.MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.item1:
                    Toast.makeText(mContext, "Item 1", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.item2:
                    Toast.makeText(mContext, "Item 2", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.item3:
                    Toast.makeText(mContext, "Item 3", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
