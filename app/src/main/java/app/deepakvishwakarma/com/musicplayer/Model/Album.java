package app.deepakvishwakarma.com.musicplayer.Model;

public class Album {

    public long _Id;
    public String _albumName;
    public String _albumArt;
    public String _artistName;
    public int _noOFsongs;





    public Album(long _Id, String _albumName, String _albumArt, String _artistName, int _noOFsongs) {
        this._Id = _Id;
        this._albumName = _albumName;
        this._artistName = _artistName;
        this._albumArt = _albumArt;
        this._noOFsongs = _noOFsongs;
    }

    public long get_Id() {
        return _Id;
    }

    public void set_Id(long _Id) {
        this._Id = _Id;
    }

    public String get_albumName() {
        return _albumName;
    }

    public void set_albumName(String _albumName) {
        this._albumName = _albumName;
    }

    public String get_albumArt() {
        return _albumArt;
    }

    public void set_albumArt(String _albumArt) {
        this._albumArt = _albumArt;
    }

    public String get_artistName() {
        return _artistName;
    }

    public void set_artistName(String _artistName) {
        this._artistName = _artistName;
    }

    public int get_noOFsongs() {
        return _noOFsongs;
    }

    public void set_noOFsongs(int _noOFsongs) {
        this._noOFsongs = _noOFsongs;
    }

}


