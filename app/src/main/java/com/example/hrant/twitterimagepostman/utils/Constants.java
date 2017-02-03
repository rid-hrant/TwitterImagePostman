package com.example.hrant.twitterimagepostman.utils;

/**
 * Created by hrant on 2/2/17.
 */
public class Constants {

    private final static String POST_STATUS_URL = "https://api.twitter.com/1.1/statuses/update.json?status=%s&media_ids=%s";

    public static class extra {
        public static String ABSOLUTE_PATH = "ABSOLUTE_PATH";

    }

    public static class TwitterApi {
        public static String CONSUMER_KEY = "FqzTbBjMU2v0Cv4fyFOarLD6i";
        public static String CONSUMER_SECRET = "A4OrVb7tGJ8QnMCpS3t0jgZPKWCrNGQc4ZkZ2raoJH12xICKEJ"; //api secret

    }

    public static class url {

        public static String BASE_URL = "https://upload.twitter.com";

        public static String getPostUrl(String status, String imageId) {
            return String.format(POST_STATUS_URL, status, imageId);
        }

    }

}
