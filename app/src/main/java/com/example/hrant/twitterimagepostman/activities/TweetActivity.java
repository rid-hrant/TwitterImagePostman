package com.example.hrant.twitterimagepostman.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hrant.twitterimagepostman.DBHelper;
import com.example.hrant.twitterimagepostman.R;
import com.example.hrant.twitterimagepostman.interfaces.ConnectionListener;
import com.example.hrant.twitterimagepostman.interfaces.ImageUploadListener;
import com.example.hrant.twitterimagepostman.interfaces.StatusUpdateListener;
import com.example.hrant.twitterimagepostman.models.UploadedImage;
import com.example.hrant.twitterimagepostman.receivers.NetworkStateReceiver;
import com.example.hrant.twitterimagepostman.requests.CheckNetConnection;
import com.example.hrant.twitterimagepostman.requests.ImageUploader;
import com.example.hrant.twitterimagepostman.requests.StatusPostman;
import com.example.hrant.twitterimagepostman.utils.Constants;

import org.scribe.model.Response;

import java.io.IOException;
import java.util.Stack;

/**
 * Created by hrant on 2/2/17.
 */
public class TweetActivity extends AppCompatActivity {

    private EditText mTweetStatusEditText;
    private Button mTweetButton;
    private String mPathFromURI;
    private NetworkStateReceiver mNetworkStateReceiver;
    private Stack<String> mPathStack = new Stack<>();
    private Stack<String> mStatusTextStack = new Stack<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        mPathFromURI = "";
        Intent sentIntent = getIntent();
        if (sentIntent != null) {
            Bundle extras = sentIntent.getExtras();
            if (extras != null && extras.containsKey(Constants.extra.ABSOLUTE_PATH)) {
                mPathFromURI = extras.getString(Constants.extra.ABSOLUTE_PATH);
            }
        }

        findViews();
        tweetButtonClk();

        Toast.makeText(TweetActivity.this, mPathFromURI, Toast.LENGTH_SHORT).show();
    }

    private void tweetButtonClk() {
        mTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPathStack.push(mPathFromURI);
                mStatusTextStack.push(mTweetStatusEditText.getText().toString());
                checkConnection();
            }
        });
    }

    private void checkConnection() {

        mNetworkStateReceiver = new NetworkStateReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent == null || intent.getExtras() == null)
                    return;

                ConnectivityManager manager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo ni = manager.getActiveNetworkInfo();

                if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                    connected = true;
                } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,
                        Boolean.FALSE)) {

                    connected = false;
                }

                if (connected) {

                    /*
                    * ip check
                    * */
                    new CheckNetConnection(new ConnectionListener() {

                        @Override
                        public void connected(boolean isConnected) {
                            if (isConnected) {
                                uploadImage();
                            } else {
                                Toast.makeText(TweetActivity.this, "Ip address error.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).execute();
                } else {
                    Toast.makeText(TweetActivity.this, "Connection error.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        IntentFilter updateIntentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mNetworkStateReceiver, updateIntentFilter);
    }

    private void uploadImage() {
        if (mPathStack.size() > 0) {

            ImageUploader.upload(mPathStack.pop(), new ImageUploadListener() {

                @Override
                public void onResponse(UploadedImage uploadedImage) {
                    if (uploadedImage != null)
                        updateStatus(uploadedImage.getMedia_id_string());
                }

                @Override
                public void onError(IOException e) {

                }
            });
        }
    }

    private void updateStatus(String imageId) {

        if (mStatusTextStack.size() > 0) {
            new StatusPostman(mStatusTextStack.pop(), imageId, new StatusUpdateListener() {
                @Override
                public void onResponse(Response response) {

                    if (response == null) {
                        System.out.println("Response is null.");
                        return;
                    }

                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                    dbHelper.insertStatus(response.getBody());

                    int responseCode = response.getCode();
                    Toast.makeText(TweetActivity.this, "Updated: " + String.valueOf(responseCode) +
                            response.getMessage(), Toast.LENGTH_SHORT).show();

                    if (mNetworkStateReceiver != null)
                        unregisterReceiver(mNetworkStateReceiver);
                }
            }).execute();
        }
    }

    private void findViews() {
        mTweetStatusEditText = (EditText) findViewById(R.id.edit_tweet_status);
        mTweetButton = (Button) findViewById(R.id.btn_tweet);
    }
}
