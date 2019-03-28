package app.deepakvishwakarma.com.musicplayer.Model;

public class Song implements Cloneable {
    public Long _ID;
    public String mArtist;
    public String mTitle;
    public String mAlbum;
    public Long ALBUM_ID;
    public Long ARTIST_ID;
    public int TRACK;
    public String DATA;
    public Long DURATION;

    public Song(Long _ID, String title, String ALBUM, Long ALBUM_ID, String ARTIST, Long ARTIST_ID, String DATA, int TRACK, Long DURATION) {
        this._ID = _ID;
        this.mTitle = title;
        this.mAlbum = ALBUM;
        this.ALBUM_ID = ALBUM_ID;
        this.mArtist = ARTIST;
        this.ARTIST_ID = ARTIST_ID;
        this.DATA = DATA;
        this.TRACK = TRACK;
        this.DURATION = DURATION;
    }


    public Long get_ID() {
        return _ID;
    }

    public void setID(Long _ID) {
        this._ID = _ID;
    }


    public void setARTIST(String ARTIST) {
        this.mArtist = ARTIST;
    }


    public void setTITLE(String TITLE) {
        this.mTitle = TITLE;
    }

    public String getALBUM() {
        return mAlbum;
    }

    public void setALBUM(String ALBUM) {
        this.mAlbum = ALBUM;
    }

    public Long getALBUM_ID() {
        return ALBUM_ID;
    }

    public void setALBUM_ID(Long ALBUM_ID) {
        this.ALBUM_ID = ALBUM_ID;
    }

    public Long getARTIST_ID() {
        return ARTIST_ID;
    }

    public void setARTIST_ID(Long ARTIST_ID) {
        this.ARTIST_ID = ARTIST_ID;
    }

    public int getTRACK() {
        return TRACK;
    }

    public void setTRACK(int TRACK) {
        this.TRACK = TRACK;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public Long getDURATION() {
        return DURATION;
    }

    public void setDURATION(Long DURATION) {
        this.DURATION = DURATION;
    }

}
