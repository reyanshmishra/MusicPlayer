package app.deepakvishwakarma.com.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        EditText edit = (EditText) findViewById(R.id.edit);
      //  Intent getToken = getIntent();
      //  String token = getToken.getExtras().getString("FirebaseID", "");
      //  edit.setText(token);

    }
}
