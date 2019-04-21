package app.deepakvishwakarma.com.musicplayer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import app.deepakvishwakarma.com.musicplayer.Adapters.ViewPagerAdapter;
import app.deepakvishwakarma.com.musicplayer.Fragments.AlbumFragment;
import app.deepakvishwakarma.com.musicplayer.Fragments.ArtistFragment;
import app.deepakvishwakarma.com.musicplayer.Fragments.SongsFragment;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewpager;
    TabLayout mTabLayout;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.id_tabs);
        mViewpager = (ViewPager) findViewById(R.id.view_pager);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SongsFragment(), "TRACKS");
        adapter.addFragment(new ArtistFragment(), "ARTISTS");
        adapter.addFragment(new AlbumFragment(), "ALBUMS");
        mViewpager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewpager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
        } else if (id == R.id.item1) {
            Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item2) {
            Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item3) {
            Toast.makeText(this, "Item 3", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item4) {
            Toast.makeText(this, "Item 4", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item5) {
            Toast.makeText(this, "Item 5", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
