package com.example.hrant.twitterimagepostman.requests;

import com.example.hrant.twitterimagepostman.interfaces.ImageUploadListener;
import com.example.hrant.twitterimagepostman.interfaces.UploadImageService;
import com.example.hrant.twitterimagepostman.models.UploadedImage;
import com.example.hrant.twitterimagepostman.utils.Constants;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by hrant on 2/2/17.
 */
public class ImageUploader {

    public static synchronized void upload(String fileAbsolutePath, final ImageUploadListener uploadListener) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        UploadImageService service = new Retrofit.Builder().baseUrl(Constants.url.BASE_URL)
                .client(client)
                .build()
                .create(UploadImageService.class);

        File file = new File(fileAbsolutePath);


        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("media", file.getName(), reqFile);

        retrofit2.Call<okhttp3.ResponseBody> req = service.postImage(body);
        req.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                if (response == null) {
                    System.out.println("image uploader response is null;");
                    return;
                }

                try {

                    String json = response.body().string() + "";
                    System.out.println("My json: " + json);
                    if (json != null && !json.equals("")) {
                        Gson gson = new Gson();
                        UploadedImage uploadedImage = gson.fromJson(json, UploadedImage.class);
                        uploadedImage.toString();
                        uploadListener.onResponse(uploadedImage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    uploadListener.onError(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
