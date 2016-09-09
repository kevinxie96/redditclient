package com.reddit.androidclient;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

/**
 * Created by kevin_000 on 8/1/2016.
 */
public class PostsHolder
{
    /**
     * We will be fetching JSON data from the API.
     */
    private final String urlTemplate=
            "http://www.reddit.com/r/SUBREDDIT_NAME/"
                    +".json"
                    +"?after=AFTER";

    String subreddit;
    String url;
    String after;

    PostsHolder(String sr){
        subreddit=sr;
        after="";
        generateURL();
    }

    /**
     * Generates the actual URL from the template based on the
     * subreddit name and the 'after' property.
     */
    private void generateURL(){
        url=urlTemplate.replace("SUBREDDIT_NAME", subreddit);
        url=url.replace("AFTER", after);
    }

    /**
     * Returns a list of Post objects after fetching data from
     * Reddit using the JSON API.
     *
     * @return
     */
    List<Posts> fetchPosts(){
        String raw=AndroidClient.readContents(url);
        List<Posts> list=new ArrayList<Posts>();
        try{
            JSONObject data=new JSONObject(raw)
                    .getJSONObject("data");
            JSONArray children=data.getJSONArray("children");

            //Using this property we can fetch the next set of
            //posts from the same subreddit
            after=data.getString("after");

            for(int i=0;i<children.length();i++){
                JSONObject cur=children.getJSONObject(i)
                        .getJSONObject("data");
                Posts p=new Posts();
                p.title=cur.optString("title");
                p.url=cur.optString("url");
                p.numComments=cur.optInt("num_comments");
                p.points=cur.optInt("score");
                p.author=cur.optString("author");
                p.subreddit=cur.optString("subreddit");
                p.permalink=cur.optString("permalink");
                p.domain=cur.optString("domain");
                p.id=cur.optString("id");
                if(p.title!=null)
                    list.add(p);
            }
        }catch(Exception e){
            Log.e("fetchPosts()",e.toString());
        }
        return list;
    }

    /**
     * This is to fetch the next set of posts
     * using the 'after' property
     * @return
     */
    List<Posts> fetchMorePosts(){
        generateURL();
        return fetchPosts();
    }
}
