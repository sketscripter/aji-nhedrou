package com.example.ajinhedrou.login.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajinhedrou.login.MainActivity;
import com.example.ajinhedrou.login.R;
import com.example.ajinhedrou.login.User;
import com.example.ajinhedrou.login.item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
import java.util.Date;
import java.util.List;

public class SignUpFragment extends Fragment implements OnSignUpListener, View.OnClickListener {
    private static final String TAG = "SignUpFragment";
    Button mRegisterbtn,changeh,changef;
    EditText emailL, passwordL, passwordconf;
    TextView mLoginPageBack;
    String Email, Password, PasswordConf;
    ProgressDialog mDialog;
    FirebaseAuth mAuth;
    private int buttonStateh = 1;
    private int buttonStatef = 1;
    DatabaseReference mdatabase;
    private List<item> itemList;
    private ListView listView;



    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_signup, container, false);
        emailL = (EditText) inflate.findViewById(R.id.editEmailL);
        passwordL = (EditText) inflate.findViewById(R.id.editPasswordL);
        mRegisterbtn = (Button) inflate.findViewById(R.id.buttonRegisterL);
        changef = (Button) inflate.findViewById(R.id.changefemme);
        changeh = (Button) inflate.findViewById(R.id.changehomme);
        passwordconf = (EditText) inflate.findViewById(R.id.editPasswordconf);
        mRegisterbtn.setOnClickListener(this);
        mDialog = new ProgressDialog(SignUpFragment.super.getContext());
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        //mStorageRef = FirebaseStorage.getInstance().getReference("defaultItems");
        FirebaseUser mUser;
        itemList = new ArrayList<>();
        listView = (ListView)inflate.findViewById(R.id.list_view);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();
        changeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonStateh % 2 == 0)
                {
                    changeh.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    buttonStateh++;
                }
                if(buttonStatef % 2 == 0)
                {
                    changef.setBackgroundColor(getResources().getColor(R.color.white));
                    buttonStatef++;
                }


            }
        });
        return inflate;
    }

    @Override
    public void signUp() {
        Toast.makeText(getContext(), "Sign up", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        if (v == mRegisterbtn) {
            UserRegister();
        } else if (v == mLoginPageBack) {

        }
        if( v == changef){
            changef.setBackgroundColor(getResources().getColor(R.color.white));
        }

    }


    private void UserRegister() {

        Email = emailL.getText().toString().trim();
        Password = passwordL.getText().toString().trim();
        PasswordConf = passwordconf.getText().toString().trim();


        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(SignUpFragment.super.getContext(), "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Password)) {
            Toast.makeText(SignUpFragment.super.getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        } else if (Password.length() < 6) {
            Toast.makeText(SignUpFragment.super.getContext(), "Password must be greater then 6 digit", Toast.LENGTH_SHORT).show();
            return;
    } else if (!PasswordConf.equals(Password)){
            Toast.makeText(SignUpFragment.super.getContext(), "Password must be the same", Toast.LENGTH_SHORT).show();
            return;
        }
        mDialog.setMessage("Creating User please wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    sendEmailVerification();
                    mDialog.dismiss();
                    OnAuth(task.getResult().getUser());
                    mAuth.signOut();
                } else {
                    Toast.makeText(SignUpFragment.super.getContext(), "error on creating user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void OnAuth(FirebaseUser user) {
        createAnewUser(user.getUid());
    }
    private void createAnewUser(String uid) {
        User user = BuildNewuser(uid);
        mdatabase.child(uid).setValue(user);
    }
    private User BuildNewuser(String id){
        return new User(
                "Zakaria",
                getUserEmail(),
                new Date().getTime(),
                id

        );
    }




        private void sendEmailVerification() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpFragment.super.getContext(), "Check your Email for verification", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                        }
                    }
                });
            }
        }


    public String getUserEmail() {
        return Email;
    }
    }

