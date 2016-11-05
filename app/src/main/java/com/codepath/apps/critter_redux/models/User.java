package com.codepath.apps.critter_redux.models;


import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;


@Parcel
public class User {

    //fields must be public when using Parceler
    String name;
    long userID;
    String screenName; //handle
    String profileURL;



    //Parceler requires an empty constructor:
    public User(){
    }

    public String getName() {
        return name;
    }

    public long getUserID() {
        return userID;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileURL() {
        return profileURL;
    }

    //deserialize and return a user
    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();

        try {
            user.name = jsonObject.getString("name");
            user.userID = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileURL = jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

}
