package com.codepath.apps.critter_redux.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.critter_redux.fragments.HomeTimelineFragment;
import com.codepath.apps.critter_redux.fragments.MentionsTimelineFragment;



//return the order of the fragments in the viewpager
public class TweetsPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = {"Home", "Mentions"};


    //how the adapter gets the manager to insert/remove fragments from activity
    public TweetsPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    //control order and creation of fragments within the pager
    @Override
    public Fragment getItem(int position) {
        //once created, they will be automatically cached
        if (position == 0) {
            return new HomeTimelineFragment();
        } else if (position == 1) {
            return new MentionsTimelineFragment();
        } else {
            return null;
        }
    }

    //return the tab title
    @Override
    public int getCount() {
        return tabTitles.length;
    }


    //how many fragments there are to swipe between
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
