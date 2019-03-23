package app.deepakvishwakarma.com.musicplayer.Model;

public class Artist {
    public long _artistId;
    public String _artistName;
    public int _noOfTracksByArtist;
    public int _noOfAlbumsByArtist;

    public Artist(long _artistId, String _artistName, int _noOfTracksByArtist, int _noOfAlbumsByArtist) {
        this._artistId = _artistId;
        this._artistName = _artistName;
        this._noOfTracksByArtist = _noOfTracksByArtist;
        this._noOfAlbumsByArtist = _noOfAlbumsByArtist;
    }


    public long get_artistId() {
        return _artistId;
    }

    public void set_artistId(long _artistId) {
        this._artistId = _artistId;
    }

    public String get_artistName() {
        return _artistName;
    }

    public void set_artistName(String _artistName) {
        this._artistName = _artistName;
    }

    public int get_noOfTracksByArtist() {
        return _noOfTracksByArtist;
    }

    public void set_noOfTracksByArtist(int _noOfTracksByArtist) {
        this._noOfTracksByArtist = _noOfTracksByArtist;
    }

    public int get_noOfAlbumsByArtist() {
        return _noOfAlbumsByArtist;
    }

    public void set_noOfAlbumsByArtist(int _noOfAlbumsByArtist) {
        this._noOfAlbumsByArtist = _noOfAlbumsByArtist;
    }

}

