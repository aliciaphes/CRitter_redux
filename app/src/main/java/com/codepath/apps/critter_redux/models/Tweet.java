package com.codepath.apps.critter_redux.models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Tweet {

    //fields must be public when using Parceler
    String body;
    long tweetID;
    com.codepath.apps.critter_redux.models.User user;
    String createdAt;


    //Parceler requires an empty constructor:
    public Tweet(){
    }



    public long getTweetID() {
        return tweetID;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public com.codepath.apps.critter_redux.models.User getUser() {
        return user;
    }


    //deserialize the JSON and build the Tweet object
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.tweetID = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = com.codepath.apps.critter_redux.models.User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }


    public static ArrayList<Tweet> fromJSONArray(JSONArray response) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject tweetJSON = response.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJSON);
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;//continue processing
            }
        }
        return tweets;
    }
}
