package com.codepath.apps.critter_redux.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.critter_redux.R;
import com.codepath.apps.critter_redux.adapters.TweetsPagerAdapter;


public class TimelineActivity extends AppCompatActivity {

    //private FragmentManager fm;
    //private TweetListFragment timelineFragment;

    //private ComposeFragment composeFragment;//todo: to be moved to another fragment I suppose


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupPagedFragments();

        //setupComposeBehavior();//todo: to be moved I believe


//        fm = getSupportFragmentManager();

//        if (savedInstanceState == null) {
//            timelineFragment = (TweetListFragment) fm.findFragmentById(R.id.fragment_timeline);
//        }

    }

    private void setupPagedFragments() {
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabstrip);
        tabStrip.setViewPager(vpPager);
    }


    //launch the profile view
    public void onProfileView(MenuItem mi) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }



/*
    private void setupComposeBehavior() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_compose);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showComposeDialog();
                composeFragment.setCustomObjectListener(new PostTwitterListener() {
                    @Override
                    public void onPostTwitter(String tweetBody) {
                        composeFragment.dismiss();

                        *//** BEGIN IMPORTANT BLOCK *//*
                        postTweet(tweetBody);
                        *//** This block is to be commented/deleted, only used to avoid tweeting every time I test.
     ALSO DO NOT FORGET TO UNCOMMENT THE ABOVE CALL TO postTweet!!
     *//*
//                        try {
//                            //create dummy tweet
//                            Tweet newTweet = Tweet.fromJSON(new JSONObject(DummyData.DUMMY_TWEET));
//                            refreshTimelineAndScrollUp(newTweet);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                        *//** END IMPORTANT BLOCK *//*
                    }
                });
            }
        });
    }
    */



/*
    private void showComposeDialog() {
        composeFragment = ComposeFragment.newInstance("Compose tweet");
        composeFragment.show(fm, "fragment_add_tweet");
    }
    */


    //todo: improve API calls by adding since_id (?)

    //todo: change bar title


//todo: to be moved to a different fragment I believe
/*
    private void postTweet(String tweet) {
        if (Utilities.isNetworkAvailable(this) && Utilities.isOnline()) {
            twitterClient.postTweet(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //super.onSuccess(statusCode, headers, response);

                    //get new tweet that was generated
                    Tweet newTweet = Tweet.fromJSON(response);
                    //newTweet = Tweet.fromJSON(new JSONObject(Utilities.dummyTweet));

                    refreshTimelineAndScrollUp(newTweet);
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
*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
