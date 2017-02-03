package com.example.hrant.twitterimagepostman.interfaces;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by hrant on 2/2/17.
 */
public interface UploadImageService {
    @Headers("Authorization: OAuth oauth_consumer_key=\"FqzTbBjMU2v0Cv4fyFOarLD6i\",oauth_token=\"826512654596505600-ONEa50YhJbtqO7f5Z3zCSSXJ2pbiSsY\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1486075220\",oauth_nonce=\"Mz1sCQ\",oauth_version=\"1.0\",oauth_signature=\"c83gQ2Xm6iN%2FsIjC%2B7vrj59jv4A%3D\"")
    @Multipart
    @POST("/1.1/media/upload.json")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image);
}