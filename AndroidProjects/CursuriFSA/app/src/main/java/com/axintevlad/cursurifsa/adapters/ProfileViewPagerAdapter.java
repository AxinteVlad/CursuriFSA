package com.axintevlad.cursurifsa.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.axintevlad.cursurifsa.fragment.InformatiiFragment;
import com.axintevlad.cursurifsa.fragment.SubscriptiiFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad__000 on 09.04.2019.
 */
public class ProfileViewPagerAdapter extends FragmentPagerAdapter {
    public ProfileViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return new InformatiiFragment();
            case 1:
                return new SubscriptiiFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Informatii";
            case 1:
                return "Subscriptii";


            }

        return null;
    }
}
