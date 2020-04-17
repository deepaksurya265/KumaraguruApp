package com.wiates.kumaraguru;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class SignupActivity extends AppCompatActivity {
    EditText name,email,password,mobile;
    CircularProgressButton register;
    TextView already_have_account;
    private FirebaseAuth firebaseAuth;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        changeStatusBarColor();

        name = findViewById(R.id.name);
        users = FirebaseDatabase.getInstance().getReference("Users");
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mobile = findViewById(R.id.mobile);
        register = findViewById(R.id.RegisterButton);
        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(view -> {

            if(TextUtils.isEmpty(name.getText().toString())){
                Toast.makeText(getApplicationContext(),"Please enter your Name",Toast.LENGTH_LONG).show();
            }
            else if(TextUtils.isEmpty(email.getText().toString())){
                Toast.makeText(getApplicationContext(),"Please enter your Email ID",Toast.LENGTH_LONG).show();
            }
            else if(TextUtils.isEmpty(mobile.getText().toString())){
                Toast.makeText(getApplicationContext(),"Please enter your Mobile Number",Toast.LENGTH_LONG).show();
            }
            else if(TextUtils.isEmpty(password.getText().toString())){
                Toast.makeText(getApplicationContext(),"Please enter your Password",Toast.LENGTH_LONG).show();
            }
            else{
                register.startAnimation();
                String emailid = email.getText().toString().trim();
                String passwd = password.getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(emailid, passwd)
                        .addOnCompleteListener(SignupActivity.this, task -> {
                            if (task.isSuccessful()) {
                                addCustomer();
                            } else {
                                Toast.makeText(SignupActivity.this, "There is some problem with the Registration! Error Code: "+task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                                register.stopAnimation();
                                register.revertAnimation();

                            }
                        });
            }
        });

    }



    private void addCustomer() {
        String namee = name.getText().toString().trim();
        String passwd = password.getText().toString().trim();
        String mob = mobile.getText().toString().trim();
        String emaill = email.getText().toString().trim();

        String id = FirebaseAuth.getInstance().getUid();
        Users cr = new Users(namee,emaill,mob,passwd);

        users.child(id).setValue(cr).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(getApplicationContext(),"You are registered Successfully!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
                register.stopAnimation();
            }
            else{
                Toast.makeText(getApplicationContext(),"There is some problem with the Registration! Error Code: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                register.stopAnimation();
                register.revertAnimation();
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }

}