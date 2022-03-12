package com.leadiing.isense;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText user_name, user_phone, user_email, user_password;
    AppCompatButton register_now_btn;
    TextView already_have_account_text;

    FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    String currentUserId;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Checking your credential! ");

        user_name = findViewById(R.id.user_name);
        user_phone = findViewById(R.id.user_phone);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);

        register_now_btn = findViewById(R.id.register_now_btn);
        already_have_account_text = findViewById(R.id.already_have_account_text);

        already_have_account_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
                finish();
            }
        });

        register_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = user_name.getText().toString().trim();
                String phone = user_phone.getText().toString().trim();
                String email = user_email.getText().toString().trim();
                String password = user_password.getText().toString().trim();

                if (name.isEmpty()) {
                    ShowAlert("Name field can not be empty");
                } else if (phone.isEmpty()) {
                    ShowAlert("Phone field can not be empty");
                } else if (email.isEmpty()) {
                    ShowAlert("Email field can not be empty");
                } else if (password.isEmpty()) {
                    ShowAlert("Password field can not be empty");
                } else if (password.length() < 6) {
                    ShowAlert("Password should be more than 6");

                } else {

                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressDialog.dismiss();

                            if (task.isSuccessful()) {

                                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                if (firebaseUser != null) {
                                    currentUserId = firebaseUser.getUid();
                                }

                                HashMap<String, String> userMap = new HashMap<>();
                                userMap.put("UserName", name);
                                userMap.put("UserPhone", phone);
                                userMap.put("UserEmail", email);
                                userMap.put("UserPassword", password);
                                userMap.put("UserId", currentUserId);


                                databaseReference.child(currentUserId).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this, "Register account successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
                                            finish();
                                        }
                                    }
                                });



                            } else {
                                String err = task.getException().getLocalizedMessage();
                                ShowAlert(err);
                            }
                        }
                    });
                }


            }
        });
    }

    private void ShowAlert(String s) {

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Error");
        builder.setMessage(s);
        builder.setIcon(R.drawable.ic_baseline_error_24);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}