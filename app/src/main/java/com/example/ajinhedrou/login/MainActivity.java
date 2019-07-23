package com.example.ajinhedrou.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajinhedrou.login.databinding.ActivityMainBinding;
import com.example.ajinhedrou.login.login.LoginFragment;
import com.example.ajinhedrou.login.login.SignUpFragment;
import com.example.ajinhedrou.login.login.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.ajinhedrou.login.FlexibleFrameLayout.ORDER_LOGIN_STATE;
import static com.example.ajinhedrou.login.FlexibleFrameLayout.ORDER_SIGN_UP_STATE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public ActivityMainBinding binding;
    private boolean isLogin = true;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    ProgressDialog dialog;
    DatabaseReference mdatabase;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        LoginFragment topLoginFragment = new LoginFragment();
        SignUpFragment topSignUpFragment = new SignUpFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sign_up_fragment, topSignUpFragment)
                .replace(R.id.login_fragment, topLoginFragment)
                .commit();
        binding.loginFragment.setRotation(-90);
        binding.button.setOnLoginListener(topLoginFragment);
        binding.button.setOnSignUpListener(topSignUpFragment);
        binding.button.setOnButtonSwitched(new OnButtonSwitchedListener() {
            @Override
            public void onButtonSwitched(boolean isLogin) {
                binding.getRoot()
                        .setBackgroundColor(ContextCompat.getColor(
                                MainActivity.this,
                                isLogin ? R.color.gris : R.color.white));
            }
        });

        binding.loginFragment.setVisibility(INVISIBLE);
        FirebaseApp.initializeApp(this);
        dialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {

            startActivity(new Intent(this, Genre_Page.class));
            finish();
        }
        mDialog = new ProgressDialog(this);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    }


    @Override
    protected void onStart() {
        super.onStart();
        //removeAuthSateListner is used  in onStart function just for checking purposes,it helps in logging you out.
        mAuth.removeAuthStateListener(mAuthListner);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }

    }

    @Override
    public void onBackPressed() {
        MainActivity.super.finish();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        binding.loginFragment.setPivotX(binding.loginFragment.getWidth() / 2);
        binding.loginFragment.setPivotY(binding.loginFragment.getHeight());
        binding.signUpFragment.setPivotX(binding.signUpFragment.getWidth() / 2);
        binding.signUpFragment.setPivotY(binding.signUpFragment.getHeight());
    }

    public void switchFragment(View v) {
        if (isLogin) {
            binding.loginFragment.setVisibility(VISIBLE);
            binding.loginFragment.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    binding.signUpFragment.setVisibility(INVISIBLE);
                    binding.signUpFragment.setRotation(90);
                    binding.wrapper.setDrawOrder(ORDER_LOGIN_STATE);
                }
            });
        } else {
            binding.signUpFragment.setVisibility(VISIBLE);
            binding.signUpFragment.animate().rotation(0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    binding.loginFragment.setVisibility(INVISIBLE);
                    binding.loginFragment.setRotation(-90);
                    binding.wrapper.setDrawOrder(ORDER_SIGN_UP_STATE);
                }
            });
        }

        isLogin = !isLogin;
        binding.button.startAnimation();
    }


}


