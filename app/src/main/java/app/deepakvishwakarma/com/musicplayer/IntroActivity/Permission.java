package app.deepakvishwakarma.com.musicplayer.IntroActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import app.deepakvishwakarma.com.musicplayer.Common;
import app.deepakvishwakarma.com.musicplayer.Interface.OnProgressUpdate;
import app.deepakvishwakarma.com.musicplayer.MainActivity;
import app.deepakvishwakarma.com.musicplayer.Model.Song;
import app.deepakvishwakarma.com.musicplayer.R;

public class Permission extends AppCompatActivity {
    private ProgressBar mProgressBar;
    private RelativeLayout mProgressBarHolder;
    ArrayList<Song> mSongList;
    private Cursor mCursor;
    Common mApp;
    int mProgress = 0;
    OnProgressUpdate onProgressUpdate;
    String mBuild;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission);
        mSongList = new ArrayList<>();
        mProgressBar = findViewById(R.id.building_library_progress);
        mProgressBarHolder = findViewById(R.id.progress_elements_container);
        mApp = (Common) Common.getInstance().getApplicationContext();

        onProgressUpdate = new OnProgressUpdate() {
            @Override
            public void onProgressed(int progress) {
                mProgressBar.setProgress(progress);
            }

            @Override
            public void maxProgress(int max) {
                mProgressBar.setMax(max);
            }
        };
        checkPermissions();
    }

    private void fadeInFadeOut() {
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        mProgressBarHolder.startAnimation(fadeInAnimation);
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mProgressBarHolder.setVisibility(View.VISIBLE);
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void checkPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            Dexter.withActivity(this)
                    .withPermissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            // check if all permissions are granted
                            if (report.areAllPermissionsGranted()) {
                                Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_LONG).show();
                                fadeInFadeOut();
                            } else {
                                checkPermissions();
                            }
                            // check for permanent denial of any permission
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                // show alert dialog navigating to Settings
                                showSettingsDialog();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).
                    withErrorListener(new PermissionRequestErrorListener() {
                        @Override
                        public void onError(DexterError error) {
                            Toast.makeText(Permission.this, "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .onSameThread()
                    .check();
        }
    }

    private void showSettingsDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    @SuppressLint("StaticFieldLeak")
    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
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

            mCursor = Common.getInstance().getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    null,
                    null);

            if (mCursor != null && mCursor.moveToFirst()) {
                if (onProgressUpdate != null)
                    onProgressUpdate.maxProgress(mCursor.getCount());
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
                    if (onProgressUpdate != null)
                        onProgressUpdate.onProgressed(mProgress++);
                    mSongList.add(song);
                } while (mCursor.moveToNext());
                mBuild = "Buil Success";
            }
            if (mCursor != null) {
                mCursor.close();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            mProgressBarHolder.setVisibility(View.INVISIBLE);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("build", mBuild);
            editor.apply();
            Thread mWait = new Thread() {
                public void run() {
                    try {
                        // Thread will sleep for 0.2 seconds
                        sleep(200);
                        // After 2 seconds redirect to another intent
                        Intent intpermission = new Intent(Permission.this, MainActivity.class);
                        startActivity(intpermission);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            // start thread
            mWait.start();

        }
    }
}




