package com.codepath.apps.critter_redux.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.critter_redux.R;
import com.codepath.apps.critter_redux.TwitterApplication;
import com.codepath.apps.critter_redux.TwitterClient;
import com.codepath.apps.critter_redux.models.User;
import com.codepath.apps.critter_redux.util.DummyData;
import com.codepath.apps.critter_redux.util.Utilities;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class UserHeaderFragment extends Fragment {

    private TwitterClient twitterClient;
    private User user;

    private TextView tvName;
    private TextView tvTagline;
    private TextView tvFollowers;
    private TextView tvFollowing;
    private ImageView ivImage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_header, parent, false);
        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //identify the fields to give them values later
        getReferences(view);

        String screenName = getArguments().getString("screen_name");

        //populateUserHeader(screenName);
        populateDummyUserInfo();
    }



    // Create a new fragment
    public static UserHeaderFragment newInstance(String screenName) {
        UserHeaderFragment fragmentInstance = new UserHeaderFragment();

        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        fragmentInstance.setArguments(args);

        return fragmentInstance;
    }



    private void populateDummyUserInfo() {
        JSONObject jsonObject = DummyData.getDummyProfile(getContext());
        user = User.fromJSON(jsonObject);
        fillWithHeaderValues();
    }


    private void populateUserHeader(String screenName) {

        twitterClient = TwitterApplication.getRestClient();//get singleton client

        //check connectivity:
        if (Utilities.isNetworkAvailable(getContext()) && Utilities.isOnline()) {

            twitterClient.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //establish who the user is:
                    user = User.fromJSON(response);

                    //visibly show the user's info
                    fillWithHeaderValues();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Toast.makeText(getContext(), "Error in request", Toast.LENGTH_SHORT).show();
                }


                @Override
                public void onUserException(Throwable error) {
                    Toast.makeText(getContext(), R.string.on_user_exception, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), R.string.device_not_connected, Toast.LENGTH_SHORT).show();
        }
    }

    private void getReferences(View v) {
        tvName = (TextView) v.findViewById(R.id.tv_name);
        tvTagline = (TextView) v.findViewById(R.id.tv_tagline);
        tvFollowers = (TextView) v.findViewById(R.id.tv_followers);
        tvFollowing = (TextView) v.findViewById(R.id.tv_following);
        ivImage = (ImageView) v.findViewById(R.id.iv_profile_image);
    }


    private void fillWithHeaderValues() {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("@" + user.getScreenName());

        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(Integer.toString(user.getFollowersCount()) + " followers");
        tvFollowing.setText(Integer.toString(user.getFollowingCount()) + " following");

        //todo: picasso on ivImage
    }


}
