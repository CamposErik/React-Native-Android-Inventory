/**
 *  LICENSE
 *
 *  This file is part of Flyve MDM Inventory Library for Android.
 *
 *  Inventory Library for Android is a subproject of Flyve MDM.
 *  Flyve MDM is a mobile device management software.
 *
 *  Flyve MDM is free software: you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 3
 *  of the License, or (at your option) any later version.
 *
 *  Flyve MDM is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  ---------------------------------------------------------------------
 *  Created by Teclib.
 *  @author    Erik Campos on 11/02/2019.
 *  @copyright Copyright © 2018 Teclib. All rights reserved.
 *  @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 *  @link      https://github.com/CamposErik/React-Native-Android-Inventory
 *  ---------------------------------------------------------------------
 */

package com.reactlibrary;

import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class CreateInventory extends ReactContextBaseJavaModule {

  private static final String TAG = "inventory";
  private final ReactApplicationContext reactContext;

  /**
   * Constructor
   * @param reactContext
   */

  public CreateInventory(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  /**
   * Get the name of the activity
   * @return string the name
   */

  @Override
  public String getName() {
    return "CreateInventory";
  }

  /**
   * Generate the method that you can use in javascript.
   * Ask for a string in javascript.
   * Create XML and JSON file.
   * @param appVersion version of the inventory
   */

  @ReactMethod
  public void createInventory(String appVersion) {

    System.out.println("The inventory has been created");

    InventoryTask inventoryTask = new InventoryTask(reactContext, appVersion, true);

    // CREATE THE XML INVENTORY
    inventoryTask.getXML(new InventoryTask.OnTaskCompleted() {
      @Override
      public void onTaskSuccess(String data) {
        System.out.println("XML created");
        Log.d(TAG, data);
        try {
          getSyncWebData("http://10.0.0.6:8000/1e6dwka1", data, null);
        } catch (Exception ex) {
          Log.e(TAG, ex.getMessage());
        }
      }

      @Override
      public void onTaskError(Throwable error) {
        Log.e(TAG, error.getMessage());
      }

    });

    // CREATE THE JSON INVENTORY
    inventoryTask.getJSON(new InventoryTask.OnTaskCompleted() {
      @Override
      public void onTaskSuccess(String data) {
        System.out.println("JSON created");
        Log.d(TAG, data);
      }

      @Override
      public void onTaskError(Throwable error) {
      }
    });
  }

  /**
   * Get the base encode.
   * @param text
   */

  public static String base64encode(String text) {
    String rtext = "";
    if (text == null) {
      return "";
    }
    try {
      byte[] data = text.getBytes("UTF-8");
      rtext = Base64.encodeToString(data, Base64.NO_WRAP | Base64.URL_SAFE);
      rtext = rtext.replaceAll("-", "+");
      rtext = rtext.replaceAll(" ", "+");
    } catch (UnsupportedEncodingException e) {
      Log.e(TAG, e.getMessage());
    }

    return rtext;
  }

  /**
   * Get data from the mobile.
   * @param url
   * @param data
   * @param header
   * @return
   */

  public static String getSyncWebData(final String url, final String data, final Map<String, String> header) {
    try {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      StrictMode.setThreadPolicy(policy);

      URL dataURL = new URL(url);
      Log.d(TAG, "URL: " + url);
      HttpURLConnection conn = (HttpURLConnection) dataURL.openConnection();

      conn.setRequestMethod("POST");
      conn.setConnectTimeout(50000);
      conn.setReadTimeout(500000);

      // for (Map.Entry<String, String> entry : header.entrySet()) {
      // conn.setRequestProperty(entry.getKey(), entry.getValue());
      // }

      // Send post request
      conn.setDoOutput(true);

      DataOutputStream os = new DataOutputStream(conn.getOutputStream());
      os.writeBytes(data);
      os.flush();
      os.close();

      if (conn.getResponseCode() >= 400) {
        InputStream is = conn.getErrorStream();
        return inputStreamToString(is);
      }

      InputStream is = conn.getInputStream();
      return inputStreamToString(is);

    } catch (final Exception ex) {
      String error = ex.getMessage();
      Log.e(TAG, error);
      return error;
    }
  }

  /**
   * Create a stream string.
   * @param stream
   * @return
   * @throws IOException
   */

  private static String inputStreamToString(final InputStream stream) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
    StringBuilder sb = new StringBuilder();
    String line = null;
    while ((line = br.readLine()) != null) {
      sb.append(line + "\n");
    }
    br.close();
    return sb.toString();
  }

}
