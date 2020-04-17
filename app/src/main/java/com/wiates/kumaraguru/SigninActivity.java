package com.wiates.kumaraguru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class SigninActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    CircularProgressButton login;
    TextView register;
    TextView forgot_password;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.reg);
        login = findViewById(R.id.login);
        forgot_password = findViewById(R.id.forgotpwd);

        login.setOnClickListener(view -> {
            String email = username.getText().toString().trim();
            String passwd = password.getText().toString().trim();
            if (TextUtils.isEmpty(email)){
                Toast.makeText(SigninActivity.this,"Please enter your Email ID",Toast.LENGTH_SHORT).show();
                return;
            }
            else if (TextUtils.isEmpty(passwd)){
                Toast.makeText(SigninActivity.this,"Please enter your Password",Toast.LENGTH_SHORT).show();
                return;
            }
            else if (password.length()<6){
                Toast.makeText(SigninActivity.this,"Your password is too short",Toast.LENGTH_SHORT).show();
            }
            else {
                login.startAnimation();
                mAuth.signInWithEmailAndPassword(email, passwd)
                        .addOnCompleteListener(SigninActivity.this, task -> {
                            if (task.isSuccessful()) {
                                Intent intent=new Intent(SigninActivity.this,MainActivity.class);
                                login.stopAnimation();
                                startActivity(intent);
                                finish();
                            } else {
                                login.stopAnimation();
                                login.revertAnimation();
                                Toast.makeText(SigninActivity.this,"Sorry! The entered credentials were not correct! Please try again! Error Code: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
            }

        });

        register.setOnClickListener(view -> {
            Intent intent = new Intent(SigninActivity.this,SignupActivity.class);
            startActivity(intent);
        });
    }



    public void onLoginClick(View View){
        startActivity(new Intent(this,SignupActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
}