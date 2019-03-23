package app.deepakvishwakarma.com.musicplayer.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

import app.deepakvishwakarma.com.musicplayer.Fragments.AlbumSongs;
import app.deepakvishwakarma.com.musicplayer.Fragments.ArtistSongs;
import app.deepakvishwakarma.com.musicplayer.Model.Artist;
import app.deepakvishwakarma.com.musicplayer.R;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;

public class RecyclerArtistAdapter extends RecyclerView.Adapter<RecyclerArtistAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<Artist> artistList;
    private Context mContext;
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    public RecyclerArtistAdapter(Context mContext, ArrayList<Artist> artistList) {
        this.mContext = mContext;
        this.artistList = artistList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  mTextView1, mTextView2, mTextView3 ;
        ImageView Artist_image;
        ImageButton img_btn_option;

        //constructor of viewholder class
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Artist_image = (ImageView) itemView.findViewById(R.id.artist);
            mTextView1 = (TextView) itemView.findViewById(R.id.textview1);
            mTextView2 = (TextView) itemView.findViewById(R.id.textview2);
            mTextView3 = (TextView) itemView.findViewById(R.id.textview3);
            img_btn_option = (ImageButton) itemView.findViewById(R.id.img_btn_option);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_artist, viewGroup, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

  /*  public void update(ArrayList<Artist> data) {
        artistList.addAll(data);
        notifyDataSetChanged();
    }
*/
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Artist model = artistList.get(i);
        String id = String.valueOf(model.get_artistId());

        viewHolder.mTextView1.setText(model.get_artistName());
        viewHolder.mTextView2.setText(String.valueOf(model.get_noOfTracksByArtist()) +" Songs | ");
        viewHolder.mTextView3.setText(String.valueOf(model.get_noOfAlbumsByArtist()) +" Albums");


         options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.placeholder)
                .showImageOnFail(R.drawable.placeholder)
                .showImageOnLoading(R.drawable.placeholder).build();
        imageLoader.displayImage(String.valueOf(CentraliseMusic.getAlbumArtUri(model.get_artistId())), viewHolder.Artist_image, options);

        viewHolder.img_btn_option.setOnClickListener(this);


        viewHolder.Artist_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendAlbumId = new Intent(mContext, ArtistSongs.class);
                sendAlbumId.putExtra("ArtistID",id);
                mContext.startActivity(sendAlbumId);
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.img_btn_option)
        {
            showPopupMenu(v);
        }
        else
        {
        }
    }

    @Override
    public int getItemCount() {
        return artistList.size();
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
