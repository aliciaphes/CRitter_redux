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


//this activity will display some details related to a user
public class ProfileActivity extends AppCompatActivity {

    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Utilities.GoToPreviousScreenWithToolbar(this, Utilities.TOOLBAR_ID);

        //retrieve the current user from the intent:
        currentUser = Parcels.unwrap(getIntent().getParcelableExtra("user"));

        if (savedInstanceState == null) {

            //todo: make userHeaderFragment and userTimelineFragment fragments share the user?

            UserHeaderFragment userHeaderFragment = UserHeaderFragment.newInstance(currentUser);

            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(currentUser);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_user_header, userHeaderFragment);//insert dynamically
            //if currentUser is null, it means we are trying to display our own data
            ft.replace(R.id.fl_user_timeline, userTimelineFragment);
            //otherwise we display the other user's profile ONLY
            ft.commit();
        }
    }



}
