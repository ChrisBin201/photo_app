package com.example.photo_app.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.photo_app.fragment.FragmentHome;
import com.example.photo_app.fragment.FragmentProfile;
import com.example.photo_app.fragment.FragmentSearch;
import com.example.photo_app.fragment.FragmentUpload;
import com.example.photo_app.fragment.ratingComment.FragmentViewComments;
import com.example.photo_app.fragment.ratingComment.FragmentViewRatings;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentHome();
//                return new FragmentViewComments();
            case 1:
                return new FragmentSearch();
//                return new FragmentViewRatings();
            case 2:
                return new FragmentUpload();
            case 3:
                return new FragmentProfile();

        }
        return new FragmentHome();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
