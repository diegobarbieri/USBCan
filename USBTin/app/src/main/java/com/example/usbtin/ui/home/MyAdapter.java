package com.example.usbtin.ui.home;

import android.content.Context;

import com.example.usbtin.ui.home.InvioTab;
import com.example.usbtin.ui.home.RiceviTab;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentManager;

class MyAdapter extends FragmentPagerAdapter {
        Context context;
        int totalTabs;
        public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
            super(fm);
            context = c;
            this.totalTabs = totalTabs;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new InvioTab();
                case 1:
                    return new RiceviTab();
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return totalTabs;
        }
}
