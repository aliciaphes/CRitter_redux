package com.codepath.apps.critter_redux.util;


import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class DummyData {


    public static final String FILE_NAME = "DummyTimeline.txt";

    public static final String DUMMY_TWEET = "{\n" +
            "  \"coordinates\": null,\n" +
            "  \"favorited\": false,\n" +
            "  \"created_at\": \"Wed Sep 05 00:37:15 +0000 2012\",\n" +
            "  \"truncated\": false,\n" +
            "  \"id_str\": \"243145735212777472\",\n" +
            "  \"entities\": {\n" +
            "    \"urls\": [\n" +
            "\n" +
            "    ],\n" +
            "    \"hashtags\": [\n" +
            "      {\n" +
            "        \"text\": \"peterfalk\",\n" +
            "        \"indices\": [\n" +
            "          35,\n" +
            "          45\n" +
            "        ]\n" +
            "      }\n" +
            "    ],\n" +
            "    \"user_mentions\": [\n" +
            "\n" +
            "    ]\n" +
            "  },\n" +
            "  \"in_reply_to_user_id_str\": null,\n" +
            "  \"text\": \"This is a #dummy #tweet\",\n" +
            "  \"contributors\": null,\n" +
            "  \"retweet_count\": 0,\n" +
            "  \"id\": 243145735212777472,\n" +
            "  \"in_reply_to_status_id_str\": null,\n" +
            "  \"geo\": null,\n" +
            "  \"retweeted\": false,\n" +
            "  \"in_reply_to_user_id\": null,\n" +
            "  \"place\": null,\n" +
            "  \"user\": {\n" +
            "    \"name\": \"Dummy Tweeter\",\n" +
            "    \"profile_image_url\": \"http://a0.twimg.com/profile_images/1751674923/new_york_beard_normal.jpg\",\n" +
            "    \"created_at\": \"Wed May 28 00:20:15 +0000 2008\",\n" +
            "    \"is_translator\": true,\n" +
            "    \"follow_request_sent\": false,\n" +
            "    \"id_str\": \"14927800\",\n" +
            "    \"entities\": {\n" +
            "      \"url\": {\n" +
            "        \"urls\": [\n" +
            "          {\n" +
            "            \"expanded_url\": \"http://www.jason-costa.blogspot.com/\",\n" +
            "            \"url\": \"http://t.co/YCA3ZKY\",\n" +
            "            \"indices\": [\n" +
            "              0,\n" +
            "              19\n" +
            "            ],\n" +
            "            \"display_url\": \"jason-costa.blogspot.com\"\n" +
            "          }\n" +
            "        ]\n" +
            "      },\n" +
            "      \"description\": {\n" +
            "        \"urls\": []\n" +
            "      }\n" +
            "    },\n" +
            "    \"default_profile\": false,\n" +
            "    \"contributors_enabled\": false,\n" +
            "    \"url\": \"http://t.co/YCA3ZKY\",\n" +
            "    \"favourites_count\": 883,\n" +
            "    \"utc_offset\": -28800,\n" +
            "    \"id\": 14927800,\n" +
            "    \"profile_image_url_https\": \"https://si0.twimg.com/profile_images/1751674923/new_york_beard_normal.jpg\",\n" +
            "    \"profile_use_background_image\": true,\n" +
            "    \"listed_count\": 150,\n" +
            "    \"profile_text_color\": \"333333\",\n" +
            "    \"protected\": false,\n" +
            "    \"lang\": \"en\",\n" +
            "    \"followers_count\": 8760,\n" +
            "    \"time_zone\": \"Pacific Time (US & Canada)\",\n" +
            "    \"profile_background_image_url_https\": \"https://si0.twimg.com/images/themes/theme6/bg.gif\",\n" +
            "    \"verified\": false,\n" +
            "    \"profile_background_color\": \"709397\",\n" +
            "    \"notifications\": false,\n" +
            "    \"description\": \"Platform at Twitter\",\n" +
            "    \"geo_enabled\": true,\n" +
            "    \"statuses_count\": 5532,\n" +
            "    \"default_profile_image\": false,\n" +
            "    \"friends_count\": 166,\n" +
            "    \"profile_background_image_url\": \"http://a0.twimg.com/images/themes/theme6/bg.gif\",\n" +
            "    \"show_all_inline_media\": true,\n" +
            "    \"screen_name\": \"dummyMan\",\n" +
            "    \"following\": false\n" +
            "  }}";


    public static JSONArray getDummyTimeline(Context context) {
        JSONArray jsonArray = new JSONArray();


        //todo: ...reading from json file (using background thread) goes here...

        String str = loadJSONFromFile(context);

        //the file already contains the array we need


        try {
            jsonArray = new JSONArray(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }


//        JSONParser parser = new JSONParser();
//        Object obj = parser.parse(new FileReader(PATH));
//        JSONObject jsonObject = (JSONObject) obj;



//        JsonReader reader = new JsonReader(new InputStreamReader(PATH, "UTF-8"));
//        JSONObject obj = reader.readObject();






/*        JsonReader reader = new JsonReader(new InputStreamReader(PATH, "UTF-8"));
        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMessage(reader));
        }
        reader.endArray();
        reader.close();
        return messages;*/




//    //Using GSON lib:
//    Gson gson = new Gson();
//    String data="[{\"A\":\"a\",\"B\":\"b\",\"C\":\"c\",\"D\":\"d\",\"E\":\"e\",\"F\":\"f\",\"G\":\"g\"}]";
//    JsonParser jsonParser = new JsonParser();
//    JsonArray jsonArray = (JsonArray) jsonParser.parse(data);



//todo: http://stackoverflow.com/questions/35000998/how-do-i-turn-a-json-file-into-a-java-8-object-stream

    public static String loadJSONFromFile(Context context)  {
        String json;
        InputStream is;

        try {
            //is = new FileInputStream(FILE_NAME);
            is = context.getAssets().open(FILE_NAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}
