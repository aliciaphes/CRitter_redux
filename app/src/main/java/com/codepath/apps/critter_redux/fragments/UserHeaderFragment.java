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
import com.codepath.apps.critter_redux.util.Utilities;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;


//this fragment will display some information related to the user that we want to display
public class UserHeaderFragment extends Fragment {

    private TwitterClient twitterClient;
    private User currentUser;

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

        currentUser = Parcels.unwrap(getArguments().getParcelable("user"));
        //if user is not null, it means it's not the logged user
        //therefore we already have the information from the user
        //we just need to display it


        if (currentUser == null) {
            populateUserHeader();
            //populateDummyUserInfo();
        } else {
            fillHeaderWithValues();
        }
    }


    // Create a new fragment
    public static UserHeaderFragment newInstance(User currentUser) {
        UserHeaderFragment fragmentInstance = new UserHeaderFragment();

        Bundle args = new Bundle();
        args.putParcelable("user", Parcels.wrap(currentUser));
        fragmentInstance.setArguments(args);

        return fragmentInstance;
    }


    //this is a dummy method to avoid unnecessary API calls
//    private void populateDummyUserInfo() {
//        JSONObject jsonObject = DummyData.getDummyProfile(getContext());
//        currentUser = User.fromJSON(jsonObject);
//        fillWithHeaderValues();
//    }


    private void populateUserHeader() {

        twitterClient = TwitterApplication.getRestClient();//get singleton client

        //check connectivity:
        if (Utilities.isNetworkAvailable(getContext()) && Utilities.isOnline()) {

            //todo: show and hide progress bar

            twitterClient.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //establish who the user is:
                    currentUser = User.fromJSON(response);

                    //visibly show the user's info
                    fillHeaderWithValues();
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


    private void fillHeaderWithValues() {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("@" + currentUser.getScreenName());

        tvName.setText(currentUser.getName());
        tvTagline.setText(currentUser.getTagline());
        tvFollowers.setText(Integer.toString(currentUser.getFollowersCount()) + " followers");
        tvFollowing.setText(Integer.toString(currentUser.getFollowingCount()) + " following");

        //load image with Picasso
        Picasso.with(getContext()).load(currentUser.getProfileURL())
                .placeholder(R.drawable.placeholder)
                .into(ivImage);
    }
}
