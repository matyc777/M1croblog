package com.example.nikita.m1croblog.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nikita.m1croblog.R;
import com.example.nikita.m1croblog.dataaccess.UserInfoDao;
import com.example.nikita.m1croblog.model.UserInformation;
import com.example.nikita.m1croblog.service.api.IUserInfoValidator;
import com.example.nikita.m1croblog.service.UserInfoValidator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;

    private EditText email;
    private EditText password;
    private EditText name;
    private EditText surname;
    private RadioButton gender;
    private Spinner ageSpinner;

    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailTextField);
        password = findViewById(R.id.passwordTextField);
        name = findViewById(R.id.nameTextField);
        surname = findViewById(R.id.surnameTextField);
        gender = findViewById(R.id.maleRadioButton);
        ageSpinner = findViewById(R.id.ageSpinner);

        findViewById(R.id.signUpButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signUpButton) {
            IUserInfoValidator validator = new UserInfoValidator();
            userInformation = getFields();

            if(validator.isCorrect(userInformation))
            signingUp(email.getText().toString().trim(), password.getText().toString());
            else
                Toast.makeText(SignUpActivity.this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
        }
        else {
            hideKeyboard(this);
        }
    }

    private UserInformation getFields() {
        UserInformation userInformation = new UserInformation();
        userInformation.setName(name.getText().toString().trim());
        userInformation.setSurname(surname.getText().toString().trim());
        userInformation.setEmail(email.getText().toString().trim());
        if (gender.isChecked()) userInformation.setGender("Male");
        else
            userInformation.setGender("Female");
        userInformation.setAge(Integer.parseInt(ageSpinner.getSelectedItem().toString()));
        return userInformation;
    }

     private void hideKeyboard(Activity activity) {
         InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
         View view = activity.getCurrentFocus();
         if (view == null) {
             view = new View(activity);
         }
         imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
     }

    private void signingUp(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    UserInfoDao userInfoDao = new UserInfoDao();
                    userInfoDao.setInfo(userInformation, mAuth.getUid());
                    Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                    signingIn(email, password);
                } else
                    Toast.makeText(SignUpActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void signingIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, RecordsActivity.class);               //change
                    startActivity(intent);
                }else
                    Toast.makeText(SignUpActivity.this, "Something went wrong(", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
