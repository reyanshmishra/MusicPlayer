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
import app.deepakvishwakarma.com.musicplayer.Model.AlbumSong;
import app.deepakvishwakarma.com.musicplayer.R;
import app.deepakvishwakarma.com.musicplayer.Utility.CentraliseMusic;

public class RecyclerAlbumSongAdapter extends RecyclerView.Adapter<RecyclerAlbumSongAdapter.ViewHolder> {
    private ArrayList<AlbumSong> AlbumSongList;
    private Context mContext;


    public RecyclerAlbumSongAdapter(Context mContext, ArrayList<AlbumSong> AlbumSongList) {
        this.mContext = mContext;
        this.AlbumSongList = AlbumSongList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView1, mTextView2, mTextView3;
        ImageButton three_dot_album_song;


        //constructor of viewholder class
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            three_dot_album_song = (ImageButton) itemView.findViewById(R.id.three_dot_album_song);
            mTextView1 = (TextView) itemView.findViewById(R.id.textview1);
            mTextView2 = (TextView) itemView.findViewById(R.id.textview2);
            mTextView3 = (TextView) itemView.findViewById(R.id.textview3);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_album_song, viewGroup, false);
        ViewHolder viewholder = new ViewHolder(view);

        return viewholder;
    }

  /*  public void update(ArrayList<AlbumSong> data) {
        AlbumSongList.addAll(data);
        notifyDataSetChanged();
    }  */

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        AlbumSong model = AlbumSongList.get(i);
        viewHolder.mTextView1.setText(model.getTITLE());
        viewHolder.mTextView2.setText(model.getARTIST());
        viewHolder.mTextView3.setText(CentraliseMusic.makeShortTimeString(mContext,(model.getDURATION()/1000)));

        viewHolder.three_dot_album_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(viewHolder.three_dot_album_song);
            }
        });
    }

    @Override
    public int getItemCount() {
        return AlbumSongList.size();
    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new RecyclerAlbumSongAdapter.MyMenuItemClickListener());
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
