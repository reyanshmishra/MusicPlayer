package app.deepakvishwakarma.com.musicplayer.IntroActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import app.deepakvishwakarma.com.musicplayer.MainActivity;
import app.deepakvishwakarma.com.musicplayer.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 2 seconds
                    sleep(2000);
                    // After 2 seconds redirect to another intent

                    Intent mIntent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(mIntent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        // start thread
        background.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
