package com.example.nikita.m1croblog.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikita.m1croblog.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailField);
        password = findViewById(R.id.passwordField);

        findViewById(R.id.signInButton).setOnClickListener(this);
        findViewById(R.id.signUpButton).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(AuthenticationActivity.this, RecordsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signInButton && email.getText().length() > 5 && password.getText().length() > 5) {
            signingIn(email.getText().toString(), password.getText().toString());
        } else if (v.getId() == R.id.signUpButton) {
            Intent intent = new Intent(AuthenticationActivity.this, SignUpActivity.class);
            startActivity(intent);
        } else Toast.makeText(AuthenticationActivity.this, "Incorrect input", Toast.LENGTH_SHORT).show();
    }

    public void signingIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(AuthenticationActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AuthenticationActivity.this, RecordsActivity.class);
                    startActivity(intent);
                }else Toast.makeText(AuthenticationActivity.this, "Authorization failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
