package com.codepath.apps.critter_redux.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.critter_redux.R;
import com.codepath.apps.critter_redux.models.Tweet;
import com.codepath.apps.critter_redux.util.Utilities;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class UserTimelineFragment extends TweetListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //first call, max_id is -1 so it won't be included as parameter in the API call
        //populateTimeline(index);
        getLocalTweets();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        return v;
    }


    // Creates a new fragment
    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment fragmentInstance = new UserTimelineFragment();

        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        fragmentInstance.setArguments(args);

        return fragmentInstance;
    }


    //send API request to get the timeline JSON
    //and fill the recyclerview with the data retrieved by creating tweet objects
    public void populateTimeline(long maxId) {

        //check connectivity:
        if (Utilities.isNetworkAvailable(getContext()) && Utilities.isOnline()) {

            String screenName = getArguments().getString("screen_name");

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


}
