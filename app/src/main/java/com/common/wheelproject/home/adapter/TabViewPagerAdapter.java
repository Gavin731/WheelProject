package com.common.wheelproject.home.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.common.wheelproject.home.fragment.AFragment;

public class TabViewPagerAdapter extends FragmentPagerAdapter {

    private int size;

    public TabViewPagerAdapter(FragmentManager fm, int size) {
        super(fm);
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {
        return AFragment.newInstance(position + "");
    }

    @Override
    public int getCount() {
        return size;
    }
}
