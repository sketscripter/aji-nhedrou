package com.example.ajinhedrou.login;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ajinhedrou.login.List.EasyTransition;
import com.example.ajinhedrou.login.List.EasyTransitionOptions;
import com.example.ajinhedrou.login.List.SwipeRevealLayout;
import com.example.ajinhedrou.login.List.ViewBinderHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.v7.widget.RecyclerView.*;
import static com.example.ajinhedrou.login.R.layout.activity_buttons__page;
import static com.example.ajinhedrou.login.R.layout.cards;



public class MyAdapter extends ArrayAdapter<genre> {
    private final LayoutInflater mInflater;
    private final ViewBinderHelper binderHelper;
    private Context mContext;
    private List<genre> mUploads;
    private FloatingActionButton fab;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    Activity act;

    public MyAdapter(Context context, ArrayList<genre> objects, Activity act) {
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



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.from(mContext).inflate(cards, parent, false);
            holder = new ViewHolder();
           // holder.swipeLayout = (SwipeRevealLayout) convertView.findViewById(R.id.swipe_layout);
            holder.frontView = convertView.findViewById(R.id.front_layout);
            holder.textView = (TextView) convertView.findViewById(R.id.textGenre);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageGenre);
            holder.list = convertView.findViewById(R.id.list_view_genre);

            mStorageRef = FirebaseStorage.getInstance().getReference("items");
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final genre genre = getItem(position);
        if (genre != null) {
            //binderHelper.bind(holder.swipeLayout,item.getName());
            holder.textView.setText(genre.getName());
            String url = genre.getImageUrl();
            Picasso.get()
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(holder.imageView);


           holder.frontView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id=genre.getGenreId();
                    Intent intent = new Intent(mContext, Buttons_Page.class);
                    intent.putExtra("ID", genre.getGenreId());
                    intent.putExtra("Im",genre.getImageUrl());
                    intent.putExtra("nom",genre.getName());
                    Pair[] p = new Pair[2];
                    p[0] = new Pair<View, String>(holder.imageView, "imageTransition");
                    p[1] = new Pair<View, String>(holder.textView, "textTransition");
                    ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(act,
                            p);
                    act.startActivity(intent, option.toBundle());
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

    private class ViewHolder {
        View frontView;
        TextView textView;
        ImageView imageView;
        ListView list;
    }
}
