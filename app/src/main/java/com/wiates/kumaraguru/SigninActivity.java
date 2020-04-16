package com.wiates.kumaraguru;

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


public class SigninActivity extends AppCompatActivity {

    EditText un;
    EditText pd;
    Button login;
    TextView reg;
    TextView forgotpwd;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        setContentView(R.layout.activity_signin);

        un=(EditText)findViewById(R.id.email);
        pd=(EditText)findViewById(R.id.password);
        reg=(TextView)findViewById(R.id.reg);
        login=(Button)findViewById(R.id.login);
        forgotpwd=(TextView)findViewById(R.id.forgotpwd);

        firebaseAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=un.getText().toString().trim();
                String password=pd.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(SigninActivity.this,"Please Enter your Email ID",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(SigninActivity.this,"Please Enter your Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length()<6){
                    Toast.makeText(SigninActivity.this,"Your Password is too short",Toast.LENGTH_SHORT).show();
                }
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent i1=new Intent(SigninActivity.this,MainActivity.class);
                                    startActivity(i1);
                                } else {
                                    Toast.makeText(SigninActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(SigninActivity.this,SignupActivity.class);
                startActivity(i1);
            }
        });


    }



    public void onLoginClick(View View){
        startActivity(new Intent(this,SignupActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

    }



}