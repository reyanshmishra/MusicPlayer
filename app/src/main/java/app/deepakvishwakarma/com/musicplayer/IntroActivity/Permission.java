package app.deepakvishwakarma.com.musicplayer.IntroActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
    ArrayList<Song> nameList;
    private Cursor cursor;
    Common mApp;
    int progress = 0;
    OnProgressUpdate onProgressUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission);
        nameList = new ArrayList<>();
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
        if (android.os.Build.VERSION.SDK_INT >= 18) {
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

            cursor = Common.getInstance().getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    null,
                    null);

            if (cursor != null && cursor.moveToFirst()) {
                if (onProgressUpdate != null)
                    onProgressUpdate.maxProgress(cursor.getCount());
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
                    if (onProgressUpdate != null)
                        onProgressUpdate.onProgressed(progress++);
                    nameList.add(song);
                } while (cursor.moveToNext());
            }
            if (cursor != null) {
                cursor.close();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            mProgressBarHolder.setVisibility(View.INVISIBLE);
            Intent intpermission = new Intent(Permission.this, MainActivity.class);
            startActivity(intpermission);
            finish();
        }
    }
}




