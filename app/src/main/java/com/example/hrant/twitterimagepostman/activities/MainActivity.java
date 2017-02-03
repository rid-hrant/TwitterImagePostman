package com.example.hrant.twitterimagepostman.activities;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hrant.twitterimagepostman.R;
import com.example.hrant.twitterimagepostman.utils.Constants;
import com.example.hrant.twitterimagepostman.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE_REQUEST = 1;
    private static final int CAPTURE_IMAGE_REQUEST = 2;
    private Button mSelectButton;
    private Button mCaptureButton;
    private Button mTempStatusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        selectButtonClk();
        tackButtonClk();
        tempStatusButtonClk();
    }

    private void tempStatusButtonClk() {
        mTempStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void tackButtonClk() {
        mCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePicture();
            }
        });
    }

    private void capturePicture() {

        Intent capturePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (capturePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(capturePictureIntent, CAPTURE_IMAGE_REQUEST);
        }
    }

    private void selectButtonClk() {
        mSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPicture();
            }
        });
    }

    private void findViews() {
        mSelectButton = (Button) findViewById(R.id.select_pic);
        mCaptureButton = (Button) findViewById(R.id.capture_pic);
        mTempStatusButton = (Button) findViewById(R.id.btn_temp_status);
    }

    private void selectPicture() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null) {

            final Uri uri = data.getData();
            final String pathFromURI = Utils.getPathFromURI(MainActivity.this, uri);
            startTweetActivity(pathFromURI);
        }

        if (requestCode == CAPTURE_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {


            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            final String pathFromURI = saveToInternalStorage(imageBitmap);
            startTweetActivity(pathFromURI);
        }
    }

    private void startTweetActivity(String path) {
        Intent intent = new Intent(MainActivity.this, TweetActivity.class);
        intent.putExtra(Constants.extra.ABSOLUTE_PATH, path);
        startActivity(intent);
    }


    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "tweet_image.png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

}
