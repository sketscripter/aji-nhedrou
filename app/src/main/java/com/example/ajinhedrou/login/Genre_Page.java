package com.example.ajinhedrou.login;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.mbms.MbmsErrors;
import android.util.Pair;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ajinhedrou.login.List.EasyTransition;
import com.example.ajinhedrou.login.List.EasyTransitionOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Genre_Page extends AppCompatActivity {

public ArrayList<genre> list;
    public ListView listView;
    private ImageView mImageView;
    private FirebaseAuth mAuth;
    private Context mContext;
    private List<item> mUploads;
    public LinearLayout front;
    private Buttons_Page.ImageViewHolder holder;
    private FirebaseUser mUser, User;
    private DatabaseReference mDatabaseRef, mData;
    private TextView text;
    private ImageView image;
    private StorageReference mStorageRef;
    private item it2;
    private ListView layoutAbout;
    private ImageView ivAdd;
    private MediaPlayer player;
    private boolean finishEnter;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre__page);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("genres");
        mData = FirebaseDatabase.getInstance().getReference("genres");
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        listView = (ListView) findViewById(R.id.list_view_genre);
        front = (LinearLayout)findViewById(R.id.front_layout);
        mImageView = findViewById(R.id.imageGenre);
        text = findViewById(R.id.textGenre);
        list = new ArrayList<genre>();
        fab =findViewById(R.id.fab);
        mStorageRef = FirebaseStorage.getInstance().getReference("defaultItems");

    }

    int i = 0;
    @Override
    protected void onStart() {
        super.onStart();


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == 0){
                    fab.show();
                }

            }
            private int preLast;
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    switch(view.getId())
                    {
                        case R.id.list_view_genre:

                            // Make your calculation stuff here. You have all your
                            // needed info from the parameters of this function.

                            // Sample calculation to determine if the last
                            // item is fully visible.
                            final int lastItem = firstVisibleItem + visibleItemCount;

                            if(lastItem == totalItemCount)
                            {
                                if(preLast!=lastItem)
                                {
                                   fab.hide();
                                }

                            }
                            else{
                                fab.show();}
                    }
            }
        });


       /* mDatabaseRef.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                String uploadId = databaseReference.push().getKey();
                databaseReference.child(uploadId).removeValue();
            }
        });*/
            mDatabaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                    User = FirebaseAuth.getInstance().getCurrentUser();
                    String user = User.getUid();
                    list.clear();


                /*for(int k=0;k<17000;k++){
                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).removeValue();
                    k++;
                }*/
                    for (DataSnapshot itemsnap : dataSnapshot.getChildren()) {
                        genre g = itemsnap.getValue(genre.class);

                        if (g.getUserId().equals(user)) {
                            i++;
                            list.add(g);
                        }
                    }

                    if (i == 0) {
                        User = FirebaseAuth.getInstance().getCurrentUser();
                        String user1 = User.getUid();
                        genre genre5 = new genre("باش كانحس ؟", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultGenres%2Fsmiley.png?alt=media&token=8d1f7508-a0eb-4c12-9f60-800cb2118dc9", user1,"1");
                        genre genre6 = new genre("فلوسي", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultGenres%2Fcoins.png?alt=media&token=099642e9-283f-430d-9af3-e9df109287ba", user1,"2");
                        genre genre7 = new genre("باش غناكل ؟", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultGenres%2Frestaurant.png?alt=media&token=a4d99852-22ce-429d-ae79-a2fe0d3f0dcb", user1,"3");
                        genre genre8 = new genre("شنو كنحتاج ؟", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultGenres%2Fteamwork.png?alt=media&token=92112d2c-e5d9-4a95-ae1d-1257640aa338", user1,"4");
                        genre genre1 = new genre("شنو بغيت ناكل ؟", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fdiet.png?alt=media&token=7b882e76-1495-4669-94b5-e41c04139642", user1,"5");
                        genre genre2 = new genre("عائلتي ؟", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Ffamily.png?alt=media&token=2fd341cf-1d06-44fd-a47d-9100f57fde56", user1,"6");
                        //String uploadId = mDatabaseRef.push().getKey();
                        genre genre3 = new genre("فين بغيت نمشي ؟", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fplaceholder.png?alt=media&token=d30ac368-97bd-4dfc-9794-1b58ce86ca71", user1,"7");
                        //String uploadId = mDatabaseRef.push().getKey();
                        genre genre9 = new genre("صحابي ؟", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Ffriendship.png?alt=media&token=91cc2dbb-5bd5-4ed1-9875-68d0d52f8775", user1,"9");
                        genre genre4 = new genre("شنو بغيت نلبس ؟", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Flaundry.png?alt=media&token=63937930-9faf-468e-b713-839f94fd9227", user1,"8");

                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(genre1);
                        String uploadId2 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId2).setValue(genre2);
                        String uploadId3 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId3).setValue(genre3);
                        String uploadId4 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId4).setValue(genre4);
                        String uploadId5 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId5).setValue(genre5);
                        String uploadId6 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId6).setValue(genre6);
                        String uploadId7 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId7).setValue(genre7);
                        String uploadId8 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId8).setValue(genre8);
                        String uploadId9 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId9).setValue(genre9);
                        list.add(genre1);
                        list.add(genre2);
                        list.add(genre3);
                        list.add(genre4);
                        list.add(genre5);
                        list.add(genre6);
                        list.add(genre7);
                        list.add(genre8);
                        list.add(genre9);

                    }
                    MyAdapter adapter = new MyAdapter(Genre_Page.this, list, Genre_Page.this);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(Genre_Page.this, Buttons_Page.class);
                            genre genre = dataSnapshot.getValue(genre.class);

                            intent.putExtra("ID", genre.getGenreId());
                            Pair[] p = new Pair[2];
                            p[0] = new Pair<View, String>(mImageView, "imageTransition");
                            p[1] = new Pair<View, String>(text, "textTransition");
                            ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(Genre_Page.this,
                                    p);
                            startActivity(intent,option.toBundle());

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }


    public void logout_onclick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("متيقن بغيتي تخرج ؟");
        //builder.setMessage("you will logOut !!!");
        builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final Intent intent = new Intent(this,MainActivity.class);
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth.signOut();
                startActivity(intent);
            }
        });
        builder.show();

    }

    public void swip(View view) {
        /*Intent intent = new Intent(Genre_Page.this, Buttons_Page.class);

        startActivity(intent);*/
    }


    public void sber(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage("                      صبر معايا !!!   ");
        //builder.setIcon(R.drawable.stop);
        builder.show();
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.sber);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    // stopPlayer();
                }
            });
        }
        player.start();

    }
}
