package org.wit.android.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.wit.mytweet.model.Tweet;

public class DbHelper extends SQLiteOpenHelper {
  static final String TAG = "DbHelper";
  static final String DATABASE_NAME = "myTweet.db";
  static final int DATABASE_VERSION = 1;
  static final String TABLE_TWEETS = "tableTweets";
  static final String TABLE_USERS = "tableUsers";

  static final String PRIMARY_KEY = "id";
  static final String CONTENT = "content";
  static final String DATE = "date";

  Context context;

  public DbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String createTweetTable =
        "CREATE TABLE tableTweets " +
            "(id text primary key, " +
                "content text, date text)";

    String createUserTable =
        "CREATE TABLE tableUsers " +
            "(email text primary key, " +
                "firstName text, lastName text," +
                    "password text)";

    db.execSQL(createTweetTable);
    db.execSQL(createUserTable);
    Log.d(TAG, "DbHelper.onCreated: " + createTweetTable + "; " + createUserTable);
  }

  /**
   * @param tweet Reference to Tweet object to be added to database
   */
  public void addTweet(Tweet tweet) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(PRIMARY_KEY, tweet.id.toString());
    values.put(CONTENT, tweet.getContent());
    values.put(DATE, tweet.getDate());
    // Insert record
    db.insert(TABLE_TWEETS, null, values);
    db.close();
  }


  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table if exists " + TABLE_TWEETS);
    Log.d(TAG, "onUpdated");
    onCreate(db);
  }
}