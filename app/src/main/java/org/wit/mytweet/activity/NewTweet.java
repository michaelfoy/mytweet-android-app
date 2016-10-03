package org.wit.mytweet.activity;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @file NewTweet.java
 * @brief Class to provide functionality to newtweet.xml layout
 * @version 2016.10.03
 * @author michaelfoy
 */
public class NewTweet extends AppCompatActivity {

  private Button emailButton;
  private Button contactButton;
  private Button tweetButton;
  private EditText tweet;
  private TextView date;
  private TextView chars;
  private Application app;
  private Date currentDate;
  private int counter = 140;

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
    tweet = (EditText) findViewById(R.id.tweet);
    chars = (TextView) findViewById(R.id.chars);
    date = (TextView) findViewById(R.id.date);
    chars.setText("" + counter);
    currentDate = new Date();
    date.setText(editDate());
    app = (MyTweetApp) getApplication();

    Log.v("MyTweet","New tweet page opened");
  }

  /**
   * Formats the date input for output
   */
  public String editDate() {
    SimpleDateFormat formatter = new SimpleDateFormat("EEE d MMM yyyy H:mm");
    Date today = new Date();
    return formatter.format(today);
  }

}
