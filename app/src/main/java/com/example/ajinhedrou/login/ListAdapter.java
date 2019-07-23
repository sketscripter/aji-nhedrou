package com.example.ajinhedrou.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ajinhedrou.login.List.SwipeRevealLayout;
import com.example.ajinhedrou.login.List.ViewBinderHelper;
import com.example.ajinhedrou.login.item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.example.ajinhedrou.login.R.layout.row_list;

public class ListAdapter<progressCount> extends ArrayAdapter<item>  implements MediaPlayer.OnPreparedListener{
    private final LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper;
    private Context mContext;
    private List<item> mUploads;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    FirebaseStorage firebaseStorage;
    private MediaPlayer[] mediaPlayer;
    private MediaPlayer mp;
    private int[] pos;
    private SoundPool sound;
    ProgressBar pb;
    private Handler progressHandler = new Handler();
    Activity act;

    public ListAdapter(Context context, List<item> objects, Activity act) {
        super(context, R.layout.row_list,objects);
        this.mContext=context;
        this.mUploads=objects;
        this.mInflater = LayoutInflater.from(context);
        this.binderHelper = new ViewBinderHelper();
        this.act=act;
    }


    @Override
    public int getCount() {
        return mUploads.size();
    }
    int i = 0;
    int progressCount = 0;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.from(mContext).inflate(row_list, parent, false);
            holder = new ViewHolder();
            holder.swipeLayout = (SwipeRevealLayout) convertView.findViewById(R.id.swipe_layout);
            holder.frontView = convertView.findViewById(R.id.front_layout);
            holder.deleteView = convertView.findViewById(R.id.edit_layout);
            holder.textView = (TextView) convertView.findViewById(R.id.text);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageItem);
            mStorageRef = FirebaseStorage.getInstance().getReference("items");
            mediaPlayer = new MediaPlayer[getCount()];
            mp = new MediaPlayer();
             // initiate the progress bar
            firebaseStorage = FirebaseStorage.getInstance();


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final item item = getItem(position);

        if (item != null) {
            mStorageRef = firebaseStorage.getReferenceFromUrl(item.getAudioUrl());
            binderHelper.bind(holder.swipeLayout,item.getName());
            holder.textView.setText(item.getName());
            String url = item.getImageUrl();
            Picasso.get()
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(holder.imageView);
            final String audio = item.getAudioUrl();
            final itemMedia itemMedia = new itemMedia(audio);
            try{
                mediaPlayer[position] = itemMedia.getMedia(audio);
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
           // mediaPlayer[position].prepareAsync();
            holder.deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getContext(),Edit_Activity.class);
                    intent.putExtra("ID",item.getGenreId());
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    act.startActivity(intent);
                }
            });
            holder.frontView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {


                    if (mediaPlayer[position] == null) {
                        String displayText = " تم الظغط على " + item.getName();
                        Toast.makeText(getContext(), displayText, Toast.LENGTH_SHORT).show();
                        /*mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                try {
                                    // Download url of file
                                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    String url = uri.toString();
                                    mp.setDataSource(url);
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
                    try {
                        mp.prepareAsync();
                    }
                     catch (NullPointerException |IllegalStateException e) {
                        e.printStackTrace();
                    }
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }*/

                        try{
                                    mediaPlayer[position] = itemMedia.getMedia(audio);
                                }
                                catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                }try {
                            mediaPlayer[position].prepare();
                                } catch (IllegalStateException | NullPointerException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                        mediaPlayer[position].setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        mp.start();
                                try{
                                    mediaPlayer[position] = itemMedia.getMedia(audio);
                                }
                                catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else {
                        String displayText = " تم الظغط على " + item.getName();
                        Toast.makeText(getContext(), displayText, Toast.LENGTH_SHORT).show();
                        Log.d("ListAdapter", displayText);
                        /*mediaPlayer[position].setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                if (mediaPlayer[position].isPlaying())
                                    mediaPlayer[position].stop();
                                mediaPlayer[position].reset();
                            }
                        });*/
                        if(mediaPlayer[position].isPlaying()){
                            mediaPlayer[position].stop();
                            //mediaPlayer[position].reset();
                            try {
                                mediaPlayer[position].prepareAsync();
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                            mediaPlayer[position].setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mediaPlayer[position].start();
                                    try{
                                        mediaPlayer[position] = itemMedia.getMedia(audio);
                                    }
                                    catch (IllegalArgumentException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        else {
                            try {
                                    mediaPlayer[position].prepareAsync();
                                mediaPlayer[position].setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        mediaPlayer[position].start();
                                        try{
                                            mediaPlayer[position] = itemMedia.getMedia(audio);
                                        }
                                        catch (IllegalArgumentException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            });
        }
        return convertView;
    }
    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onSaveInstanceState(Bundle)}
     */
    public void saveStates(Bundle outState) {
        binderHelper.saveStates(outState);
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in {@link android.app.Activity#onRestoreInstanceState(Bundle)}
     */
    public void restoreStates(Bundle inState) {
        binderHelper.restoreStates(inState);
    }
    private void playmusic(int i){
                    mediaPlayer[i].start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        try {
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private class ViewHolder {
        SwipeRevealLayout swipeLayout;
        View frontView;
        View deleteView;
        TextView textView;
        ImageView imageView;
        ProgressBar pb;
    }

}
