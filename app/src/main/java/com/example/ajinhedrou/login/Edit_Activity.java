package com.example.ajinhedrou.login;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Method;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Edit_Activity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Uri mImageUri,mAudioUri;
    private EditText mEditTextFileName;
    private StorageReference mStorageRef,mStorageRefVoice;

    private DatabaseReference mDatabaseRef,mDatabaseRefVoice;
    private StorageTask mUploadTask;
    private FirebaseUser mUser;
    private AudioRecordButton mAudioRecordButton;
    private AudioRecording audioRecording;
    private String mFileName=null;
    private String aud;
   // public static final int RECORD_AUDIO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_);
        imageView = findViewById(R.id.profile);
        mEditTextFileName = findViewById(R.id.name_photo);
        imageView.setImageResource(R.drawable.family);
        mStorageRef = FirebaseStorage.getInstance().getReference("items");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("items");
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        audioRecording = new AudioRecording(getBaseContext());
        mStorageRefVoice=FirebaseStorage.getInstance().getReference("voices");
        mDatabaseRefVoice = FirebaseDatabase.getInstance().getReference("voices");
        initView();
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO, READ_EXTERNAL_STORAGE},0);
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, 0);

        this.mAudioRecordButton.setOnAudioListener(new AudioListener() {
            @Override
            public void onStop(RecordingItem recordingItem) {
                Toast.makeText(getBaseContext(), "تسجيل الصوت", Toast.LENGTH_SHORT).show();
                audioRecording.save(recordingItem.getFilePath(),mStorageRefVoice,mDatabaseRefVoice);

            }

            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(), "مسح", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Log.d("Edit_Activity", "Error: " + e.getMessage());
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(imageView);
        }
    }



    private void initView() {
        this.mAudioRecordButton = (AudioRecordButton) findViewById(R.id.audio_record_button);
    }
    private void uploadFile() {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "" + "");
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //getting audio uri and converting into string
                                    item item = new item(mEditTextFileName.getText().toString().trim(),uri.toString().trim(),mUser.getUid(),getIntent().getStringExtra("ID"),audioRecording.getUrl().trim());
                                    String uploadId = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(uploadId).setValue(item);
                                }
                            });
                            Toast.makeText(Edit_Activity.this, "أضيف الزر بنجاح", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Edit_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        }
                    });
        } else {
            Toast.makeText(this, "لم تختر أي صورة", Toast.LENGTH_SHORT).show();
        }
    }

    public void onclick_change(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    public void onclick_upload(View view) {
        uploadFile();
        openImagesActivity();
    }

    private void openImagesActivity() {
        Intent intent = new Intent(this, Buttons_Page.class);
        intent.putExtra("ID",getIntent().getStringExtra("ID"));
        intent.putExtra("Im",getIntent().getStringExtra("Im"));
        intent.putExtra("nom",getIntent().getStringExtra("nom"));
        startActivity(intent);
    }
}
