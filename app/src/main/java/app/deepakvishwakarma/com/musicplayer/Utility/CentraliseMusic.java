package app.deepakvishwakarma.com.musicplayer.Utility;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Model.Album;
import app.deepakvishwakarma.com.musicplayer.Model.Artist;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.R;

public class CentraliseMusic {

    public static ArrayList getAlbums() {

        ArrayList<Album> albumList = new ArrayList<>();

        String[] columns = {
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
        };

        Cursor cursor = Common.getInstance().getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                columns,
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Album album = new Album(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                // OR
                Album album = new Album(
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums._ID)),
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)),
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)),
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)),
                        cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS)));

                albumList.add(album);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return albumList;
    }

    public static ArrayList getSongs(String from, int Id) {
        String selection = null;
        String sortOrder = null;
        if (from.equals("Album")) {
            if (Id > 0) {
                selection = "is_music != 0 and album_id = " + Id;
                sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC";
            }
        } else if (from.equals("Artist")) {
            if (Id > 0) {
                selection = "is_music != 0 and artist_id = " + Id;
                sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC";
            }
        } else {
            selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        }
        ArrayList<Song> songList = new ArrayList<>();
        Cursor cursor;
        // String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
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
        //uri,projection,selection,selection args and sord order
        cursor = Common.getInstance().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                sortOrder);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Song song = new Song(
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)),
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)),
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)),
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)),
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
                        cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.TRACK)),
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                );
                songList.add(song);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return songList;
    }

    public static ArrayList getArtists() {
        ArrayList<Artist> artistList = new ArrayList<>();
        Cursor artistCursor;

        String[] mProjection =
                {
                        MediaStore.Audio.Artists._ID,
                        MediaStore.Audio.Artists.ARTIST,
                        MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
                        MediaStore.Audio.Artists.NUMBER_OF_ALBUMS
                };

        artistCursor = Common.getInstance().getContentResolver().query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                mProjection,
                null,
                null,
                MediaStore.Audio.Artists.ARTIST + " ASC");

        if (artistCursor != null && artistCursor.moveToFirst()) {
            do {
                Artist artist = new Artist(
                        artistCursor.getLong(artistCursor.getColumnIndex(MediaStore.Audio.Artists._ID)),
                        artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST)),
                        artistCursor.getInt(artistCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)),
                        artistCursor.getInt(artistCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS))
                );
                artistList.add(artist);
            } while (artistCursor.moveToNext());
            if (artistCursor != null) {
                artistCursor.close();
            }
        }
        return artistList;
    }

    public static final String makeShortTimeString(final Context context, long secs) {
        long hours, mins;

        hours = secs / 3600;
        secs %= 3600;
        mins = secs / 60;
        secs %= 60;

        final String durationFormat = context.getResources().getString(
                hours == 0 ? R.string.durationformatshort : R.string.durationformatlong);
        return String.format(durationFormat, hours, mins, secs);

    }

    public static Uri getAlbumArtUri(long paramInt) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), paramInt);
    }

}
