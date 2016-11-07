package com.codepath.apps.critter_redux.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.critter_redux.R;
import com.codepath.apps.critter_redux.fragments.TweetFragment;
import com.codepath.apps.critter_redux.models.Tweet;
import com.codepath.apps.critter_redux.util.Utilities;

import org.parceler.Parcels;

public class TweetActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        Utilities.GoToPreviousScreenWithToolbar(this, Utilities.TOOLBAR_ID);

        //retrieve tweet from intent
        Tweet currentTweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        if (savedInstanceState == null) {
            TweetFragment tweetFragment = TweetFragment.newInstance(currentTweet);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_tweet, tweetFragment);//insert dynamically
            ft.commit();
        }
    }





}
