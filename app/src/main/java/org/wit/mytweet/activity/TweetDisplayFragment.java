package org.wit.mytweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.R;
import org.wit.mytweet.model.Tweet;
import static org.wit.android.helpers.IntentHelper.navigateUp;

import java.util.UUID;

/**
 * @file NewTweetFragment.java
 * @brief Class to provide functionality to newtweet.xml layout
 * @version 2016.10.17
 * @author michaelfoy
 */
public class TweetDisplayFragment extends Fragment {

  private Tweet tweet;

  /**
   * Activates the layout and instantiates it's widgets
   *
   * @param savedInstanceState Saved data pertaining to the activity
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    UUID tweetId = (UUID) getActivity().getIntent().getExtras().getSerializable("TWEET_ID");
    tweet = MyTweetApp.getTweetById(tweetId);
    Log.v("MyTweet","Display tweet page opened");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    super.onCreateView(inflater, parent, savedInstanceState);
    View v = inflater.inflate(R.layout.tweet_display, parent, false);
    //NewTweet newTweet = (NewTweet)getActivity();
    TweetDisplay.actionBar.setDisplayHomeAsUpEnabled(true);
    initializeWidgets(v);
    return v;
  }

  private void initializeWidgets(View v) {

    TextView tweeter;
    TextView date;
    TextView tweetText;

    tweetText = (TextView) v.findViewById(R.id.tweet);
    tweeter = (TextView) v.findViewById(R.id.tweeter);
    date = (TextView) v.findViewById(R.id.date);

    tweetText.setText(tweet.getContent());
    tweeter.setText(tweet.getTweeterName());
    date.setText(tweet.getDate());
  }

  /**
   * Displays actionbar menu on page
   *
   * @param menu Menu object to be displayed
   * @param inflater The MenuInflater to create the menu
   */
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
  {
    inflater.inflate(R.menu.mytweet_menu, menu);
    super.onCreateOptionsMenu(menu,inflater);
  }

  /**
   * Returns true if selected menu item is implemented
   *
   * @param item The selected menu item
   * @return True if item implemented
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      case android.R.id.home:  navigateUp(getActivity());
        return true;

      case R.id.menuItemNewTweet: Tweet tweet = new Tweet();
        tweet.setId();
        tweet.setTweeter(MyTweetApp.getCurrentUser().id.toString());
        MyTweetApp.setTempTweet(tweet);
        startActivity(new Intent(getActivity(), NewTweetFragment.class));
        return true;

      case R.id.action_mytweets:
        Toast toast = Toast.makeText(getActivity(), "mytweets", Toast.LENGTH_SHORT);
        toast.show();
        return true;

      case R.id.action_settings:
        Toast toast1 = Toast.makeText(getActivity(), "settings", Toast.LENGTH_SHORT);
        toast1.show();
        return true;

      case R.id.action_logout:
        MyTweetApp.logout();
        startActivity(new Intent(getActivity(), Welcome.class));
        Toast logoutToast = Toast.makeText(getActivity(), "Successfully logged out :)", Toast.LENGTH_LONG);
        logoutToast.show();
        return true;

      default: return super.onOptionsItemSelected(item);
    }
  }

}
