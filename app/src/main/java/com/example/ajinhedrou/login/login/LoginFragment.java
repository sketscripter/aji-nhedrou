package com.example.ajinhedrou.login.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ajinhedrou.login.Buttons_Page;
import com.example.ajinhedrou.login.Genre_Page;
import com.example.ajinhedrou.login.MainActivity;
import com.example.ajinhedrou.login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment implements OnLoginListener{
    private static final String TAG = "LoginFragment";
    Button LogInButton;
    EditText Email, Password;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser mUser;
    String email, password;
    ProgressDialog dialog;
    String uid;
    public static final String userEmail="";

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View inflate = inflater.inflate(R.layout.fragment_login, container, false);
        LogInButton = (Button) inflate.findViewById(R.id.buttonLogin);
        Email = (EditText) inflate.findViewById(R.id.editEmail);
        Password = (EditText) inflate.findViewById(R.id.editPassword);
        dialog = new ProgressDialog(LoginFragment.super.getContext());
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        //uid = mUser.getUid();


        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mUser != null) {
                    Intent intent = new Intent(LoginFragment.super.getContext(), Genre_Page.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent,1);
                }
                else
                {
                    Log.d(TAG,"AuthStateChanged:Logout");
                }

            }
        };
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling EditText is empty or no method.
                userSign();

            }
        });

        inflate.findViewById(R.id.forgot_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginFragment.this.getContext(), "Forgot password clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return inflate;
    }
    private void userSign() {
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginFragment.super.getContext(), "Enter the correct Email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(LoginFragment.super.getContext(), "Enter the correct password", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.setMessage("Loging in please wait...");
        dialog.setIndeterminate(true);
        dialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(LoginFragment.super.getContext(), "Login not successfull", Toast.LENGTH_SHORT).show();

                } else {
                    
                    dialog.dismiss();
                    Intent intent = new Intent(LoginFragment.super.getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    checkIfEmailVerified();


                }
            }
        });

    }

     private void checkIfEmailVerified(){
            FirebaseUser users=FirebaseAuth.getInstance().getCurrentUser();
            boolean emailVerified=users.isEmailVerified();
            if(!emailVerified){
                Toast.makeText(LoginFragment.super.getContext(),"Verify the Email Id",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                LoginFragment.super.getActivity().finish();
            }
            else {
                Email.getText().clear();
                Password.getText().clear();
                Intent intent = new Intent(LoginFragment.super.getContext(),Genre_Page.class);
                // Sending Email to Dashboard Activity using intent.
                intent.putExtra(userEmail,email);
                startActivity(intent);

            }
        }




    @Override
    public void login() {
        Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
    }


}
