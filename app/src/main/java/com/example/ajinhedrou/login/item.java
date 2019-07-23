package com.example.ajinhedrou.login;

import android.media.Image;

public class item {

    private String genreId;
    private String userId;
    private String itemName;
    private String ImageUrl;
    private String AudioUrl;

    public item() {
        //empty constructor needed
    }

    public item(String name, String imageUrl,String userId,String genreId,String AudioUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        this.genreId=genreId;
        this.userId=userId;
        this.itemName = name;
        this.ImageUrl = imageUrl;
        this.AudioUrl = AudioUrl;
    }

    public String getGenreId(){
        return  genreId;
    }


    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return itemName;
    }

    public void setName(String name) {
        itemName = name;
    }
    public String getUserId(){
        return userId;
    }
    public void setUserId(String id){
        userId = id;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
    public String getAudioUrl(){
        return AudioUrl;
    }
    public void setAudioUrl(String audio) {
        AudioUrl = audio;
    }
}
