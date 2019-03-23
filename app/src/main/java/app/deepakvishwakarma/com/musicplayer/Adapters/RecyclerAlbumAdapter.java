package app.deepakvishwakarma.com.musicplayer.Adapters;

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
import app.deepakvishwakarma.com.musicplayer.Model.Album;
import app.deepakvishwakarma.com.musicplayer.R;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;

public class RecyclerAlbumAdapter extends RecyclerView.Adapter<RecyclerAlbumAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<Album> albumList;
    private Context mContext;
    ImageLoader imageLoader = ImageLoader.getInstance();

    DisplayImageOptions options;

    public RecyclerAlbumAdapter(Context mContext, ArrayList<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView1, mTextView2;
        ImageView Album_image;
        ImageButton img_btn_option;


        //constructor of viewholder class
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Album_image = (ImageView) itemView.findViewById(R.id.album);
            mTextView1 = (TextView) itemView.findViewById(R.id.textview1);
            mTextView2 = (TextView) itemView.findViewById(R.id.textview2);
            img_btn_option = (ImageButton) itemView.findViewById(R.id.img_btn_option);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_album, viewGroup, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;

    }

  /*  public void update(ArrayList<Album> data) {
        albumList.addAll(data);
        notifyDataSetChanged();
    }  */

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Album model = albumList.get(i);
       // viewHolder.mTextView.setText(String.valueOf(model.get_Id()));
        String id = String.valueOf(model.get_Id());
        viewHolder.mTextView1.setText(model.get_albumName());
        viewHolder.mTextView2.setText(model.get_artistName());

        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.placeholder)
                .showImageOnFail(R.drawable.placeholder)
                .showImageOnLoading(R.drawable.placeholder).build();
        imageLoader.displayImage(String.valueOf(CentraliseMusic.getAlbumArtUri(model.get_Id())), viewHolder.Album_image, options);
        // three dot button onclicklistener
        viewHolder.img_btn_option.setOnClickListener(this);

      //  viewHolder.Album_image.setOnClickListener(this);
        viewHolder.Album_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendAlbumId = new Intent(mContext, AlbumSongs.class);
                sendAlbumId.putExtra("AlbumID",id);
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
        return albumList.size();
    }
}
