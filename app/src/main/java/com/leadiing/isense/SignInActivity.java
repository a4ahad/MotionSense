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

public class SignInActivity extends AppCompatActivity {

    TextInputEditText user_name, user_phone, user_email, user_password;
    AppCompatButton sign_In_btn;
    TextView no_have_account_text;
    FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Checking your credential! ");

        user_email = findViewById(R.id.user_email_sign);
        user_password = findViewById(R.id.user_password_sign);

        sign_In_btn = findViewById(R.id.sign_In_btn);
        no_have_account_text = findViewById(R.id.no_have_account_text);

        no_have_account_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, RegisterActivity.class));
                finish();
            }
        });

        sign_In_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user_email.getText().toString().trim();
                String password = user_password.getText().toString().trim();


                if (email.isEmpty()) {
                    ShowAlert("Email field can not be empty");
                } else if (password.isEmpty()) {
                    ShowAlert("Password field can not be empty");
                } else if (password.length() < 6) {
                    ShowAlert("Password should be more than 6");

                } else {
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressDialog.dismiss();

                            if (task.isSuccessful()){
                                Toast.makeText( SignInActivity.this, "Welcome....", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                finish();
                            }else{
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

        AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
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