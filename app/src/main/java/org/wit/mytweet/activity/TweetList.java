package org.wit.mytweet.activity;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.wit.mytweet.R;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.model.Tweet;
import org.wit.mytweet.model.User;

import java.util.List;

import static org.wit.android.helpers.IntentHelper.startActivityWithData;

public class TweetList extends Activity implements AdapterView.OnItemClickListener {
  private ListView listView;
  private List<Tweet> tweetList;
  private TweetAdapter adapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(R.string.app_name);
    setContentView(R.layout.tweet_list);

    listView = (ListView) findViewById(R.id.tweetList);
    listView.setOnItemClickListener(this);

    MyTweetApp app = (MyTweetApp) getApplication();
    tweetList = app.getTweets();
    adapter = new TweetAdapter(this, tweetList);
    listView.setAdapter(adapter);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id)
  {
    /*List<Tweet> tweets = MyTweetApp.getTweets();
    for(Tweet tweet : tweets) {
      Log.v("MyTweet", tweet.getTweeter() + " " + tweet.id);
    }*/
    Tweet tweet = adapter.getItem(position);
    startActivityWithData(this, TweetDisplay.class, "TWEET_ID", tweet.id);
    //Intent intent = new Intent(this, LogIn.class);
    //startActivity(intent);
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