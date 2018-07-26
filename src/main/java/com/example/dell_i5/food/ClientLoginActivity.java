package com.example.dell_i5.food;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;

public class ClientLoginActivity extends AppCompatActivity {

    private EditText email,password;
    private Button btn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);
        email=(EditText) findViewById(R.id.Lemail);
        password=(EditText) findViewById(R.id.Lpassword);
        btn=(Button) findViewById(R.id.loginBtn);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("users");

        progressBar=new ProgressDialog(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailField=email.getText().toString().trim();
                String passwordField=password.getText().toString().trim();
                if(!TextUtils.isEmpty(emailField) && !TextUtils.isEmpty(passwordField)){
                    progressBar.setMessage("Wait Checking the Credentials");
                    progressBar.show();
                    checkIfuserExits();
                }else{
                    Toast.makeText(ClientLoginActivity.this, "Please Enter The Email And Password ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkIfuserExits() {
        final String user_id=mAuth.getCurrentUser().getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(user_id)){
                        Intent intent=new Intent(ClientLoginActivity.this,MenuItemActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(ClientLoginActivity.this, "Please Check your credentials Or Internet connection", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
