package org.wit.mytweet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.R;
import org.wit.mytweet.model.Tweet;
import static org.wit.android.helpers.IntentHelper.navigateUp;

import java.util.UUID;

/**
 * @file NewTweet.java
 * @brief Class to provide functionality to newtweet.xml layout
 * @version 2016.10.17
 * @author michaelfoy
 */
public class TweetDisplay extends AppCompatActivity {


  private TextView tweetText;
  private Tweet tweet;
  private TextView date;
  private TextView tweeter;
  private MyTweetApp app;

  /**
   * Activates the layout and instantiates it's widgets
   *
   * @param savedInstanceState Saved data pertaining to the activity
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tweet_display);

    tweetText = (TextView) findViewById(R.id.tweet);
    tweeter = (TextView) findViewById(R.id.tweeter);
    date = (TextView) findViewById(R.id.date);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    UUID tweetId = (UUID) getIntent().getExtras().getSerializable("TWEET_ID");

    app = (MyTweetApp) getApplication();

    tweet = app.getTweetById(tweetId);
    tweetText.setText(tweet.getContent());
    tweeter.setText(tweet.getTweeterName());
    date.setText(tweet.getDate());

    Log.v("MyTweet","Display tweet page opened");
  }

  /**
   * Returns true if menu can be displayed
   *
   * @param menu Menu object to be displayed
   * @return True if menu can be displayed
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.mytweet_menu, menu);
    return true;
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
      case android.R.id.home:  navigateUp(this);
        return true;

      case R.id.menuItemNewTweet: Tweet tweet = new Tweet();
        tweet.setId();
        tweet.setTweeter(app.getCurrentUser().id.toString());
        MyTweetApp.setTempTweet(tweet);
        startActivity(new Intent(this, NewTweet.class));
        return true;

      case R.id.action_mytweets:
        Toast toast = Toast.makeText(this, "mytweets", Toast.LENGTH_SHORT);
        toast.show();
        return true;

      case R.id.action_settings:
        Toast toast1 = Toast.makeText(this, "settings", Toast.LENGTH_SHORT);
        toast1.show();
        return true;

      case R.id.action_logout:
        app.logout();
        startActivity(new Intent(this, Welcome.class));
        Toast logoutToast = Toast.makeText(this, "Successfully logged out :)", Toast.LENGTH_LONG);
        logoutToast.show();
        return true;

      default: return super.onOptionsItemSelected(item);
    }
  }

}
