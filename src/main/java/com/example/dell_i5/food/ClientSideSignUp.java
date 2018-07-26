package com.example.dell_i5.food;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClientSideSignUp extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button signupbtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_side_sign_up);
        progressDialog=new ProgressDialog(this);
        email=(EditText) findViewById(R.id.emailField);
        password=(EditText) findViewById(R.id.passwordField);

        signupbtn=(Button) findViewById(R.id.signUp);

        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("users");

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailValue=email.getText().toString().trim();
                final String passwordValue=password.getText().toString().trim();

                if(!TextUtils.isEmpty(emailValue) && !TextUtils.isEmpty(passwordValue)){
                    progressDialog.setMessage("Wait :) Checking for Credentials");
                    progressDialog.show();
                        mAuth.createUserWithEmailAndPassword(emailValue,passwordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    String user_id=mAuth.getCurrentUser().getUid();
                                    DatabaseReference currentUser=databaseReference.child(user_id);
                                    currentUser.child("Name").setValue(emailValue);
                                    currentUser.child("password").setValue(passwordValue);
                                    progressDialog.hide();
                                }else{
                                    Toast.makeText(ClientSideSignUp.this, "Please Check your credentials Or Internet connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }else{
                    Toast.makeText(ClientSideSignUp.this, "Please Enter Email and Password ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
