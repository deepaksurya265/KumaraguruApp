package com.wiates.kumaraguru;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {
    EditText name,e,pwd,mb;
    Button r;
    TextView already_have_account;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        changeStatusBarColor();

        name = (EditText) findViewById(R.id.name);
        databaseCustomer = FirebaseDatabase.getInstance().getReference("Customers");
        e = (EditText) findViewById(R.id.email);
        pwd = (EditText) findViewById(R.id.password);
        mb = (EditText) findViewById(R.id.mobile);
        r = (Button) findViewById(R.id.RegisterButton);
        firebaseAuth = FirebaseAuth.getInstance();

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomer();
                String email = e.getText().toString().trim();
                String password = pwd.getText().toString().trim();
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "You are registered successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignupActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            }

        });

    }

    private void addCustomer() {
        String firstname = name.getText().toString().trim();
        String password=pwd.getText().toString().trim();
        String mobile = mb.getText().toString().trim();
        String email=e.getText().toString().trim();
        if (!(TextUtils.isEmpty(firstname)) && !(TextUtils.isEmpty(password)) && !(TextUtils.isEmpty(mobile)) && !(TextUtils.isEmpty(email))) {
            String id = databaseCustomer.push().getKey();
            Customer cr = new Customer(id, firstname,email, mobile,password);
            databaseCustomer.child(id).setValue(cr);

            Toast.makeText(this,"Customer added",Toast.LENGTH_LONG).show();
        }
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view){
        startActivity(new Intent(this,SignupActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }

}