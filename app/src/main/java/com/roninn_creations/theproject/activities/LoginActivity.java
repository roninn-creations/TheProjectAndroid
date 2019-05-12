package com.roninn_creations.theproject.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.models.User;

import static com.roninn_creations.theproject.TheProjectApplication.getAuthService;
import static com.roninn_creations.theproject.TheProjectApplication.getRequestHandler;
import static com.roninn_creations.theproject.TheProjectApplication.setUser;

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
        googleButton = findViewById(R.id.button_google);
        facebookButton = findViewById(R.id.button_facebook);
        registerButton = findViewById(R.id.button_register);

        passwordEdit.setOnEditorActionListener(this::onPasswordEditorSend);
        loginButton.setOnClickListener(this::onLoginButtonClick);
        registerButton.setOnClickListener(this::onRegisterButtonClick);
    }

    @Override
    public void onStop(){
        super.onStop();
        getRequestHandler().cancelRequests(TAG);
    }

    private void onLoginButtonClick(View view){
        submitLogin();
    }

    private boolean onPasswordEditorSend(TextView view, int actionId, KeyEvent event){
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEND){
            submitLogin();
            handled = true;
        }
        return handled;
    }

    private void submitLogin(){
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        getAuthService().login(email, password,
                this::onLoginResponse, this::onErrorResponse, TAG);
    }

    private void onRegisterButtonClick(View view){
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        registerIntent.putExtra(RegisterActivity.EXTRA_KEY_EMAIL, emailEdit.getText().toString());
        registerIntent.putExtra(RegisterActivity.EXTRA_KEY_PASSWORD, passwordEdit.getText().toString());
        startActivity(registerIntent);
    }

    private void onLoginResponse(User user, String token){
        setUser(user);
        getRequestHandler().setToken(token);
        Intent placesIntent = new Intent(this, PlacesActivity.class);
        startActivity(placesIntent);
    }

    private void onErrorResponse(String message){
        progressBar.setVisibility(View.GONE);
        Snackbar.make(loginButton, message, Snackbar.LENGTH_LONG).show();
    }
}
