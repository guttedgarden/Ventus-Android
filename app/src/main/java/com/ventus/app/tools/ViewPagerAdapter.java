package com.ventus.app.tools;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ventus.app.fragments.DHT11Fragment;
import com.ventus.app.fragments.MQ135Fragment;
import com.ventus.app.fragments.MQ4Fragment;
import com.ventus.app.fragments.MQ7Fragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MQ4Fragment();
            case 1:
                return new MQ7Fragment();
            case 2:
                return new MQ135Fragment();
            case 3:
                return new DHT11Fragment();
                default:
                    return new MQ4Fragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
