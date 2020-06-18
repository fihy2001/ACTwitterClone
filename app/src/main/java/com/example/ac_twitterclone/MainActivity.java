package com.example.ac_twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtSignUpEmail, edtSignUpUsername, edtSignUpPassword;
    private Button btnSignUp, btnSignUpLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpUsername = findViewById(R.id.edtSignUpUsername);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);

        edtSignUpPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnSignUp);
                }
                return false;
            }
        });



        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUpLogin = findViewById(R.id.btnSignUpLogin);

        btnSignUp.setOnClickListener(this);
        btnSignUpLogin.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){
 //           ParseUser.getCurrentUser().logOut();
            transitionToTwitterUsers();
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                if (edtSignUpEmail.getText().toString().equals("") || edtSignUpUsername.getText().toString().equals("")
                || edtSignUpPassword.getText().toString().equals("")){

                    FancyToast.makeText(MainActivity.this, "Email, Username, Password are required",
                            Toast.LENGTH_SHORT,FancyToast.INFO,false).show();
                } else {
                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtSignUpEmail.getText().toString());
                    appUser.setUsername(edtSignUpUsername.getText().toString());
                    appUser.setPassword(edtSignUpPassword.getText().toString());

                    progressBar.setVisibility(View.VISIBLE);


                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null){
                                FancyToast.makeText(MainActivity.this, appUser.get("username") + " added successfully",
                                        Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                transitionToTwitterUsers();
                                progressBar.setVisibility(View.INVISIBLE);

                            } else {
                                FancyToast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT,
                                        FancyToast.ERROR,false).show();

                            }

                        }
                    });


                }
                break;
            case R.id.btnSignUpLogin:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void transitionToTwitterUsers() {
        Intent intent = new Intent(MainActivity.this,TwitterUsers.class);
        startActivity(intent);
        finish();
    }
}