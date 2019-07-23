package com.example.ajinhedrou.login;

public class genre {
    private String userId;
    private String genreName;
    private String ImageUrl;
    private String genreId;

    public genre() {
        //empty constructor needed
    }

    public genre(String name, String imageUrl,String userId,String genreId) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        this.userId=userId;
        this.genreName = name;
        this.ImageUrl = imageUrl;
        this.genreId = genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public String getGenreId() {
        return genreId;
    }

    public String getName() {
        return genreName;
    }

    public void setName(String name) {
        genreName = name;
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
}
