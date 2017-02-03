package com.example.hrant.twitterimagepostman.requests;

import android.os.AsyncTask;
import android.util.Log;

import com.example.hrant.twitterimagepostman.interfaces.StatusUpdateListener;
import com.example.hrant.twitterimagepostman.utils.Constants;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * Created by hrant on 2/2/17.
 */
public class StatusPostman extends AsyncTask<Void, Void, Void> {

    private StatusUpdateListener mStatusUpdateListener;
    private String mStatus;
    private String mImageId;
    private Response mResponse;

    public StatusPostman(String status, String imageId,
                         StatusUpdateListener statusUpdateListener) {

        mStatusUpdateListener = statusUpdateListener;
        mStatus = status;
        mImageId = imageId;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        OAuthService service = new ServiceBuilder()
                .provider(TwitterApi.Authenticate.class)
                .apiKey(Constants.TwitterApi.CONSUMER_KEY)
                .apiSecret(Constants.TwitterApi.CONSUMER_SECRET)
                .build();

        OAuthRequest request = new OAuthRequest(Verb.POST, Constants.url.getPostUrl(mStatus, mImageId));

        Token accessToken = new Token("826512654596505600-ONEa50YhJbtqO7f5Z3zCSSXJ2pbiSsY",
                "0pq9hmYoRIIopJlDLSAtYcozYjd84PfUHi6gMdYFEVvNc"); //not required for context.io
        service.signRequest(accessToken, request);

        mResponse = request.send();
        Log.d("OAuthTask", mResponse.getBody());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mStatusUpdateListener.onResponse(mResponse);
    }
}
