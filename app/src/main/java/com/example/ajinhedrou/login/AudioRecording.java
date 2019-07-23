package com.example.ajinhedrou.login;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class AudioRecording {
    private DatabaseReference mDatabaseRef;
    private String mFileName;
    private Context mContext;
    private StorageReference mStorageRefVoice;
    private StorageTask mUploadTask;
    private MediaPlayer mMediaPlayer;
    private AudioListener audioListener;
    private MediaRecorder mRecorder;
    private String ur ;
    private long mStartingTimeMillis = 0;
    private long mElapsedMillis = 0;

    public AudioRecording(Context context) {
        mRecorder = new MediaRecorder();
        this.mContext = context;
    }
    public void save(String mF, StorageReference sr, final DatabaseReference dr){
        final StorageReference fileReference = sr.child(System.currentTimeMillis()
                + "" + "");

        Uri uriAudio = Uri.fromFile(new File(mF).getAbsoluteFile());
        mUploadTask = fileReference.putFile(uriAudio).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // getting audio uri and converting into string
                        String uploadId = dr.push().getKey();
                        dr.child(uploadId);
                        ur = uri.toString();
                    }
                });
            }
        });

    }
    public String getUrl(){
        return ur;
    }

    public AudioRecording() {
        mRecorder = new MediaRecorder();
    }

    public AudioRecording setNameFile(String nameFile) {
        this.mFileName = nameFile;
        return this;
    }

    public AudioRecording start(AudioListener audioListener) {
        this.audioListener = audioListener;

        try {

            mRecorder.reset();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            mRecorder.setOutputFile(mContext.getCacheDir() + mFileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mRecorder.prepare();
            mRecorder.start();
            mStartingTimeMillis = System.currentTimeMillis();
        } catch (IOException e) {
            this.audioListener.onError(e);
        }
        return this;
    }

    public void stop(Boolean cancel) {
        try {
            mRecorder.stop();
        } catch (RuntimeException e) {
            deleteOutput();
        }
        mRecorder.release();
        mElapsedMillis = (System.currentTimeMillis() - mStartingTimeMillis);

        RecordingItem recordingItem = new RecordingItem();
        recordingItem.setFilePath(mContext.getCacheDir() + mFileName);
        recordingItem.setName(mFileName);
        recordingItem.setLength((int)mElapsedMillis);
        recordingItem.setTime(System.currentTimeMillis());

        if (cancel == false) {
            audioListener.onStop(recordingItem);
        } else {
            audioListener.onCancel();
        }
    }

    private void deleteOutput() {
        File file = new File(mContext.getCacheDir() + mFileName);
        if (file.exists()) {
            file.delete();
        }
    }

    public void play() {

            this.mMediaPlayer = new MediaPlayer();
            //this.mMediaPlayer.setDataSource(mF);
            //this.mMediaPlayer.prepare();
            //this.mMediaPlayer.start();



    }

}
