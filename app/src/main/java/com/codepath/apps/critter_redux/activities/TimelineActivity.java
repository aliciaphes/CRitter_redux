package com.codepath.apps.critter_redux.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.critter_redux.R;
import com.codepath.apps.critter_redux.TwitterApplication;
import com.codepath.apps.critter_redux.TwitterClient;
import com.codepath.apps.critter_redux.adapters.SmartFragmentStatePagerAdapter;
import com.codepath.apps.critter_redux.adapters.TweetsPagerAdapter;
import com.codepath.apps.critter_redux.fragments.ComposeFragment;
import com.codepath.apps.critter_redux.fragments.HomeTimelineFragment;
import com.codepath.apps.critter_redux.fragments.TweetListFragment;
import com.codepath.apps.critter_redux.listeners.PostTwitterListener;
import com.codepath.apps.critter_redux.models.Tweet;
import com.codepath.apps.critter_redux.util.Utilities;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


//this activity displays a timeline
public class TimelineActivity extends AppCompatActivity {

    private ViewPager vpPager;

    private SmartFragmentStatePagerAdapter adapterViewPager;

    private TwitterClient twitterClient;

    private ComposeFragment composeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        twitterClient = TwitterApplication.getRestClient();//get singleton client

        setupPagedFragments();

    }

    private void setupPagedFragments() {
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabstrip);
        tabStrip.setViewPager(vpPager);
    }


    //launch the profile view
    public void onProfileView(MenuItem mi) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }


    //this method is called when the floating button is pressed
    public void compose(View v) {
        showComposeDialog();
        composeFragment.setCustomObjectListener(new PostTwitterListener() {
            @Override
            public void onPostTwitter(String tweetBody) {
                composeFragment.dismiss();

                postTweet(tweetBody);
                //createDummyTweet();
            }
        });


    }

    //dummy method to avoid posting 'test' tweets in my timeline, I have a reputation!
//    public void createDummyTweet(){
//        try {
//            //create dummy tweet
//            Tweet newTweet = Tweet.fromJSON(new JSONObject(DummyData.DUMMY_TWEET));
//            addAndDisplay(newTweet);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }



    private void showComposeDialog() {
        composeFragment = ComposeFragment.newInstance("Compose tweet");
        composeFragment.show(getSupportFragmentManager(), "fragment_add_tweet");
    }


    //todo: improve API calls by adding since_id (?)


    private void postTweet(String tweet) {
        if (Utilities.isNetworkAvailable(this) && Utilities.isOnline()) {
            twitterClient.postTweet(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    //get new tweet that was generated
                    Tweet newTweet = Tweet.fromJSON(response);
                    //newTweet = Tweet.fromJSON(new JSONObject(Utilities.dummyTweet));

                    addAndDisplay(newTweet);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(getBaseContext(), "Error in request", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onUserException(Throwable error) {
                    Toast.makeText(getBaseContext(), R.string.on_user_exception, Toast.LENGTH_SHORT).show();
                }

            }, tweet);
        } else {
            Toast.makeText(getBaseContext(), R.string.device_not_connected, Toast.LENGTH_SHORT).show();
        }
    }


    //use SmartFragmentStatePagerAdapter to retrieve which fragment is displaying
    //if it's HomeTimeline, we visibly update the list with the new tweet on top, like a cherry
    protected void addAndDisplay(Tweet newTweet) {
        //get current fragment that is displayed:
        adapterViewPager = (SmartFragmentStatePagerAdapter) vpPager.getAdapter();

        TweetListFragment f = (TweetListFragment) adapterViewPager.getRegisteredFragment(vpPager.getCurrentItem());

        //only if HomeTimeLine is displaying, we update it with the new tweet
        if (f instanceof HomeTimelineFragment) {
            f.refreshTimelineAndScrollUp(newTweet);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

}
