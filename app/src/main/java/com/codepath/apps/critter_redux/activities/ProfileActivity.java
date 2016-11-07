package com.codepath.apps.critter_redux.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.critter_redux.R;
import com.codepath.apps.critter_redux.fragments.UserHeaderFragment;
import com.codepath.apps.critter_redux.fragments.UserTimelineFragment;
import com.codepath.apps.critter_redux.models.User;
import com.codepath.apps.critter_redux.util.Utilities;

import org.parceler.Parcels;

public class ProfileActivity extends AppCompatActivity {

    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Utilities.GoToPreviousScreenWithToolbar(this, Utilities.TOOLBAR_ID);

        //retrieve the screen name from the intent:
        //String screenName = getIntent().getStringExtra("user");//screen_name

        currentUser = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        if (savedInstanceState == null) {

            //todo: make userHeaderFragment and userTimelineFragment fragments share the user?

            //UserHeaderFragment userHeaderFragment = UserHeaderFragment.newInstance(screenName);
            UserHeaderFragment userHeaderFragment = UserHeaderFragment.newInstance(currentUser);

            //UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(currentUser);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_user_header, userHeaderFragment);//insert dynamically
            //this means we are trying to display our own data
            ft.replace(R.id.fl_user_timeline, userTimelineFragment);
            //otherwise we display the other user's profile ONLY
            ft.commit();
        }
    }

}
