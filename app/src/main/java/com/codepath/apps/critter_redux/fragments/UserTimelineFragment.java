package com.codepath.apps.critter_redux.fragments;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.critter_redux.R;
import com.codepath.apps.critter_redux.listeners.EndlessRecyclerViewScrollListener;
import com.codepath.apps.critter_redux.models.Tweet;
import com.codepath.apps.critter_redux.models.User;
import com.codepath.apps.critter_redux.util.Utilities;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class UserTimelineFragment extends TweetListFragment {

    private User currentUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        currentUser = Parcels.unwrap(getArguments().getParcelable("user"));
        if (currentUser == null) {
            populateTimeline(index, null);
            //getLocalTweets();
        }
        else{
            populateTimeline(index, currentUser.getScreenName());
            //getLocalTweets();
        }
    }

    // Creates a new fragment
    //public static UserTimelineFragment newInstance(String screenName) {
    public static UserTimelineFragment newInstance(User currentUser) {
        UserTimelineFragment fragmentInstance = new UserTimelineFragment();

        Bundle args = new Bundle();
        //args.putString("screen_name", screenName);
        args.putParcelable("user", Parcels.wrap(currentUser));
        fragmentInstance.setArguments(args);

        return fragmentInstance;
    }


    //send API request to get the timeline JSON
    //and fill the recyclerview with the data retrieved by creating tweet objects
    public void populateTimeline(long maxId, String screenName) {

        //check connectivity:
        if (Utilities.isNetworkAvailable(getContext()) && Utilities.isOnline()) {

            //String screenName = getArguments().getString("screen_name");

            twitterClient.getUserTimeline(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    // deserialize the response and create models
                    ArrayList<Tweet> tweetList = Tweet.fromJSONArray(response);

                    //load data into the view:

                    //store reference to current size
                    int currentSize = tweets.size();

                    //add retrieved tweets to existing list
                    tweets.addAll(tweetList);

                    //visually refresh the list
                    tweetsAdapter.notifyItemRangeInserted(currentSize, tweetList.size());

                    if (!tweetList.isEmpty()) {
                        updateIndex();
                    }
                    swipeContainer.setRefreshing(false);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Toast.makeText(getContext(), "Error in request", Toast.LENGTH_SHORT).show();
                }


                @Override
                public void onUserException(Throwable error) {
                    Toast.makeText(getContext(), R.string.on_user_exception, Toast.LENGTH_SHORT).show();
                }
            }, maxId, screenName);
        } else {
            Toast.makeText(getContext(), R.string.device_not_connected, Toast.LENGTH_SHORT).show();
        }
    }



    protected void setRefreshOnSwipe() {

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                int size = tweets.size();
                tweets.clear();
                //tweetsAdapter.clear();
                //notify the changes
                tweetsAdapter.notifyItemRangeRemoved(0, size);

                //reset index and call get home timeline again
                index = -1L;

                if (currentUser == null) {
                    populateTimeline(index, null);
                    //getLocalTweets();
                }
                else{
                    populateTimeline(index, currentUser.getScreenName());
                    //getLocalTweets();
                }

                swipeContainer.setRefreshing(false);
            }
        });
    }




    protected void enableInfiniteScroll() {
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(long max_id, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list of tweets
                if (currentUser == null) {
                    populateTimeline(index, null);
                    //getLocalTweets();
                }
                else{
                    populateTimeline(index, currentUser.getScreenName());
                    //getLocalTweets();
                }
            }
        };

        // Add the scroll listener to RecyclerView
        rvTweets.addOnScrollListener(scrollListener);
    }



}
