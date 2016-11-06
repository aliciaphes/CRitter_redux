package com.codepath.apps.critter_redux;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;




/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API, in this case dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 *
 * EVERY METHOD HERE IS AN ENDPOINT
 */


public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = "IEk0ydY8yN0G8Et2RiQnqbtaA";
    public static final String REST_CONSUMER_SECRET = "A5AooQVuIk27OYzJcZn9JVcg7Lg5kCyGUhekTozUBwrs8nMyK5";
    public static final String REST_CALLBACK_URL = "oauth://critter";

    public static final int NUM_TWEETS_TO_RETRIEVE = 15;

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }


    // DEFINE METHODS for different API endpoints here


    /* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
     * 	  i.e getApiUrl("statuses/home_timeline.json");
     * 2. Define the parameters to pass to the request (query or body)
     *    i.e RequestParams params = new RequestParams("foo", "bar");
     * 3. Define the request method and make a call to the client
     *    i.e client.get(apiUrl, params, handler);
     *    i.e client.post(apiUrl, params, handler);
     */
    public void getHomeTimeline(AsyncHttpResponseHandler handler, long maxId) {
        String apiURL = getApiUrl("statuses/home_timeline.json");

        RequestParams params = new RequestParams();
        params.put("count", NUM_TWEETS_TO_RETRIEVE);


        //maxId: Returns results with an ID less than (i.e. older than) or equal to the specified ID.
        if (maxId != -1L) {//not the first call
            params.put("max_id", maxId);
        }

        //execute the request, which will be handled by handler:
        getClient().get(apiURL, params, handler);
    }


    public void getMentionsTimeline(AsyncHttpResponseHandler handler, long maxId) {
        String apiURL = getApiUrl("statuses/mentions_timeline.json");

        RequestParams params = new RequestParams();
        params.put("count", NUM_TWEETS_TO_RETRIEVE);


        //maxId: Returns results with an ID less than (i.e. older than) or equal to the specified ID.
        if (maxId != -1L) {//not the first call
            params.put("max_id", maxId);
        }

        //execute the request, which will be handled by handler:
        getClient().get(apiURL, params, handler);
    }


    public void getUserTimeline(AsyncHttpResponseHandler handler, long maxId, String screenName) {
        String apiURL = getApiUrl("statuses/user_timeline.json");

        RequestParams params = new RequestParams();
        params.put("count", NUM_TWEETS_TO_RETRIEVE);

        params.put("screen_name", screenName);

        //maxId: Returns results with an ID less than (i.e. older than) or equal to the specified ID.
        if (maxId != -1L) {//not the first call
            params.put("max_id", maxId);
        }

        //execute the request, which will be handled by handler:
        getClient().get(apiURL, params, handler);
    }


    public void getUserInfo(AsyncHttpResponseHandler handler) {
        String apiURL = getApiUrl("account/verify_credentials.json");

        //execute the request, which will be handled by handler:
        getClient().get(apiURL, null, handler);
    }


    public void postTweet(AsyncHttpResponseHandler handler, String tweetText) {
        String apiURL = getApiUrl("statuses/update.json");

        RequestParams params = new RequestParams();
        params.put("status", tweetText);

        //execute the request:
        getClient().post(apiURL, params, handler);
    }

}
