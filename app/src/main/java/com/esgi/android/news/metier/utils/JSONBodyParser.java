package com.esgi.android.news.metier.utils;

import com.esgi.android.news.metier.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 08/06/16.
 */
public class JSONBodyParser {

    ArrayList<Item> items = null;
    JSONObject jsonObject;

    //COMMON TAGS
    private static final String RESPONSE_DATA_TAG = "responseData";
    private static final String FEED_TAG = "feed";
    private static final String ENTRIES_TAG = "entries";

    private static final String TITLE_TAG = "title";
    private static final String LINK_TAG = "link";
    private static final String DESCRIPTION_TAG = "description";
    private static final String CONTENT_TAG = "content";
    private static final String PUBDATE_TAG = "publishedDate";

    public JSONBodyParser(String jsonStr){
        if(jsonStr != null){
            try{
                jsonObject = new JSONObject(jsonStr);
                items = new ArrayList<>();
            } catch (JSONException ex){
            }
        }
    }

    public List<Item> getEurosportItems(){
        if(jsonObject != null){
            try {
                JSONObject responseDate = jsonObject.getJSONObject(RESPONSE_DATA_TAG);
                JSONObject feedJson = responseDate.getJSONObject(FEED_TAG);
                JSONArray entriesJson = feedJson.getJSONArray(ENTRIES_TAG);

                for(int i=0; i<entriesJson.length(); i++){
                    JSONObject entry = entriesJson.getJSONObject(i);

                    String title = entry.getString(TITLE_TAG);
                    String description = entry.getString(DESCRIPTION_TAG);
                    String link = entry.getString(LINK_TAG);
                    String date = entry.getString(PUBDATE_TAG);

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
