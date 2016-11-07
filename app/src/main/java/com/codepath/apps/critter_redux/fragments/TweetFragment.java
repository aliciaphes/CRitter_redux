package com.codepath.apps.critter_redux.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.critter_redux.R;
import com.codepath.apps.critter_redux.models.Tweet;
import com.codepath.apps.critter_redux.util.Utilities;

import org.parceler.Parcels;

public class TweetFragment extends Fragment {

    private final int FONT_SIZE = 14;

    private ImageView ivProfileImage;
    private TextView tvUserName;
    private TextView tvHandle;
    private TextView tvTimestamp;
    private TextView tvBody;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_tweet, parent, false);
        return v;
    }


    // Create a new fragment
    public static TweetFragment newInstance(Tweet tweet) {
        TweetFragment fragmentInstance = new TweetFragment();

        Bundle args = new Bundle();
        args.putParcelable("tweet", Parcels.wrap(tweet));
        fragmentInstance.setArguments(args);

        return fragmentInstance;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //identify the fields to give them values later
        getReferences(view);

        Tweet tweet = Parcels.unwrap(getArguments().getParcelable("tweet"));
        //populate fragment from tweet
        if (tweet != null) {
            setUpValues(tweet);
        }

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle("Tweet by @"+ tweet.getUser().getScreenName());
    }


    private void getReferences(View v) {
        ivProfileImage = (ImageView) v.findViewById(R.id.iv_profile_image);
        tvUserName = (TextView) v.findViewById(R.id.tv_username);
        tvHandle = (TextView) v.findViewById(R.id.tv_handle);
        tvTimestamp = (TextView) v.findViewById(R.id.tv_timestamp);
        tvBody = (TextView) v.findViewById(R.id.tv_body);
    }



    private void setUpValues(Tweet currentTweet) {
        //clear image in case it had a previous value
        ivProfileImage.setImageResource(android.R.color.transparent);
        //then load image with Picasso //todo: not for now
//        Picasso.with(getContext()).load(currentTweet.getUser().getProfileURL())
//                .placeholder(R.drawable.placeholder)
//                .into(ivProfileImage);

        tvUserName.setText(currentTweet.getUser().getName());
        tvUserName.setTextSize(FONT_SIZE);

        tvHandle.setText("@" + currentTweet.getUser().getScreenName());
        tvHandle.setTextSize(FONT_SIZE);

        tvTimestamp.setText(Utilities.getRelativeTimeAgo(currentTweet.getCreatedAt()));
        tvTimestamp.setTextSize(FONT_SIZE);

        tvBody.setText(currentTweet.getBody());
        tvBody.setTextSize(FONT_SIZE);
    }


}