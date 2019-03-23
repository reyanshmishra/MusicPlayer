package app.deepakvishwakarma.com.musicplayer.Model;

public class AlbumSong {
    Long _ID;
    String ARTIST;
    String TITLE;
    String ALBUM;
    Long ALBUM_ID;
    Long ARTIST_ID;
    int TRACK;
    String DATA;
    Long DURATION;


    public AlbumSong(Long _ID, String TITLE, String ALBUM, Long ALBUM_ID, String ARTIST, Long ARTIST_ID, String DATA, int TRACK, Long DURATION) {
        this._ID = _ID;
        this.TITLE = TITLE;
        this.ALBUM = ALBUM;
        this.ALBUM_ID = ALBUM_ID;
        this.ARTIST = ARTIST;
        this.ARTIST_ID = ARTIST_ID;
        this.DATA = DATA;
        this.TRACK = TRACK;
        this.DURATION = DURATION;
    }

    public AlbumSong(Long _ID) {
        this._ID = _ID;
    }

    public Long get_ID() {
        return _ID;
    }

    public void set_ID(Long _ID) {
        this._ID = _ID;
    }

    public String getARTIST() {
        return ARTIST;
    }

    public void setARTIST(String ARTIST) {
        this.ARTIST = ARTIST;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getALBUM() {
        return ALBUM;
    }

    public void setALBUM(String ALBUM) {
        this.ALBUM = ALBUM;
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
