package com.reddit.androidclient;

/**
 * Created by kevin_000 on 8/7/2016.
 */
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * As of now, all this activity does is create and
 * render a fragment.
 *
 * @author Hathy
 */
public class MainActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_client);
        addFragment();
    }

    void addFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragments_holder
                        , PostsFragment.newInstance("nba"))
                .commit();
    }
}
