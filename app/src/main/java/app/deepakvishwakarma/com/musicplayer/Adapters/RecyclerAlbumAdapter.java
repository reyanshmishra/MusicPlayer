package app.deepakvishwakarma.com.musicplayer.Adapters;

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
import app.deepakvishwakarma.com.musicplayer.Fragments.AlbumSongs;
import app.deepakvishwakarma.com.musicplayer.Model.Album;
import app.deepakvishwakarma.com.musicplayer.R;
import app.deepakvishwakarma.com.musicplayer.Services.MusicService;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;

public class RecyclerAlbumAdapter extends RecyclerView.Adapter<RecyclerAlbumAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<Album> mAlbumList;
    private Context mContext;
    Common mApp;

    public RecyclerAlbumAdapter(Context context) {
        mContext = context;
        mApp = (Common) mContext.getApplicationContext();
        mAlbumList = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mAlbumName, mArtistName;
        ImageView mAlbum_image;
        CardView mCard_view;
        ImageButton mImg_btn_option;

        //constructor of viewholder class
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCard_view = itemView.findViewById(R.id.card_view_album);
            mAlbum_image = itemView.findViewById(R.id.album_img);
            mAlbumName = itemView.findViewById(R.id.album_name);
            mArtistName = itemView.findViewById(R.id.artist_name);
            mImg_btn_option = itemView.findViewById(R.id.img_btn_option);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mApp.getService() == null) {
                mContext.startService(new Intent(mContext, MusicService.class));
//                mApp.getService().playsong(mAlbumList.get(getAdapterPosition()));
            } else {
//                mApp.getService().playsong(mAlbumList.get(getAdapterPosition()));
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_album, viewGroup, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    public void update(ArrayList<Album> data) {
        mAlbumList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Album model = mAlbumList.get(i);
     //   String id = String.valueOf(model.get_Id());
        viewHolder.mAlbumName.setText(model.get_albumName());
        viewHolder.mArtistName.setText(model.get_artistName());
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .showImageForEmptyUri(R.drawable.placeholder)
                .showImageOnFail(R.drawable.placeholder)
                .build();
        ImageLoader.getInstance().displayImage(String.valueOf(CentraliseMusic.getAlbumArtUri(model.get_Id())), viewHolder.mAlbum_image,          options);
        viewHolder.mImg_btn_option.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_option) {
            showPopupMenu(v);
        }
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
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

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }
}
