
package com.reactlibrary;

import android.content.Context;
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

public class RNAndroidInventoryModule extends ReactContextBaseJavaModule {

  private static final String TAG = "inventory";
  private final ReactApplicationContext reactContext;

  public RNAndroidInventoryModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "Inventory";
  }


  @ReactMethod
  public void Inventory(Context context, String appVersion) {
    InventoryTask inventoryTask = new InventoryTask(context, appVersion, true);

    // CREATE THE XML INVENTORY
    inventoryTask.getXML(new InventoryTask.OnTaskCompleted() {
      @Override
      public void onTaskSuccess(String data) {
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

      @Override
      public void onTaskCompleted(String data) {
        try {
          getSyncWebData("http://10.0.0.6:8000/1e6dwka1", data, null);
        } catch (Exception ex) {
          Log.e(TAG, ex.getMessage());
        }
      }
    });

    //CREATE THE JSON INVENTORY
    inventoryTask.getJSON(new InventoryTask.OnTaskCompleted() {
      @Override
      public void onTaskSuccess(String data) {
        Log.d(TAG, data);
      }

      @Override
      public void onTaskError(Throwable error) {
      }

      @Override
      public void onTaskCompleted(String data) {
        Log.d(TAG, data);
      }
    });
  }

  public static String base64encode(String text) {
    String rtext = "";
    if(text == null) { return ""; }
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

  public static String getSyncWebData(final String url, final String data, final Map<String, String> header) {
    try {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      StrictMode.setThreadPolicy(policy);

      URL dataURL = new URL(url);
      Log.d(TAG, "URL: " + url);
      HttpURLConnection conn = (HttpURLConnection)dataURL.openConnection();

      conn.setRequestMethod("POST");
      conn.setConnectTimeout(50000);
      conn.setReadTimeout(500000);

//            for (Map.Entry<String, String> entry : header.entrySet()) {
//                conn.setRequestProperty(entry.getKey(), entry.getValue());
//            }

      // Send post request
      conn.setDoOutput(true);

      DataOutputStream os = new DataOutputStream(conn.getOutputStream());
      os.writeBytes(data);
      os.flush();
      os.close();

      if(conn.getResponseCode() >= 400) {
        InputStream is = conn.getErrorStream();
        return inputStreamToString(is);
      }

      InputStream is = conn.getInputStream();
      return inputStreamToString(is);

    }
    catch (final Exception ex) {
      String error = ex.getMessage();
      Log.e(TAG, error);
      return error;
    }
  }

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
