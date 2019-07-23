package com.example.ajinhedrou.login;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ajinhedrou.login.List.SwipeRevealLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Buttons_Page extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ListAdapter adapter;
    private List<item> itemList;
    private SwipeRevealLayout swip_layout;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser, User;
    private DatabaseReference mDatabaseRef, mData;
    private ListView listView;
    private StorageReference mStorageRef;
    private item it2;
    private ListView layoutAbout;
    private ImageView front;
    private boolean finishEnter;
    private TextView nomT;
    private FloatingActionButton fab,fab2;
    float pixelDensity;
    ProgressBar pb;
    private UpdatedViewFrag updatedViewFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons__page);
        // holder.textViewName = findViewById(R.id.text);
        // holder.imageView = findViewById(R.id.imageItem);
        fab =findViewById(R.id.fab2);
        fab2 =findViewById(R.id.fab3);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("items");
        mData = FirebaseDatabase.getInstance().getReference("DefaultItems");
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        front = findViewById(R.id.iv_icon);
        nomT = findViewById(R.id.textGenre2);
        listView = (ListView) findViewById(R.id.list_view);
        swip_layout = (SwipeRevealLayout) findViewById(R.id.swipe_layout);
        itemList = new ArrayList<>();
        pb=(ProgressBar) findViewById(R.id.simpleProgressBar);
       // int maxValue=simpleProgressBar.getMax(); // get maximum value of the progress bar

        mStorageRef = FirebaseStorage.getInstance().getReference("defaultItems");
        String im= getIntent().getStringExtra("Im");
        String nom= getIntent().getStringExtra("nom");
        nomT.setText(nom);
        Picasso.get()
                .load(im)
                .fit()
                .centerCrop()
                .into(front);
        pixelDensity = getResources().getDisplayMetrics().density;
}



    int i = 0;

    @Override
    protected void onStart() {
        fab2.hide();
        pb.setVisibility(View.VISIBLE);



        super.onStart();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int preLast;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                switch(view.getId())
                {
                    case R.id.list_view:

                        final int lastItem = firstVisibleItem + visibleItemCount;
                        if(lastItem == totalItemCount)
                        {
                            if(preLast!=lastItem)
                            {
                                fab.hide();
                                fab2.show();
                            }
                        }
                        else{
                            fab.show();
                        fab2.hide();}
                }
            }
        });



        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User = FirebaseAuth.getInstance().getCurrentUser();
                String user = User.getUid();
                itemList.clear();
                for (DataSnapshot itemsnap : dataSnapshot.getChildren()) {
                    item it = itemsnap.getValue(item.class);
                    String id = getIntent().getStringExtra("ID");

                    if (it.getUserId().equals(user) && it.getGenreId().equals(id)) {
                        i++;
                        itemList.add(it);
                    }
                }
                if (i == 0) {
                    User = FirebaseAuth.getInstance().getCurrentUser();
                    String user1 = User.getUid();
                    if (getIntent().getStringExtra("ID").equals("1")) {

                        /*********************************** Sentiment ************************************************/
                        item item1 = new item("فرحان", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fhappy.png?alt=media&token=b3c6d617-e950-4976-b51c-59e608668308", user1, "1", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F%D9%81%D8%B1%D8%AD%D8%A7%D9%86.m4a?alt=media&token=71ec98f3-4af8-4f04-8ac4-ef30a7ff58ec");
                        item item2 = new item("حزين", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Funhappy.png?alt=media&token=95bd0156-b038-4fb5-8649-3e035984c786", user1, "1", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F%D8%AD%D8%B2%D9%8A%D9%86.m4a?alt=media&token=6efaac4b-8887-4eaa-b538-df984819c5c7");
                        item item3 = new item("عاجبني الحال", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fsatisfied.png?alt=media&token=21b0211f-cd44-469f-95b8-062ebde6f2df", user1, "1", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F%D8%A7%D9%84%D8%AD%D8%A7%D9%84.m4a?alt=media&token=4f1e9ebc-75fc-4de9-ab3b-84b2ddf096ea");
                        item item4 = new item("خايف", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fscared.png?alt=media&token=3391fa8e-3503-4c87-aac6-1472fb81fa9b", user1, "1", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F%D8%AE%D8%A7%D9%8A%D9%81.m4a?alt=media&token=96c0fe29-b675-402a-8b38-6f9881e1c17c");
                        item item5 = new item("داهش", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fsurprised.png?alt=media&token=c66d7a00-519e-406f-b05e-88442b4f4d71", user1, "1", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F%D9%85%D8%AF%D9%87%D8%B4%D8%B1.m4a?alt=media&token=262a05ea-db9b-4436-b83c-964a106b7686");
                        item item6 = new item("دايخ", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fdizzy.png?alt=media&token=6ba85f6f-3a96-4cc1-946e-6c9e9dbcae39", user1, "1", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F%D8%AF%D8%A7%D9%8A%D8%AE.m4a?alt=media&token=f9110373-6827-41e8-abe1-e8a8fcfc2320");
                        item item7 = new item("مكتاءب", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Finsomnia.png?alt=media&token=bae0dacb-eaaa-42d0-8806-3cf40ede3130", user1, "1", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F%D9%85%D9%83%D8%AA%D8%A7%D8%A1%D8%A8.m4a?alt=media&token=a56bf852-7314-4f0f-8a56-5ec6e4c5fc78");
                        item item8 = new item("مقلق", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fanxiety.png?alt=media&token=f86cfd7c-dbc2-4148-b54a-39134a231d1d", user1, "1", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F%D9%85%D9%82%D9%84%D9%82.m4a?alt=media&token=ab1909b7-db88-485e-8bb2-3d8861019d9f");
                        item item9 = new item("فيا السخانة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F11_besoin%2Ffever.png?alt=media&token=b169d157-4edb-4b96-b8e1-04d79346ed1a", user1, "1", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F11_besoin%2FVoix%20112.m4a?alt=media&token=1e0b1ae6-da6b-4212-9786-27732734a35f");

                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(item1);
                        String uploadId2 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId2).setValue(item2);
                        String uploadId3 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId3).setValue(item3);
                        String uploadId4 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId4).setValue(item4);
                        String uploadId5 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId5).setValue(item5);
                        String uploadId6 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId6).setValue(item6);
                        String uploadId7 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId7).setValue(item7);
                        String uploadId8 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId8).setValue(item8);
                        String uploadId9 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId9).setValue(item9);
                        itemList.add(item1);
                        itemList.add(item3);
                        itemList.add(item2);
                        itemList.add(item4);
                        itemList.add(item5);
                        itemList.add(item6);
                        itemList.add(item7);
                        itemList.add(item8);
                        itemList.add(item9);
                    }
                    if (getIntent().getStringExtra("ID").equals("8")) {

                        /*********************************** vetement ************************************************/
                        item item1 = new item("بلغة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2Fbabouhes.jpg?alt=media&token=a79a4d2a-4b5a-4330-8e64-6ab0ce3b302a", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FBabbouche.m4a?alt=media&token=1496ec84-ac66-42fb-b9d6-42c44a696caa");
                        item item2 = new item("طاكية", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2Fbonnet.jpg?alt=media&token=f2159b8f-2982-49fa-9f3a-9708b20f5488", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FBonnet.m4a?alt=media&token=089d408c-cc28-4e79-862d-3223c33e66c8");
                        item item3 = new item(" كاسكطة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2Fcasquette.jpg?alt=media&token=8fe76368-04a1-4c6f-bf5c-0e2af356d2ac", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FCasquette.m4a?alt=media&token=5a41805c-2cb0-4494-9faf-58e6940e59ca");
                        item item4 = new item("سمطة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2Fceinture.png?alt=media&token=7af7eb5b-a903-4d21-9133-5d6c2e48e666", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FCeinture.m4a?alt=media&token=b93720f7-a483-4a2c-97cf-dc22f78aaca5");
                        item item5 = new item("تقاشر", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2Fchaussette.jpg?alt=media&token=56ed150f-9ae2-4f60-9a9d-6c7920bfa614", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FChausette.m4a?alt=media&token=3cbb259d-32d8-4b3d-93f8-bcee5c342b34");
                        item item6 = new item("سباط", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2Fchaussures.jpg?alt=media&token=872a290c-c57f-40b4-abb5-a18e9b8525f7", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FChaussure.m4a?alt=media&token=85446254-4535-4eb8-ac75-c9e330a135ea");
                        item item7 = new item("قميجة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2Fchemise.png?alt=media&token=6b6df42b-030b-4d04-a4d7-170b2b225736", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FChemise.m4a?alt=media&token=96c3ed1f-a089-4f0e-992e-2d166912f617");
                        item item8 = new item("فولار", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2Ffoulard.jpg?alt=media&token=c0c8e9a5-fefd-4a10-9fba-a20d19db25f5", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FFoulard.m4a?alt=media&token=651f5587-41a9-4b87-8a97-8254b2575677");
                        item item10 = new item("كندورة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2Fgandoura.jpg?alt=media&token=5c8e7d08-d676-4c8a-9327-45ae5b6dc783", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FGandora.m4a?alt=media&token=c3ff35e0-e9bc-44d8-971d-e1384d523aa1");
                        item item11= new item("جلابة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2Fgandoura.jpg?alt=media&token=5c8e7d08-d676-4c8a-9327-45ae5b6dc783", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FJelaba.m4a?alt=media&token=5861a89a-07a2-4171-8230-9f91fca37272");
                        item item12= new item("مونطو", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2Fdoudoune.jpg?alt=media&token=da0bb475-4be8-4890-aa00-2606e6afeacd", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FManteau.m4a?alt=media&token=ee6f0022-efcc-49b3-b483-d3cd6a59bf89");
                        item item13= new item("سروال", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2Fpantalon.jpg?alt=media&token=a2a1e13c-6604-4b9c-8763-745b57511c1d", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FPantalon.m4a?alt=media&token=831fb1f1-7125-49fb-9bab-914c8a8b1f91");
                        item item14= new item("بينوار", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2FPeignoir.png?alt=media&token=61f629fc-9c80-4817-ad06-fbd744ea3595", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FPeinoire.m4a?alt=media&token=3e8f4336-8def-4596-995e-db7d4eb7403c");
                        item item15= new item("كبوط", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2Fpull.jpg?alt=media&token=04492226-3d54-486d-9d90-a2bec3176b3e", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FPull.m4a?alt=media&token=5d46db61-048e-44cc-a0b9-124b5e0d90fe");
                        item item16= new item("بيجاما", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2FVetement%2Fpyjama.jpg?alt=media&token=6941e784-05bd-423b-ba2b-ab8fadc3af0f", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FPyjama.m4a?alt=media&token=2ee4f5cb-c506-427d-9a8b-aed51498d943");
                       // item item17= new item("كبوط نص كم", "", user1, "8", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fvetement%2FT%20shirt.m4a?alt=media&token=495d9e06-2561-4792-8d30-c5c141c4723d");
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(item1);
                        String uploadId2 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId2).setValue(item2);
                        String uploadId3 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId3).setValue(item3);
                        String uploadId4 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId4).setValue(item4);
                        String uploadId5 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId5).setValue(item5);
                        String uploadId6 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId6).setValue(item6);
                        String uploadId7 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId7).setValue(item7);
                        String uploadId8 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId8).setValue(item8);
                        String uploadId10 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId10).setValue(item10);
                        String uploadId11 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId11).setValue(item11);
                        String uploadId12 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId12).setValue(item12);
                        String uploadId13= mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId13).setValue(item13);
                        String uploadId14 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId14).setValue(item14);
                        String uploadId15 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId15).setValue(item15);
                        String uploadId16 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId16).setValue(item16);
                        itemList.add(item1);
                        itemList.add(item3);
                        itemList.add(item2);
                        itemList.add(item4);
                        itemList.add(item5);
                        itemList.add(item6);
                        itemList.add(item7);
                        itemList.add(item8);

                        itemList.add(item10);
                        itemList.add(item11);
                        itemList.add(item13);
                        itemList.add(item12);
                        itemList.add(item14);
                        itemList.add(item15);
                        itemList.add(item16);

                    }
                    if (getIntent().getStringExtra("ID").equals("7")) {

                        /*********************************** local ************************************************/
                        item item13 = new item("بيتي", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F11_besoin%2Froom.png?alt=media&token=5a05135d-6d24-4bf4-aecb-b9cde475848e", user1, "7", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F11_besoin%2FVoix%20111.m4a?alt=media&token=5824bb73-559f-42ae-96d6-94123cd9e5d9");
                        item item1 = new item("بنكة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fbanque.jpg?alt=media&token=645b400e-9f69-4dcf-a726-ab81bc63df9a", user1, "7", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Flocal%2Fbanka.m4a?alt=media&token=ff15b429-5728-442c-aafc-1283cda94277");
                        item item2 = new item("الجامع", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fmosqu%C3%A9e.jpg?alt=media&token=c92e6e15-2896-445d-b532-1985241fa798", user1, "7", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Flocal%2Fmosque.m4a?alt=media&token=35d6d4d4-352c-4029-a3ec-f973b485853d");
                        item item3 = new item("البحر", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fplage.jpg?alt=media&token=c41ad970-2e97-45fa-b60b-621244751c01", user1, "7", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Flocal%2Fplage.m4a?alt=media&token=8f1975da-0277-48fc-85de-fdfff875d40f");
                        item item5 = new item("طبيب", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fmedcin.jpg?alt=media&token=bc0c147c-5c9c-48f4-a2c9-57c7422ca32b", user1, "7", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Flocal%2Fdocteur.m4a?alt=media&token=499584d4-ae69-46c4-beda-6f24d8f3b2a5");
                        item item6 = new item("قهوة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fcaf%C3%A9%20(1).png?alt=media&token=34c00933-98d4-4ba3-9b3d-16d3ea5adf7d", user1, "7", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Flocal%2Fcafe.m4a?alt=media&token=654385f0-00b2-4937-8bf5-e064e9cbc767");
                        item item4 = new item("حمام", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Flocal%2Fhammam.jpg?alt=media&token=abd372ac-9f96-4010-873d-092a61e4cef3", user1, "7", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Flocal%2Fhammam.m4a?alt=media&token=7d19be0b-8aa6-4a64-b445-043e1f7c7316");
                        item item7 = new item("الترويض", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Flocal%2Fkin%C3%A9.jpg?alt=media&token=60c289ea-fc15-40a4-84fe-229869db3dcb", user1, "7", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Flocal%2Fkine.m4a?alt=media&token=a4b5d875-e8af-49e6-82be-0e37bc8792d3");
                        item item8 = new item("المدينة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Flocal%2Fmedina.jpg?alt=media&token=2b10dc40-8465-45a8-8601-0667504a9859", user1, "7", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Flocal%2Fmdina.m4a?alt=media&token=fc457399-b1d0-44e9-be8f-89a2d22dcc20");
                        item item9 = new item("الغابة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Flocal%2Ffor%C3%AAt.jpg?alt=media&token=869b8d44-14b8-4248-96e6-88bdebf241b0", user1, "7", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Flocal%2Fforet.m4a?alt=media&token=868341c3-b52f-4ad4-95c3-7d7ea70fe3c0");
                        item item10 = new item("نتقدى", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Flocal%2Fcentre%20commercial.jpg?alt=media&token=5282c3a7-23a8-4444-9137-b4c91afbd7c0", user1, "7", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Flocal%2Fnt9eda.m4a?alt=media&token=2dcb2f20-9e99-48d6-9620-aabdea602a2a");
                        item item11 = new item("كوافور", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Flocal%2Fcoiffeur.jpg?alt=media&token=0874ec65-937f-4e26-989f-1734f08e45d8", user1, "7", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Flocal%2Fcoiffeur.m4a?alt=media&token=077c37cc-9fbd-4e10-980e-bb8887224f17");
                        item item12 = new item("كيشي", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Flocal%2Fghuichet%20automatique.jpg?alt=media&token=d4a680ca-5eae-4cfd-8335-041bee256533", user1, "7", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Flocal%2Fbanque.m4a?alt=media&token=8e1781f1-9d94-4e65-a31f-95b8f5f296a3");
                        String uploadId0 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId0).setValue(item13);
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(item1);
                        String uploadId2 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId2).setValue(item2);
                        String uploadId3 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId3).setValue(item3);
                        String uploadId4 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId4).setValue(item4);
                        String uploadId5 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId5).setValue(item5);
                        String uploadId6 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId6).setValue(item6);
                        String uploadId7 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId7).setValue(item7);
                        String uploadId8 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId8).setValue(item8);
                        String uploadId9 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId9).setValue(item9);
                        String uploadId10 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId10).setValue(item10);
                        String uploadId11 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId11).setValue(item11);
                        String uploadId12 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId12).setValue(item12);

                        itemList.add(item1);
                        itemList.add(item3);
                        itemList.add(item2);
                        itemList.add(item4);
                        itemList.add(item5);
                        itemList.add(item6);
                        itemList.add(item7);
                        itemList.add(item8);
                        itemList.add(item9);
                        itemList.add(item10);
                        itemList.add(item11);
                        itemList.add(item12);
                        itemList.add(item13);

                    }
                    if (getIntent().getStringExtra("ID").equals("6")) {

                        /*********************************** Famille ************************************************/
                        item item1 = new item("الوالدين", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/family%2Fparents.jpg?alt=media&token=844cbd16-dc8c-42ed-8a76-188bb9da453e", user1, "6", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2FParent.m4a?alt=media&token=5a187e6c-7fc0-44b3-ba2a-2d1c027715ea");
                        // item item2 = new item("اجداد", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/family%2Fgrands%20parents.jpg?alt=media&token=8d6e79b1-b641-45a0-86c7-c0f6ecd0cd8b", user1, "6", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fwalid.opus?alt=media&token=dd4a8131-e084-4339-8ae0-64f857fcc838");
                        //item item3 = new item("زوجي", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/family%2Fepoux.jpg?alt=media&token=6a1c293c-e7b2-4da8-bdf6-8dc933c9556d", user1, "6", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Frajli.opus?alt=media&token=3cd33e27-f6c9-4bb2-a71f-76e696d5ab4a");
                        item item4 = new item("أولاد", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/family%2Fenfants.jpg?alt=media&token=90fa3b64-76be-4e2b-b942-6c4dd8675441", user1, "6", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F1_famille%2F1_enfant.m4a?alt=media&token=9873ace1-8204-4521-b3fe-ce9995afbd60");
                        item item5 = new item("زوجتي", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/family%2Fepouse.png?alt=media&token=dfac7c3b-643f-4e66-b1ba-3308ae573159", user1, "6", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fmrati.opus?alt=media&token=95a621e5-0e29-47c8-9278-e9d4d66d4124");
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(item1);
                        // String uploadId2 = mDatabaseRef.push().getKey();
                        // mDatabaseRef.child(uploadId2).setValue(item2);
                       // String uploadId3 = mDatabaseRef.push().getKey();
                       // mDatabaseRef.child(uploadId3).setValue(item3);
                        String uploadId4 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId4).setValue(item4);
                        String uploadId5 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId5).setValue(item5);
                        itemList.add(item1);
                        // itemList.add(item3);
                        //  itemList.add(item2);
                        itemList.add(item4);
                        itemList.add(item5);
                    }
                    if (getIntent().getStringExtra("ID").equals("4")) {

                        item item1 = new item("بغيت ندوش", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fbesoin%2Fprendre%20une%20douche.jpeg?alt=media&token=1676b67e-ecf2-49bc-a5a4-4e65bda5bdeb", user1, "4", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F11_besoin%2F2_douche.m4a?alt=media&token=6a494b1b-610a-494f-929d-4aed0284bf6f");
                        item item2 = new item("بغيت ندخل طواليط", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fbesoin%2Ftoilette.jpg?alt=media&token=81625b96-0ffc-4622-97e8-7aa7b71768c8", user1, "4", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F11_besoin%2F2_toilette.m4a?alt=media&token=bb5e8d09-2352-4cff-a9bf-fe61d853e907");
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(item1);
                        String uploadId2 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId2).setValue(item2);
                        itemList.add(item1);
                        itemList.add(item2);
                    }
                    if (getIntent().getStringExtra("ID").equals("3")) {

                        /*********************************** cuisine ************************************************/
                        item item1 = new item("فرشيطة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fcuisine%2Ffourchette.jpg?alt=media&token=0bd25490-bc8a-4c61-8af0-9da888633cb7", user1, "3", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F11_forchete%2F4_frchita.m4a?alt=media&token=cc2ffe2f-aa0c-4e29-a618-26402d486000");
                        item item3 =new item("كاس", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fcuisine%2Fverre.png?alt=media&token=2a7658b4-e406-42ae-90bc-7cd226f42a75", user1, "3", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F11_forchete%2F4_kass.m4a?alt=media&token=68793f0c-80bb-4c0f-a53d-c2857f5dd64b");
                        item item4= new item("معلقة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fcuisine%2Fcuill%C3%A8re.jpg?alt=media&token=53aecb1a-9cfc-4404-accc-3a250babcfbd", user1, "3", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F11_forchete%2F4_m3el9a.m4a?alt=media&token=d9cbe7a2-b7f9-498a-bc2f-77252109c282");
                        item item5= new item("موس", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fcuisine%2Fcouteau.jpg?alt=media&token=f36c9e61-b60e-4c32-9fa8-2f602ebcce15", user1, "3", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F11_forchete%2F4_mouss.m4a?alt=media&token=b2b99319-c296-4b48-8f68-9bcc37a9f985");
                        item item6= new item("طبسيل", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fcuisine%2Fassiette.jpg?alt=media&token=66847057-d636-4b20-8874-1880ab9801a4", user1, "3", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F11_forchete%2F4_tebssil.m4a?alt=media&token=39a76657-2916-4457-ad19-0700c534c43b");
                        item item7= new item("زلافة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fcuisine%2Fbol.jpg?alt=media&token=b9d12b22-4607-4dcb-b2eb-60d3cdf2df8c", user1, "3", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F11_forchete%2F4_zelafa.m4a?alt=media&token=f150f315-8756-4331-b053-f10ba9cd8a64");
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(item1);
                        String uploadId2 = mDatabaseRef.push().getKey();
                         mDatabaseRef.child(uploadId2).setValue(item6);
                        String uploadId3 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId3).setValue(item3);
                        String uploadId4 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId4).setValue(item4);
                        String uploadId5 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId5).setValue(item5);
                        String uploadId6 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId6).setValue(item7);
                        itemList.add(item1);
                        itemList.add(item3);
                        itemList.add(item4);
                        itemList.add(item5);
                        itemList.add(item6);
                        itemList.add(item7);
                    }
                    if(getIntent().getStringExtra("ID").equals("10")){
                        item item1 = new item("قهوة حليب", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fcaf%C3%A9%20au%20lait.jpg?alt=media&token=4033405d-0323-4b85-9b2d-62b33bbd750f", user1, "10", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2FCafe%20au%20lait.m4a?alt=media&token=e3d37507-245d-47d0-b5ea-ce7d13f2a1d6");
                        item item2 = new item("قهوة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fcaf%C3%A9.png?alt=media&token=925f74f2-f152-454a-8b35-f03110c0f0cc", user1, "10", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2FCafe%C3%A9.m4a?alt=media&token=e3af31ec-d78b-4d1a-9770-37ba1c5486cc");
                        String uploadId6 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId6).setValue(item1);
                        String uploadId7 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId7).setValue(item2);
                        itemList.add(item1);
                        itemList.add(item2);
                    }
                    if (getIntent().getStringExtra("ID").equals("5")) {

                        /*********************************** nouriture ************************************************/
                        item item1 = new item("أناناس", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fananas.jpg?alt=media&token=a02ba1db-d9ca-438d-981f-8521a43962bd", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2FAnanas.m4a?alt=media&token=dfe2bd7c-bb79-4d2b-8377-b8b500e7ae21");
                        item item2 = new item("مشماش", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fabricot.jpg?alt=media&token=67165549-78db-45df-965b-f3567f6ce7d6", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2FMechmach.m4a?alt=media&token=71c8a40f-4de1-4bbc-ab2e-cb6d34990fe1");
                        item item3 = new item("أفوكا", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Favocat.jpg?alt=media&token=74eb877b-e223-4703-b9aa-4555e291a8f7", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2FAvocat.m4a?alt=media&token=d6ebc24d-e385-45a9-a6b7-61cb2f09690b");
                        item item4 = new item("بنان", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fbanane.jpg?alt=media&token=72855c0f-e2e0-4335-9e9b-7c26d77780f4", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2FBanane.m4a?alt=media&token=49d20119-9e82-4fdf-a9ad-25c512705064");
                        item item5 = new item("بغرير", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fbghrir.jpg?alt=media&token=11012476-595a-4f02-9fa6-7058a7aa92bc", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2FBeghrir.m4a?alt=media&token=4f368805-8832-4a8e-b8f1-c28e31164c4f");
                        item item6 = new item("قهوة حليب", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fcaf%C3%A9%20au%20lait.jpg?alt=media&token=4033405d-0323-4b85-9b2d-62b33bbd750f", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2FCafe%20au%20lait.m4a?alt=media&token=e3d37507-245d-47d0-b5ea-ce7d13f2a1d6");
                        item item7 = new item("قهوة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fcaf%C3%A9.png?alt=media&token=925f74f2-f152-454a-8b35-f03110c0f0cc", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2FCafe%C3%A9.m4a?alt=media&token=e3af31ec-d78b-4d1a-9770-37ba1c5486cc");
                        item item8 = new item("حب الملوك", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fcerise.jpg?alt=media&token=d4f97030-db3e-4cf3-80f7-5702982b135d", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2F7ebmlok.m4a?alt=media&token=9ed1c5f6-34d8-465c-95c7-b74ba9060fba");
                        item item9 = new item("كوفيتير", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fconfiture.jpg?alt=media&token=3b7add68-1860-4ff1-b058-5058457eb3ac", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2FConfiture.m4a?alt=media&token=b342cba5-abce-4b90-9108-38efaeb9a6f9");
                        item item10 = new item("كسكسو", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2FCouscous_.jpg?alt=media&token=384bca0b-08c0-427c-8ef7-759bb664cafc", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2FCouscous.m4a?alt=media&token=acb047fa-bb56-48d1-a31d-074015d0abd3");
                        item item11 = new item("كرواسا", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fcroissant.jpg?alt=media&token=21a1c638-565e-4bd4-82f0-9c2d2eb47887", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2FCroissant.m4a?alt=media&token=450c3166-0d5d-4051-a6d4-a0ab03acc1a1");
                        item item13 = new item("حوت مقلي", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Ffriture%20poisson%20(1).jpg?alt=media&token=e5c24744-e96f-44c0-a284-42db2f3d2a01", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_7out.m4a?alt=media&token=c1725c23-5a93-4f7e-b90a-4dac13396b38");
                        item item14 = new item("حريرة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fharira%20(1).jpg?alt=media&token=ef3b25f6-89ef-4bfb-9048-c43d3e70af88", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_7rira.m4a?alt=media&token=47173a62-511b-4116-8e9e-e4e15f4a54d4");
                        item item15 = new item("بسطيلة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fpastilla%20(1).jpg?alt=media&token=5f3c7f91-520e-43a9-b0ca-16a7dc8f2f15", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fbesstilla.m4a?alt=media&token=2db802dd-e3ea-4532-a364-87b6e16f6347");
                        item item16 = new item("بتيخ", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fmelon%20(1).jpg?alt=media&token=23935bc2-2753-4ae9-be4a-4f5a6b5c3034", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_bettikh.m4a?alt=media&token=168c6ea4-eaa5-4677-ae32-8d95c0c43685");
                        item item17 = new item("بزار", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fsel%20poivre.jpg?alt=media&token=641804da-98b2-4059-9ae2-d209dd019691", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_bezar.m4a?alt=media&token=f439b299-8f3a-45f9-8dce-43d3df35ddaa");
                        item item18 = new item("بوعويد", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fpoire%20(1).jpg?alt=media&token=668141f4-766a-4d06-a70a-69756021c38f", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_bou3wid.m4a?alt=media&token=5c2d00f0-c1f4-4ffa-88b1-73693bf3da37");
                        item item19 = new item("دلاح", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fpasth%C3%A9que%20(1).jpg?alt=media&token=8b423d59-515a-46a2-80b9-844e7040f6ed", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_dela7.m4a?alt=media&token=eabf2104-a207-4ab6-a7d3-d10f4807a2f3");
                        item item20 = new item("فريز", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Ffraise%20(1).jpg?alt=media&token=ce1d3856-da15-42de-89ff-9cd15a3d44cc", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_fraise.m4a?alt=media&token=459ed53a-a119-4b50-909d-5ce848b69f21");
                        item item21 = new item("فرماج", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Ffromage%20(1).jpg?alt=media&token=eebb1f91-685e-4be5-9d9b-f99e0997f896", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_fromage.m4a?alt=media&token=d6ea0b82-5340-4dc8-9315-5668dad2130d");
                        item item22 = new item("هندية", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2F%D8%A7%D8%AB%D9%89%D9%8A%D9%87%D8%B6.jpg?alt=media&token=e04f22f6-c048-497f-b6c9-c8f7311bde5c", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_hendia.m4a?alt=media&token=39a6b3bd-0bdf-4e38-bc54-ee59a52b61cd");
                        item item23 = new item("كرموس", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Ffigue%20(1).jpg?alt=media&token=ac54be9f-023e-4ade-8411-4fa80506b10a", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_kermoss.m4a?alt=media&token=3d47da0f-f05c-45d2-9cf5-4b9be6a39ce8");
                        item item24 = new item("خوخ", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fp%C3%AAche.jpg?alt=media&token=9c0dcb84-c79b-4c46-b052-e334e331389d", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_khoukh.m4a?alt=media&token=b98ff43a-efe4-4979-9242-97f05e0edcba");
                        item item25 = new item("كيوي", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fkiwi%20(1).jpg?alt=media&token=ffa5971d-c1d5-4bd4-b20a-a89268cc7998", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_kiwi.m4a?alt=media&token=a5bdaad7-bc82-423e-81f1-93b9c990a898");
                        item item26 = new item("العنب", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2F%D8%A7%D9%84%D8%B9%D9%86%D8%A8.jpg?alt=media&token=759e8b42-367a-4d15-8a02-8ace5d7814b1", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_l3neb.m4a?alt=media&token=8e442540-ad1a-42dc-8bf7-5d6b5ebc1430");
                        item item27 = new item("مونك", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fmangue%20(1).jpg?alt=media&token=ebecd9d6-1859-41ff-b99b-cb3eca799a49", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_mangue.m4a?alt=media&token=3046057a-680f-4fdc-9bc4-484636fd2818");
                        item item28 = new item("ملحا", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fsel%20poivre.jpg?alt=media&token=641804da-98b2-4059-9ae2-d209dd019691", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_mel7a.m4a?alt=media&token=71a049dd-0bec-43f1-8cd2-e07921f6b683");
                        item item29 = new item("مسمن", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fmsemen%20(1).jpg?alt=media&token=b7646549-836f-4a99-8e7e-b402a1929a30", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_msemen.m4a?alt=media&token=0632fb60-eebe-4aa2-b4f7-b4f31885293e");
                        item item30 = new item("ليمون", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Forange%20(1).jpg?alt=media&token=1ebcb132-c478-48ec-a0f5-0e0b5fd5a5b0", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_orange.m4a?alt=media&token=160a617d-e7ea-452c-be0a-c0f0066ca9b2");
                        item item31 = new item("الخبز", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fpain%20(1).jpg?alt=media&token=e43e77c6-c1fc-4265-823d-232b9aa1969f", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_pain.m4a?alt=media&token=fc84e87b-9bed-4b06-958f-17fd17e9b7c0");
                        item item32 = new item("بيتزا", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fpizza%20(1).jpg?alt=media&token=601f8b3f-0892-4626-9828-ae7b7cbbb9e0", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_pitza.m4a?alt=media&token=f586dfa9-1037-4501-80e3-df9d805f650c");
                        item item33 = new item("تفاح", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fpomme%20(1).jpg?alt=media&token=024af367-40bd-4814-abd5-1bfc7d627891", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_pomme.m4a?alt=media&token=4001db84-fccb-423c-aaf7-1117f05161aa");
                        item item34 = new item("رفيسة", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Frfissa.jpg?alt=media&token=f9d8ce91-5fa2-4e30-885e-0e851143943e", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_rfissa.m4a?alt=media&token=26d5bee7-3451-4e1b-8fee-84c5b51630f7");
                        item item35 = new item("سندويش", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fsandwich.jpg?alt=media&token=61dda1b6-b32e-444c-9509-bd53ab9aea4f", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F11_besoin%2FSandwich.m4a?alt=media&token=77845e88-bb84-496f-95e5-aa870d84d33d");
                        item item36 = new item("السكر", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fsukar.jpg?alt=media&token=97433b6f-25b3-4881-81d9-08c0bb00a421", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_sukar.m4a?alt=media&token=f4432c18-6a1a-4fe7-9d6e-a0f8c69925f0");
                        item item37 = new item("طجين", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Ftajine.jpg?alt=media&token=183b5945-2ed8-46ec-a9fb-465f2c6aac4c", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_tajine.m4a?alt=media&token=48c78651-4593-4fbd-ba11-44ad9a00f234");
                        item item38 = new item("الزيت", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fzit.jpg?alt=media&token=0950479e-5bad-4a9a-a5e2-e7d55bb2627b", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fnouriture%2Fa_nouriture%2Fa_zit.m4a?alt=media&token=50aae977-1861-4224-bb38-4f28e82e7f9a");
                        item item39 = new item("كاس العصير", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fverre%20de%20jus.jpg?alt=media&token=4af7b3ba-779f-436b-9f38-c4b16fdb7d59", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2FVoix%20077_sd.m4a?alt=media&token=0d4b7e02-908b-4687-9feb-019b222325dd");
                        item item40 = new item("كاس الماء", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2Fverred'eau.jpg?alt=media&token=b477cfd0-cc29-4b36-9a47-3a2d8b9e6a04", user1, "5", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fnouriture%2FVoix%20078_sd.m4a?alt=media&token=ee4a9f46-4799-4682-9ff7-8cad82674011");


                        String uploadId6 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId6).setValue(item6);
                        String uploadId7 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId7).setValue(item7);
                        String uploadId36 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId36).setValue(item36);
                        String uploadId39 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId39).setValue(item39);
                        String uploadId40 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId40).setValue(item40);
                        String uploadId5 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId5).setValue(item5);
                        String uploadId29 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId29).setValue(item29);
                        String uploadId11 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId11).setValue(item11);
                        String uploadId31 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId31).setValue(item31);
                        String uploadId21 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId21).setValue(item21);
                        String uploadId9 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId9).setValue(item9);
                        String uploadId38 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId38).setValue(item38);
                        String uploadId17 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId17).setValue(item17);
                        String uploadId28 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId28).setValue(item28);
                        String uploadId37 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId37).setValue(item37);
                        String uploadId34 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId34).setValue(item34);
                        String uploadId10 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId10).setValue(item10);
                        String uploadId15 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId15).setValue(item15);
                        String uploadId35 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId35).setValue(item35);
                        String uploadId32 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId32).setValue(item32);
                        String uploadId33 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId33).setValue(item33);
                        String uploadId30 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId30).setValue(item30);
                        String uploadId18 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId18).setValue(item18);
                        String uploadId16 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId16).setValue(item16);
                        String uploadId19 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId19).setValue(item19);
                        String uploadId22 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId22).setValue(item22);
                        String uploadId23 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId23).setValue(item23);
                        String uploadId26 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId26).setValue(item26);
                        String uploadId24 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId24).setValue(item24);
                        String uploadId20 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId20).setValue(item20);
                        String uploadId13 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId13).setValue(item13);
                        String uploadId14 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId14).setValue(item14);
                        String uploadId4 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId4).setValue(item4);
                        String uploadId25 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId25).setValue(item25);
                        String uploadId27 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId27).setValue(item27);
                        String uploadId8 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId8).setValue(item8);
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(item1);
                        String uploadId2 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId2).setValue(item2);

