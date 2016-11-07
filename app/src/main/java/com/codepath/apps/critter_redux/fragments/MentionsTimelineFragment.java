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
import com.codepath.apps.critter_redux.util.Utilities;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MentionsTimelineFragment extends TweetListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //first call, max_id is -1 so it won't be included as parameter in the API call
        populateTimeline(index, null);//null means mentions of logged user
        //getLocalTweets();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        return v;
    }


    //send API request to get the timeline JSON
    //and fill the recyclerview with the data retrieved by creating tweet objects
    public void populateTimeline(long maxId, String screenName) {

        //check connectivity:
        if (Utilities.isNetworkAvailable(getContext()) && Utilities.isOnline()) {

            //((TimelineActivity)getActivity()).showProgressBar();

            twitterClient.getMentionsTimeline(new JsonHttpResponseHandler() {
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

                    //((TimelineActivity)getActivity()).hideProgressBar();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Toast.makeText(getContext(), "Error in request", Toast.LENGTH_SHORT).show();
                }


                @Override
                public void onUserException(Throwable error) {
                    Toast.makeText(getContext(), R.string.on_user_exception, Toast.LENGTH_SHORT).show();
                }
            }, maxId);
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
                populateTimeline(index, null);
                //getLocalTweets();

                swipeContainer.setRefreshing(false);
            }
        });
    }


    protected void enableInfiniteScroll() {
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(long max_id, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list of tweets
                populateTimeline(index, null);
                //getLocalTweets();
            }
        };

        // Add the scroll listener to RecyclerView
        rvTweets.addOnScrollListener(scrollListener);
    }


}
