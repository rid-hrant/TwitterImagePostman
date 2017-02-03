package com.example.hrant.twitterimagepostman.requests;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hrant.twitterimagepostman.interfaces.ConnectionListener;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hrant on 2/2/17.
 */
public class CheckNetConnection extends AsyncTask<Void, Void, Boolean> {

    private boolean isConnected;
    ConnectionListener mConnectionListener;

    public CheckNetConnection(ConnectionListener connectionListener) {
        mConnectionListener = connectionListener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        while (true) {
            try {
                URL url = new URL("http://www.google.com");

                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(3000); // mTimeout is in seconds
                urlc.connect();

                if (urlc.getResponseCode() == 200) {
                    Log.e("", "getResponseCode == 200");
                    return true;
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    protected void onPostExecute(Boolean boo) {
        super.onPostExecute(boo);
        mConnectionListener.connected(boo);
    }
}
