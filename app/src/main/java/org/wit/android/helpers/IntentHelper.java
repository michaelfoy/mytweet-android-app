package org.wit.android.helpers;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;

/**
 * @file IntentHelper.java
 * @brief Helper class containing methods to pass intents to activities
 * @author michaelfoy
 * @version 2016.11.06
 */
public class IntentHelper {
  public static void startActivity(Activity parent, Class classname) {
    Intent intent = new Intent(parent, classname);
    parent.startActivity(intent);
  }

  public static void startActivityWithData(Activity parent, Class classname, String extraID, Serializable extraData) {
    Intent intent = new Intent(parent, classname);
    intent.putExtra(extraID, extraData);
    parent.startActivity(intent);
  }

  public static void startActivityWithDataForResult(Activity parent, Class classname, String extraID, Serializable extraData, int idForResult) {
    Intent intent = new Intent(parent, classname);
    intent.putExtra(extraID, extraData);
    parent.startActivityForResult(intent, idForResult);
  }

  public static void navigateUp(Activity parent) {
    Intent upIntent = NavUtils.getParentActivityIntent(parent);
    NavUtils.navigateUpTo(parent, upIntent);
  }

  public static void selectContact(Activity parent, int id)
  {
    Intent selectContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    parent.startActivityForResult(selectContactIntent, id);
  }
}