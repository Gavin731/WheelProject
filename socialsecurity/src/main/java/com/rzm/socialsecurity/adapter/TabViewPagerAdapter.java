package com.rzm.socialsecurity.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rzm.socialsecurity.fragment.AFragment;
import com.rzm.socialsecurity.fragment.BFragment;


public class TabViewPagerAdapter extends FragmentPagerAdapter {

    private int size;

    public TabViewPagerAdapter(FragmentManager fm, int size) {
        super(fm);
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return AFragment.newInstance(position + "");
            case 1:
                return BFragment.newInstance(position + "");
            default:
                return AFragment.newInstance(position + "");
        }

    }

    @Override
    public int getCount() {
        return size;
    }
}
