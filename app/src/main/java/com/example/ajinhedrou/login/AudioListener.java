package com.example.ajinhedrou.login;

public interface AudioListener {
    void onStop(RecordingItem recordingItem);

    void onCancel();

    void onError(Exception e);
}
