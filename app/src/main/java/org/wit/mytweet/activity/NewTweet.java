package org.wit.mytweet.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.wit.mytweet.model.Tweet;

import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @file NewTweet.java
 * @brief Class to provide functionality to newtweet.xml layout
 * @version 2016.10.03
 * @author michaelfoy
 */
public class NewTweet extends AppCompatActivity implements TextWatcher, View.OnClickListener {

  private Button emailButton;
  private Button contactButton;
  private Button tweetButton;
  private EditText tweetText;
  private Tweet tweet;
  private TextView date;
  private TextView chars;
  private MyTweetApp app;
  private int counter;
  private int tweetLength;

  /**
   * Activates the layout and instantiates it's widgets
   *
   * @param savedInstanceState Saved data pertaining to the activity
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.newtweet);

    emailButton = (Button) findViewById(R.id.emailButton);
    contactButton = (Button) findViewById(R.id.contactButton);
    tweetButton = (Button) findViewById(R.id.tweetButton);
    tweetText = (EditText) findViewById(R.id.tweet);
    chars = (TextView) findViewById(R.id.chars);
    date = (TextView) findViewById(R.id.date);
    app = (MyTweetApp) getApplication();

    // Retrieves the currently loaded tweet
    tweet = app.getTempTweet();

    Log.v("MyTweet", tweet.getTweeterName() + " " + tweet.id);

    resetCounter();
    date.setText(editDate());

    tweetText.addTextChangedListener(this);
    tweetButton.setOnClickListener(this);

    // Checks if the tweet has already been persisted
    checkForTweet(tweet);
  }

  /**
   * Checks if the tweet has been persisted,
   * if so, this is a logged-in user viewing his/her own tweet.
   * TweetText  tweetButton disabled, tweeter can only email tweet
   *
   * @param checkTweet The currently displayed tweet
     */
  private void checkForTweet(Tweet checkTweet) {
    List<Tweet> allTweets = app.dbHelper.selectAllTweets();
    for (Tweet tweet : allTweets) {
      if (checkTweet.getContent().equals(tweet.getContent())) {
        date.setText(tweet.getDate());
        tweetText.setText(tweet.getContent());
        chars.setText("");
        tweetText.setSingleLine(false);
        tweetText.setEnabled(false);
        tweetButton.setEnabled(false);
      }
    }
  }

  /**
   * Formats the date input for output
   */
  public String editDate() {
    SimpleDateFormat formatter = new SimpleDateFormat("EEE. d MMM yyyy, H:mm");
    Date today = new Date();
    return formatter.format(today);
  }

  /**
   * Retrieves the current length of the tweet message
   *
   * @param charSequence The tweet message
   * @param i The first character of the sequence
   * @param i1 The length of the message
   * @param i2 Length of new message
   */
  @Override
  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    tweetLength = charSequence.length();
  }

  /**
   * Modifies the counter according to the number of allowable characters remaining
   *
   * @param editable The changed tweet message
   */
  @Override
  public void afterTextChanged(Editable editable) {
    if (editable.length() <= 0) {
      resetCounter();
      chars.setText("" + counter);
    } else if(editable.length() > tweetLength) {
      chars.setText("" + (counter -= 1));
    } else if (editable.length() < tweetLength) {
      chars.setText("" + (counter += 1));
    }
  }

  /**
   * Implements functionality for the 'tweet', 'contacts' and 'email' buttons
   *
   * @param view The button which has been clicked
   */
  @Override
  public void onClick(View view) {
    switch(view.getId()) {
      case R.id.tweetButton:
        if(tweetText.getText().toString().length() > 0) {
          postTweet();
        } else {
          Toast toast = Toast.makeText(this, "You forgot the tweet!", Toast.LENGTH_SHORT);
          toast.show();
        }
        break;
        
    }
  }

  @Override
  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

  }

  /**
   * Method to reset the tweet text counter
   */
  private void resetCounter() {
    counter = 140;
    chars.setText("" + counter);
  }

  /**
   * Method to post and save a tweet, resets tweet text area
   */
  private void postTweet() {
    String content = tweetText.getText().toString();
    String dateStr = date.getText().toString();
    tweet.setContent(content);
    tweet.setDate(dateStr);
    app.dbHelper.addTweet(tweet);
    app.deleteTempTweet();

    Toast toast = Toast.makeText(this, "Tweet posted", Toast.LENGTH_SHORT);
    toast.show();
    Log.v("MyTweet", "Tweet posted by " + app.getCurrentUser().getFirstName() + " " + app.getCurrentUser().getLastName());
    startActivity(new Intent(this, TweetList.class));
  }
}
