package com.example.photo_app;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.photo_app.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavi;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavi = findViewById(R.id.bottomNavi);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavi.getMenu().findItem(R.id.mHome).setChecked(true);
                        break;
                    case 1:
                        bottomNavi.getMenu().findItem(R.id.mSearch).setChecked(true);
                        break;
                    case 2:
                        bottomNavi.getMenu().findItem(R.id.mUpload).setChecked(true);
                        break;
                    case 3:
                        bottomNavi.getMenu().findItem(R.id.mProfile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavi.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mHome:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.mSearch:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.mUpload:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.mProfile:
                    viewPager.setCurrentItem(3);
                    break;
            }
            return true;
        });
    }
}


