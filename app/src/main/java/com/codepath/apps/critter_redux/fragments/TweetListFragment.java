package com.codepath.apps.critter_redux.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.critter_redux.R;
import com.codepath.apps.critter_redux.TwitterApplication;
import com.codepath.apps.critter_redux.TwitterClient;
import com.codepath.apps.critter_redux.activities.TweetActivity;
import com.codepath.apps.critter_redux.adapters.TweetsAdapter;
import com.codepath.apps.critter_redux.listeners.EndlessRecyclerViewScrollListener;
import com.codepath.apps.critter_redux.listeners.OnItemClickListener;
import com.codepath.apps.critter_redux.models.Tweet;
import com.codepath.apps.critter_redux.util.DummyData;

import org.json.JSONArray;
import org.parceler.Parcels;

import java.util.ArrayList;

public abstract class TweetListFragment extends Fragment {

    protected TwitterClient twitterClient;

    protected long index = -1L;

    protected ArrayList<Tweet> tweets;
    protected TweetsAdapter tweetsAdapter;

    protected LinearLayoutManager linearLayoutManager;

    protected EndlessRecyclerViewScrollListener scrollListener;

    protected RecyclerView rvTweets;

    protected SwipeRefreshLayout swipeContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timeline, parent, false);

        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweets);
        rvTweets.setAdapter(tweetsAdapter);

        // Set layout manager to position the items
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTweets.setLayoutManager(linearLayoutManager);

        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        twitterClient = TwitterApplication.getRestClient();//get singleton client

        tweets = new ArrayList<>();
        tweetsAdapter = new TweetsAdapter(getActivity(), tweets);

    }


    @Override
    public void onStart() {//called once the fragment gets visible
        super.onStart();

        enableInfiniteScroll();

        setRefreshOnSwipe();

        enableClickableTweets();
    }



    public void refreshTimelineAndScrollUp(Tweet newTweet) {
        tweets.add(0, newTweet);

        tweetsAdapter.notifyItemInserted(0);

        Toast.makeText(getContext(), "Twitter was added", Toast.LENGTH_SHORT).show();

        //make sure we take new tweet is displayed on timeline:
        rvTweets.scrollToPosition(0);
    }


    protected void updateIndex() {
        //calculate new value for max_id for the API call: get the latest tweet and subtract 1
        Tweet endTweet = tweets.get(tweets.size() - 1);
        long endTweetId = endTweet.getTweetID();
        if (endTweetId != 0L) {//make the API call only if it's possible to decrement the ID
            index = --endTweetId;
        }
    }





    protected void getLocalTweets() {
        JSONArray jsonArray = DummyData.getDummyTimeline(getContext());
        ArrayList<Tweet> tweetList = Tweet.fromJSONArray(jsonArray);
        int currentSize = tweets.size();
        tweets.addAll(tweetList);
        tweetsAdapter.notifyItemRangeInserted(currentSize, tweetList.size());
    }




    protected void enableClickableTweets() {
        tweetsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                //create intent here to view
                Intent viewTweetIntent = new Intent(getActivity(), TweetActivity.class);

                //retrieve tweet that was clicked
                Tweet tweet = tweets.get(position);

                //set it as extra
                viewTweetIntent.putExtra("tweet", Parcels.wrap(tweet));

                //launch intent
                startActivity(viewTweetIntent);
            }
        });
    }


//    protected MenuItem miActionProgressItem;
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_timeline, menu);
//        miActionProgressItem = menu.findItem(R.id.mi_action_progress);
//    }
//    public void showProgressBar() {
//        if (miActionProgressItem != null)
//            miActionProgressItem.setVisible(true);
//    }
//
//    public void hideProgressBar() {
//        if (miActionProgressItem != null)
//            miActionProgressItem.setVisible(false);
//    }





//    Menu menu;

//    //Initialize the contents of the Activity's standard options menu.
//    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
////////        menuInflater.inflate(R.menu.menu_timeline, menu);
////////        //super.onCreateOptionsMenu(menu, menuInflater);
////////        getActivity().onCreateOptionsMenu(menu);
////////        MenuItem pinMenuItem = menu.findItem(R.id.mi_action_progress);
//////        menuInflater.inflate(R.menu.menu_timeline, menu);
//////        //getActivity().onCreateOptionsMenu(menu);
//////        //getActivity().getMenuInflater().inflate(R.menu.menu_timeline, menu);
//////        MenuItem pinMenuItem = menu.findItem(R.id.mi_action_progress);
//////        pinMenuItem.setVisible(true);
////        getActivity().onCreateOptionsMenu(menu);
////        menu.clear();
////        menuInflater.inflate(R.menu.menu_timeline, menu);
////        MenuItem pinMenuItem = menu.findItem(R.id.mi_action_progress);
////        pinMenuItem.setVisible(true);
//        this.menu = menu;
//    }


//    @Override
//    //prepare the items to be displayed
//    public void onPrepareOptionsMenu(Menu menu) {
////        getActivity().onPrepareOptionsMenu(menu);
////        MenuItem pinMenuItem = menu.findItem(R.id.mi_action_progress);
////        pinMenuItem.setVisible(true);
////        miActionProgressItem = menu.findItem(R.id.miActionProgress);
////
////        super.onPrepareOptionsMenu(menu);
//        this.menu = menu;
//    }



//    private void showProgressBar() {
//        MenuItem pinMenuItem = menu.findItem(R.id.mi_action_progress);
//        pinMenuItem.setVisible(true);
//    }






    // Abstract methods to be overridden depending on which tab is displaying and which user:
    //todo: or instead maybe create switch() depending which instance the fragment is of
    protected abstract void enableInfiniteScroll();


    protected abstract void setRefreshOnSwipe();


    protected abstract void populateTimeline(long maxId, String screenName);
}
