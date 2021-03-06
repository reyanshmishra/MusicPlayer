package app.deepakvishwakarma.com.musicplayer.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Fragments.ArtistSongs;
import app.deepakvishwakarma.com.musicplayer.Model.Artist;
import app.deepakvishwakarma.com.musicplayer.R;
import app.deepakvishwakarma.com.musicplayer.Services.MusicService;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;

public class RecyclerArtistAdapter extends RecyclerView.Adapter<RecyclerArtistAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<Artist> mArtistList;
    private Context mContext;
    Common mApp;

    public RecyclerArtistAdapter(Context context) {
        mContext = context;
        mApp = (Common) mContext.getApplicationContext();
        mArtistList = new ArrayList<>();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mArtistName, mNoArtistTrack, mNoAlbumTrack;
        ImageView mArtist_image;
        ImageButton mImg_btn_option;
        CardView mCard_view;

        //constructor of viewholder class
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCard_view = itemView.findViewById(R.id.card_view_artist);
            mArtist_image = itemView.findViewById(R.id.artist_img);
            mArtistName = itemView.findViewById(R.id.artist_name);
            mNoArtistTrack = itemView.findViewById(R.id.artist_count);
            mNoAlbumTrack = itemView.findViewById(R.id.album_count);
            mImg_btn_option = itemView.findViewById(R.id.artist_img_btn_option);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mApp.getService() == null) {
                mContext.startService(new Intent(mContext, MusicService.class));
//                mApp.getService().playsong(mArtistList.get(getAdapterPosition()));
            } else {
//                mApp.getService().playsong(mArtistList.get(getAdapterPosition()));
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_artist, viewGroup, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    public void update(ArrayList<Artist> data) {
        mArtistList.addAll(data);
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Artist model = mArtistList.get(i);
        String id = String.valueOf(model.get_artistId());

        viewHolder.mArtistName.setText(model.get_artistName());
        viewHolder.mNoArtistTrack.setText(String.valueOf(model.get_noOfTracksByArtist()) + " Songs | ");
        viewHolder.mNoAlbumTrack.setText(String.valueOf(model.get_noOfAlbumsByArtist()) + " Albums");


        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.placeholder)
                .showImageOnFail(R.drawable.placeholder)
                .build();
        ImageLoader.getInstance().displayImage(String.valueOf(CentraliseMusic.getAlbumArtUri(model.get_artistId())),
                viewHolder.mArtist_image, options);

        viewHolder.mImg_btn_option.setOnClickListener(this);


        viewHolder.mCard_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendAlbumId = new Intent(mContext, ArtistSongs.class);
                sendAlbumId.putExtra("ArtistID", id);
                mContext.startActivity(sendAlbumId);
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_option) {
            showPopupMenu(v);
        } else {
        }
    }

    @Override
    public int getItemCount() {
        return mArtistList.size();
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new RecyclerArtistAdapter.MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

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