/****************************ftour***************************/
                        itemList.add(item6);
                        itemList.add(item7);
                        itemList.add(item36);
                        itemList.add(item39);
                        itemList.add(item40);
                        itemList.add(item5);
                        itemList.add(item29);
                        itemList.add(item11);
                        itemList.add(item31);
                        itemList.add(item21);
                        itemList.add(item9);
                        itemList.add(item38);
                        itemList.add(item17);
                        itemList.add(item28);
                        itemList.add(item37);
                        itemList.add(item34);
                        itemList.add(item10);
                        itemList.add(item15);
                        itemList.add(item35);
                        itemList.add(item32);
                        itemList.add(item13);
                        itemList.add(item14);
                        itemList.add(item4);
                        itemList.add(item33);
                        itemList.add(item30);
                        itemList.add(item18);
                        itemList.add(item16);
                        itemList.add(item19);
                        itemList.add(item22);
                        itemList.add(item23);
                        itemList.add(item26);
                        itemList.add(item24);
                        itemList.add(item20);
                        itemList.add(item3);
                        itemList.add(item25);
                        itemList.add(item27);
                        itemList.add(item8);
                        itemList.add(item1);
                        itemList.add(item2);


                    }
                    if (getIntent().getStringExtra("ID").equals("2")) {

                        /*********************************** argent ************************************************/
                        item item1 = new item("كارطة دلبنك", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fcarte%20bancaire.jpg?alt=media&token=a2fbb393-7d87-4e80-b977-965fbaa008ae", user1, "2", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fargent%2F3_carteguichet.m4a?alt=media&token=fa4fef61-f0c0-4b27-b7d0-1ee990cf7d34");
                       // item item2 = new item("فلوس", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/items%2Fpi%C3%A8ces%20et%20billets%20de%20banque.jpeg?alt=media&token=75cf6dc1-d76a-43a4-a77a-3956341302f0", user1, "2", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fwalid.opus?alt=media&token=dd4a8131-e084-4339-8ae0-64f857fcc838");
                        item item3 = new item("شيك", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fargent%2Fcheque.png?alt=media&token=a60da86c-e805-4e5b-a675-9b38f6c01d0d", user1, "2", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fargent%2F3_cheque.m4a?alt=media&token=0e1860f6-bad2-4f4c-9b28-4e385653f2ca");
                        item item4 = new item("1DH", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fargent%2F1DH.jpg?alt=media&token=845f2cb0-129c-476b-a33a-70dacd4afc1a", user1, "2", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fargent%2F1dhs.m4a?alt=media&token=a3d009aa-0caa-4e4d-8cf9-b07602bd622a");
                        item item5 = new item("2DH", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fargent%2F2-dh.jpg?alt=media&token=78f03887-6ffb-469e-9ce9-bd07ffbe77df", user1, "2", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fargent%2F2dhs.m4a?alt=media&token=72d0b02e-8f28-4e40-859f-0e02da728e97");
                        item item6 = new item("5DH", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fargent%2F5dh.jpg?alt=media&token=49801fef-ab10-4184-b619-c841612409cd", user1, "2", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fargent%2F3_5dh.m4a?alt=media&token=26f3335d-f22b-4ebc-8ee4-d55726fa6c90");
                        item item7 = new item("10DH", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fargent%2F10DH.jpg?alt=media&token=303a4622-58ea-4705-a758-f7bf8519972e", user1, "2", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fargent%2F3_10dh.m4a?alt=media&token=49eb8115-5e70-4264-b6f0-a131396cdaa5");
                        item item8 = new item("20DH", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fargent%2F20dh.jpg?alt=media&token=39a3522c-340a-437c-941b-f7c3fc1cb810", user1, "2", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fargent%2F3_20dh.m4a?alt=media&token=3aef67f9-f5e6-4b4d-98e6-accb8741e97b");
                        item item9 = new item("50DH", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fargent%2F50DH.jpg?alt=media&token=a7041c7d-e00b-4a07-9904-31f0bc790d41", user1, "2", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2F50.m4a?alt=media&token=1c586f75-beb9-45a0-9eeb-d082ed281e91");
                        item item10 = new item("100DH", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fargent%2F100dh.jpg?alt=media&token=7438e231-03cf-4e2b-9dc3-a0209eefc235", user1, "2", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fargent%2F3_100dh.m4a?alt=media&token=93c3e651-02bb-49d7-a6cb-be7e15f47894");
                        item item11= new item("200DH", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/defaultItems%2Fargent%2F200dh.jpg?alt=media&token=556fdb5c-ea05-4f84-a186-57256dc69c02", user1, "2", "https://firebasestorage.googleapis.com/v0/b/aji-nhedrou.appspot.com/o/voices%2Fargent%2F3_200dh.m4a?alt=media&token=061d125c-8550-48c4-894d-69330239162f");
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(item1);
                        //String uploadId2 = mDatabaseRef.push().getKey();
                        //mDatabaseRef.child(uploadId2).setValue(item2);
                        String uploadId3 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId3).setValue(item3);
                        String uploadId4 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId4).setValue(item4);
                        String uploadId5 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId5).setValue(item5);
                        String uploadId6 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId6).setValue(item6);
                        String uploadId7 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId7).setValue(item7);
                        String uploadId8 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId8).setValue(item8);
                        String uploadId9 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId9).setValue(item9);
                        String uploadId10 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId10).setValue(item10);
                        String uploadId11 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId11).setValue(item11);


                        itemList.add(item1);
                        itemList.add(item3);
                        //itemList.add(item2);
                        itemList.add(item4);
                        itemList.add(item5);
                        itemList.add(item6);
                        itemList.add(item7);
                        itemList.add(item8);
                        itemList.add(item9);
                        itemList.add(item10);
                        itemList.add(item11);

                    }
                }
                    ListAdapter adapter = new ListAdapter(Buttons_Page.this, itemList, Buttons_Page.this);
                    listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        pb.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (finishEnter) {
            finishEnter = false;
            startBackAnim();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Only if you need to restore open/close state when
        // the orientation is changed
        if (adapter != null) {
            adapter.saveStates(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Only if you need to restore open/close state when
        // the orientation is changed
        if (adapter != null) {
            adapter.restoreStates(savedInstanceState);
        }
    }

    public void retour(View view) {
        Intent intent = new Intent( Buttons_Page.this,Genre_Page.class);
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivity(intent);
    }
    public void onclick(View view) {
        Intent intent = new Intent( Buttons_Page.this,Edit_Activity.class);
        intent.putExtra("ID", getIntent().getStringExtra("ID"));
        intent.putExtra("Im",getIntent().getStringExtra("Im"));
        intent.putExtra("nom",getIntent().getStringExtra("nom"));
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivity(intent);
    }

    public class ImageViewHolder extends ArrayList<item> {
        public TextView textViewName;
        public ImageView imageView;

    }

    private void startBackAnim() {
        // forbidden scrolling
        ListView sv = (ListView) findViewById(R.id.list_view);
        sv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }
}
