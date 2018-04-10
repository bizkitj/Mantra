package com.example.jeason.swipe.Adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jeason.swipe.Fragment.ArticleListFragment;
import com.example.jeason.swipe.Fragment.MantraListFragment;
import com.example.jeason.swipe.Fragment.VideoListFragment;
import com.example.jeason.swipe.R;


/**
 * Created by Jeason on 2018/3/6.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private static final int NUMBER_TABS = 3;
    private final FragmentManager fragmentManager;
    private Context mContext;
    private String[] mantraList;
    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragmentManager = fm;
        mContext = context;
        mantraList = context.getResources().getStringArray(R.array.MantraTitleList);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch (position) {
            case 0:
                return MantraListFragment.newInstance(mantraList);
            case 1:
                return VideoListFragment.newInstance(mantraList);
            case 2:
                return ArticleListFragment.newInstance(mantraList);
            default:
                return null;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.tab_zero);
            case 1:
                return mContext.getString(R.string.tab_one);
            case 2:
                return mContext.getString(R.string.tab_two);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show NUMBER_TABS total pages.
        return NUMBER_TABS;
    }


}
