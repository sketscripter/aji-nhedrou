package com.example.ajinhedrou.login;

public class ItemRecycler {
        public String Text;
        public int ImageUrl;

        ItemRecycler(String text, int imageurl) {
            this.Text = text;
            this.ImageUrl = imageurl;
        }

    public String getName() {
        return Text;
    }
}
