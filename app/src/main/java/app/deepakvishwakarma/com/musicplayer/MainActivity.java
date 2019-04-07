package app.deepakvishwakarma.com.musicplayer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import app.deepakvishwakarma.com.musicplayer.Fragments.AlbumFragment;
import app.deepakvishwakarma.com.musicplayer.Fragments.ArtistFragment;
import app.deepakvishwakarma.com.musicplayer.Fragments.SongsFragment;
import app.deepakvishwakarma.com.musicplayer.IntroActivity.Permission;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewpager;
    TabLayout mTabLayout;
    Toolbar mToolbar;
    private TextView mTooltext;
    String mFirebaseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String build = pref.getString("build", "");
        if (build.equals("")) {
            Intent intent = new Intent(this, Permission.class);
            startActivity(intent);
            finish();
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.id_tabs);
        mViewpager = (ViewPager) findViewById(R.id.view_pager);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTooltext = (TextView) findViewById(R.id.tooltext);
        setupViewPager(mViewpager);
        mTabLayout.setupWithViewPager(mViewpager);
        // FirebaseID = FirebaseInstanceId.getInstance().getToken();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(MainActivity.this.getApplicationContext()));
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SongsFragment(), "TRACKS");
        adapter.addFragment(new ArtistFragment(), "ARTISTS");
        adapter.addFragment(new AlbumFragment(), "ALBUMS");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
           /*
           Intent search = new Intent(MainActivity.this, SearchActivity.class);
            search.putExtra("FirebaseID",mFirebaseID);
            startActivity(search);
            finish();
            */
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
