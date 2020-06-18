package com.example.ac_twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtLoginUsername, edtLoginPassword;
    private Button btnLogin, btnLoginSignUp;
    private ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("LOG IN");

        edtLoginUsername = findViewById(R.id.edtLoginUsername);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);

        edtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnLogin);
                }
                return false;
            }
        });

        btnLoginSignUp = findViewById(R.id.btnLoginSignUp);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);
        btnLoginSignUp.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){
            ParseUser.getCurrentUser().logOut();
            //          transitionToTwitterUsers();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnLogin:
                if (edtLoginUsername.getText().toString().equals("") || edtLoginPassword.getText().toString().equals("")){
                    FancyToast.makeText(LoginActivity.this, "Username and Password are required!", Toast.LENGTH_SHORT,
                            FancyToast.INFO,false).show();
                } else {
                    ParseUser.logInInBackground(edtLoginUsername.getText().toString(), edtLoginPassword.getText().toString(),
                            new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null){
                                FancyToast.makeText(LoginActivity.this, user.get("username") + " logged in successfully", Toast.LENGTH_SHORT,
                                        FancyToast.SUCCESS,false).show();
                                transitionToTwitterUsers();
                            } else {
                                FancyToast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG,
                                        FancyToast.ERROR, false).show();
                            }
                        }
                    });
                }

                break;
            case R.id.btnLoginSignUp:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }

    }
    private void transitionToTwitterUsers() {
        Intent intent = new Intent(LoginActivity.this,TwitterUsers.class);
        startActivity(intent);
        finish();
    }
}