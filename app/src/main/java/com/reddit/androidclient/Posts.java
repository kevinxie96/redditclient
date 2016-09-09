package com.reddit.androidclient;

/**
 * Created by kevin_000 on 8/1/2016.
 */
public class Posts
{
    String subreddit;
    String title;
    String author;
    int points;
    int numComments;
    String permalink;
    String url;
    String domain;
    String id;

    String getDetails(){
        String details=author
                +" posted this and got "
                +numComments
                +" replies";
        return details;
    }

    String getTitle(){
        return title;
    }

    String getScore(){
        return Integer.toString(points);
    }
}
