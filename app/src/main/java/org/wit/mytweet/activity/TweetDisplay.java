package org.wit.mytweet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.R;
import org.wit.mytweet.model.Tweet;

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

    UUID tweetId = (UUID) getIntent().getExtras().getSerializable("TWEET_ID");

    app = (MyTweetApp) getApplication();

    tweet = app.getTweetById(tweetId);
    tweetText.setText(tweet.getContent());
    tweeter.setText(tweet.getTweeterName());
    date.setText(tweet.getDate());

    Log.v("MyTweet","Display tweet page opened");
  }

}
