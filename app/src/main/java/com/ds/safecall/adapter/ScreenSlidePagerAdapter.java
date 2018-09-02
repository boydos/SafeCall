package com.ds.safecall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> datas;

        public ScreenSlidePagerAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.datas = data;
        }

        public void update(List<Fragment> data) {
            this.datas = data;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            if(datas == null || datas.size() == 0 || position >= datas.size()) {
                return null;
            }
            return datas.get(position);
        }

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.size();
        }
    }