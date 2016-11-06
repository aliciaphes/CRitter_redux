package com.codepath.apps.critter_redux.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.critter_redux.R;
import com.codepath.apps.critter_redux.TwitterApplication;
import com.codepath.apps.critter_redux.TwitterClient;
import com.codepath.apps.critter_redux.fragments.UserTimelineFragment;
import com.codepath.apps.critter_redux.models.User;
import com.codepath.apps.critter_redux.util.DummyData;
import com.codepath.apps.critter_redux.util.Utilities;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class ProfileActivity extends AppCompatActivity {

    private TwitterClient twitterClient;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //retrieve the screen name from the intent:
        String screenName = getIntent().getStringExtra("screen_name");
        if (savedInstanceState == null) {
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_container, userTimelineFragment);//insert dynamically
            ft.commit();
        }

        //populateUserInfo();
        populateDummyUserInfo();

    }

    private void populateDummyUserInfo() {
        JSONObject jsonObject = DummyData.getDummyProfile(getContext());
        user = User.fromJSON(jsonObject);
        getSupportActionBar().setTitle("@" + user.getScreenName());
        fillHeader(user);
    }



    private void populateUserInfo() {

        twitterClient = TwitterApplication.getRestClient();//get singleton client

        //check connectivity:
        if (Utilities.isNetworkAvailable(getContext()) && Utilities.isOnline()) {

            twitterClient.getUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //grab twitter handle and show it on the bar:
                    user = User.fromJSON(response);
                    getSupportActionBar().setTitle("@" + user.getScreenName());
                    fillHeader(user);
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

    private void fillHeader(User user) {
        TextView tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(user.getName());

        TextView tvTagline = (TextView) findViewById(R.id.tv_tagline);
        tvTagline.setText(user.getTagline());

        TextView tvFollowers = (TextView) findViewById(R.id.tv_followers);
        tvFollowers.setText(Integer.toString(user.getFollowersCount()) + " followers");

        TextView tvFollowing = (TextView) findViewById(R.id.tv_following);
        tvFollowing.setText(Integer.toString(user.getFollowingCount()) + " following");

        ImageView ivImage = (ImageView) findViewById(R.id.iv_profile_image);
        //todo: picasso
    }
}
