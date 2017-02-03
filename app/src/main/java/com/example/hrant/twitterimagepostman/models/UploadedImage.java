package com.example.hrant.twitterimagepostman.models;

/**
 * Created by hrant on 2/2/17.
 */
public class UploadedImage {


    private long media_id;
    private String media_id_string;
    private long size;
    private long expires_after_secs;
    private Image image;


    private class Image {
        private String image_type;
        private int w;
        private int h;
    }

    public long getMedia_id() {
        return media_id;
    }

    public void setMedia_id(long media_id) {
        this.media_id = media_id;
    }

    public String getMedia_id_string() {
        return media_id_string;
    }

    public void setMedia_id_string(String media_id_string) {
        this.media_id_string = media_id_string;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getExpires_after_secs() {
        return expires_after_secs;
    }

    public void setExpires_after_secs(long expires_after_secs) {
        this.expires_after_secs = expires_after_secs;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "UploadedImage{" +
                "media_id=" + media_id +
                ", media_id_string='" + media_id_string + '\'' +
                ", size=" + size +
                ", expires_after_secs=" + expires_after_secs +
                ", image=" + image +
                '}';
    }
}
