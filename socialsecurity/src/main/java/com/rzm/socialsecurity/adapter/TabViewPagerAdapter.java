package com.rzm.socialsecurity.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rzm.socialsecurity.fragment.HomeFragment;
import com.rzm.socialsecurity.fragment.BFragment;
import com.rzm.socialsecurity.fragment.PersonalCenterFragment;
import com.rzm.socialsecurity.fragment.PayRecordFragment;
import com.rzm.socialsecurity.fragment.ToolFragment;


public class TabViewPagerAdapter extends FragmentPagerAdapter {

    private int size;

    public TabViewPagerAdapter(FragmentManager fm, int size) {
        super(fm);
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance(position + "");
            case 1:
                return ToolFragment.newInstance(position + "");
            case 2:
                return PayRecordFragment.newInstance(position + "");
            case 3:
                return PersonalCenterFragment.newInstance(position + "");
            default:
                return BFragment.newInstance(position + "");
        }

    }

    @Override
    public int getCount() {
        return size;
    }
}
