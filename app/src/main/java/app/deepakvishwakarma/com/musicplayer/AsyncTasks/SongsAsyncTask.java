package app.deepakvishwakarma.com.musicplayer.AsyncTasks;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.MainActivity;
import app.deepakvishwakarma.com.musicplayer.Model.Song;

public class SongsAsyncTask extends AsyncTask<Void, Void,  ArrayList<Song>> {
    private Cursor mCursor;

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected ArrayList<Song> doInBackground(Void... params) {
         ArrayList<Song> mSongList=new ArrayList<>();

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.TRACK,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        mCursor = Common.getInstance().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,
                null,
                null);

        if (mCursor != null && mCursor.moveToFirst()) {
            do {
                Song song = new Song(
                        mCursor.getLong(mCursor.getColumnIndex(MediaStore.Audio.Media._ID)),
                        mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                        mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)),
                        mCursor.getLong(mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)),
                        mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                        mCursor.getLong(mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)),
                        mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
                        mCursor.getInt(mCursor.getColumnIndex(MediaStore.Audio.Media.TRACK)),
                        mCursor.getLong(mCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                );
                mSongList.add(song);
            } while (mCursor.moveToNext());
        }
        if (mCursor != null) {
            mCursor.close();
        }

        return mSongList;
    }

    @Override
    protected void onPostExecute(ArrayList<Song> songs) {

    }
}
