package com.codepath.apps.critter_redux.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.codepath.apps.critter_redux.R;
import com.codepath.apps.critter_redux.fragments.UserHeaderFragment;
import com.codepath.apps.critter_redux.fragments.UserTimelineFragment;
import com.codepath.apps.critter_redux.util.Utilities;

public class ProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Utilities.GoToPreviousScreenWithToolbar(this);

        //retrieve the screen name from the intent:
        String screenName = getIntent().getStringExtra("screen_name");
        if (savedInstanceState == null) {
            UserHeaderFragment userHeaderFragment = UserHeaderFragment.newInstance(screenName);
            //todo: make these two fragments share the user?
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_user_header, userHeaderFragment);//insert dynamically
            ft.replace(R.id.fl_user_timeline, userTimelineFragment);
            ft.commit();
        }
    }

}
