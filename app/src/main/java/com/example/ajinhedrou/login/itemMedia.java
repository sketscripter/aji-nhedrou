package com.example.ajinhedrou.login;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class itemMedia {
    private int position;
    private String url ;
    private MediaPlayer mediaplayer;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;


    public itemMedia() {
        //empty constructor needed
    }

    public itemMedia(String url){
        this.url = url;

    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int pos) {
        position = pos;
    }
    public MediaPlayer getMedia(String Url){
        mediaplayer = new MediaPlayer();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageRef = firebaseStorage.getReferenceFromUrl(Url);

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    // Download url of file
                    mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    String url = uri.toString();
                    mediaplayer.setDataSource(url);
                } catch (IOException | IllegalStateException e) {
                    e.printStackTrace();
                }

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("TAG", e.getMessage());
                    }
                });

        return mediaplayer;
    }

    public void setMedia(MediaPlayer media) {
        mediaplayer = media;
    }
}
