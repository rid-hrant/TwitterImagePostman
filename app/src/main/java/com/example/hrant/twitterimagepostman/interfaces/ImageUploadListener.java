package com.example.hrant.twitterimagepostman.interfaces;

import com.example.hrant.twitterimagepostman.models.UploadedImage;

import java.io.IOException;

/**
 * Created by hrant on 2/2/17.
 */
public interface ImageUploadListener {

    void onResponse(UploadedImage uploadedImage);
    void onError(IOException e);
}
