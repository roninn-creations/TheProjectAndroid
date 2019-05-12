package com.roninn_creations.theproject.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.roninn_creations.theproject.R;

import static com.roninn_creations.theproject.TheProjectApplication.getRequestHandler;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();

    private ProgressBar progressBar;
    private EditText emailEdit;
    private EditText passwordEdit;
    private Button loginButton;
    private Button googleButton;
    private Button facebookButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progress_bar);
        emailEdit = findViewById(R.id.edit_email);
        passwordEdit = findViewById(R.id.edit_password);
        loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(this::onLoginButtonClick);
        googleButton = findViewById(R.id.button_google);
        facebookButton = findViewById(R.id.button_facebook);
        registerButton = findViewById(R.id.button_register);
        registerButton.setOnClickListener(this::onRegisterButtonClick);
    }

    @Override
    public void onStop(){
        super.onStop();
        getRequestHandler().cancelRequests(TAG);
    }

    private void onLoginButtonClick(View view){
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

    }

    private void onRegisterButtonClick(View view){
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        registerIntent.putExtra(RegisterActivity.EXTRA_KEY_EMAIL, emailEdit.getText().toString());
        registerIntent.putExtra(RegisterActivity.EXTRA_KEY_PASSWORD, passwordEdit.getText().toString());
        startActivity(registerIntent);
    }
}
