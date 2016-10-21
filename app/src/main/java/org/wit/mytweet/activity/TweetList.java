package org.wit.mytweet.activity;

import static org.wit.android.helpers.IntentHelper.startActivityWithData;
import static org.wit.android.helpers.IntentHelper.startActivityWithDataForResult;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.TextView;

import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.model.Tweet;

import java.util.List;

/**
 * @file TweetList.java
 * @brief Controller for TweetList activity
 * @author michaelfoy
 * @version 2016.10.18
 */
public class TweetList extends AppCompatActivity implements AdapterView.OnItemClickListener {
  private ListView listView;
  private List<Tweet> tweetList;
  private TweetAdapter adapter;
  public MyTweetApp app;

  /**
   * Implements the layout, instantiates all fields
   *
   * @param savedInstanceState Data from previously saved instance
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(R.string.app_name);
    setContentView(R.layout.tweet_list);

    app = (MyTweetApp) getApplication();
    tweetList = app.getTweets();

    listView = (ListView) findViewById(R.id.tweetList);
    adapter = new TweetAdapter(this, tweetList);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(this);
  }

  /**
   * Listener for click on Tweet item in list. If selected tweet was posted
   * by the logged-in-user, option to email tweet supplied. Otherwise, tweet is read-only.
   *
   * @param parent The parent adapter view
   * @param view The view that was clicked within the adapterView
   * @param position Position of the view in the adapter
   * @param id Row id of clicked item
   */
  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id)
  {
    Tweet tweet = adapter.getItem(position);
    if (app.getCurrentUser().id.equals(tweet.getTweeter())) {
      app.setTempTweet(tweet);
      startActivity(new Intent(this, NewTweet.class));
    } else {
      startActivityWithData(this, TweetDisplay.class, "TWEET_ID", tweet.id);
    }
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
      case R.id.menuItemNewTweet: Tweet tweet = new Tweet();
        tweet.setId();
        tweet.setTweeter(app.getCurrentUser().id.toString());
        MyTweetApp.setTempTweet(tweet);
        startActivity(new Intent(this, NewTweet.class));
        return true;

      default: return super.onOptionsItemSelected(item);
    }
  }
}

class TweetAdapter extends ArrayAdapter<Tweet> {
  private Context context;

  public TweetAdapter(Context context, List<Tweet> tweets) {
    super(context, 0, tweets);
    this.context = context;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.list_item_tweet, null);
    }
    Tweet tweet = getItem(position);

    TextView geolocation = (TextView) convertView.findViewById(R.id.tweetListItemContent);
    geolocation.setText(tweet.getContent());

    TextView dateTextView = (TextView) convertView.findViewById(R.id.tweetListItemDate);
    dateTextView.setText(tweet.getDate());

    return convertView;
  }
}