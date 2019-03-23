package app.deepakvishwakarma.com.musicplayer;


import android.view.View;

public interface OnAdapterItemClicked {
    void OnPopUpMenuClicked(View view, int position);

    void OnShuffledClicked();
}