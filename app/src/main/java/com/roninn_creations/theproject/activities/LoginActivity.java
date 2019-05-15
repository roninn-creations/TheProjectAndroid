package com.roninn_creations.theproject.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.TheProjectApplication;
import com.roninn_creations.theproject.models.User;

import java.util.Collections;

import static com.roninn_creations.theproject.TheProjectApplication.getAuthService;
import static com.roninn_creations.theproject.TheProjectApplication.getFacebookLoginManager;
import static com.roninn_creations.theproject.TheProjectApplication.getRequestHandler;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();
    // Google API request code for getting token
    private static final int RC_GET_TOKEN = 9002;
    private static final String EMAIL = "email";

    private ProgressBar progressBar;
    private EditText emailEdit;
    private EditText passwordEdit;
    private Button loginButton;
    private Button facebookButton;
    CallbackManager callbackManager;
    GoogleSignInClient googleSignInClient;
    private boolean isLoggingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        googleSignInClient = ((TheProjectApplication)getApplication()).getGoogleSignInClient();

        progressBar = findViewById(R.id.progress_bar);
        emailEdit = findViewById(R.id.edit_email);
        passwordEdit = findViewById(R.id.edit_password);
        loginButton = findViewById(R.id.button_login);
        Button googleButton = findViewById(R.id.button_google);
        facebookButton = findViewById(R.id.button_facebook);
        Button registerButton = findViewById(R.id.button_register);

        passwordEdit.setOnEditorActionListener(this::onPasswordEditorSend);
        loginButton.setOnClickListener(this::onLoginButtonClick);
        googleButton.setOnClickListener(this::onGoogleButtonClick);
        facebookButton.setOnClickListener(this::onFacebookButtonClick);
        registerButton.setOnClickListener(this::onRegisterButtonClick);
    }

    @Override
    public void onStart(){
        super.onStart();
        isLoggingIn = true;
        progressBar.setVisibility(View.VISIBLE);
        String jwt = getRequestHandler().getToken();
        GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(this);
        AccessToken facebookAccessToken = AccessToken.getCurrentAccessToken();
        if (jwt != null){
            submitJwtLogin();
        } else if (googleAccount != null){
            googleSignInClient.silentSignIn()
                    .addOnCompleteListener(this::handleGoogleLoginResult);
        } else if (facebookAccessToken != null){
            submitFacebookLogin(facebookAccessToken.getToken());
        } else {
            progressBar.setVisibility(View.GONE);
            isLoggingIn = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (callbackManager != null)
            callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // On result form the Google account picker
        if (requestCode == RC_GET_TOKEN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleLoginResult(task);
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        isLoggingIn = false;
        progressBar.setVisibility(View.GONE);
        getRequestHandler().cancelRequests(TAG);
    }

    private boolean onPasswordEditorSend(TextView view, int actionId, KeyEvent event){
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEND){
            if (!isLoggingIn) {
                isLoggingIn = true;
                progressBar.setVisibility(View.VISIBLE);
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                submitBasicLogin(email, password);
            }
            handled = true;
        }
        return handled;
    }

    private void onLoginButtonClick(View view){
        if (!isLoggingIn) {
            isLoggingIn = true;
            progressBar.setVisibility(View.VISIBLE);
            String email = emailEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            submitBasicLogin(email, password);
        }
    }

    private void onGoogleButtonClick(View view){
        if (!isLoggingIn){
            isLoggingIn = true;
            progressBar.setVisibility(View.VISIBLE);
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_GET_TOKEN);
        }
    }

    private void onFacebookButtonClick(View view){
        if (!isLoggingIn) {
            isLoggingIn = true;
            progressBar.setVisibility(View.VISIBLE);
            callbackManager = CallbackManager.Factory.create();
            getFacebookLoginManager().registerCallback(callbackManager,
                    new FacebookLoginCallback());
            getFacebookLoginManager().logInWithReadPermissions(this,
                    Collections.singleton(EMAIL));
        }
    }

    private void onRegisterButtonClick(View view){
        if (!isLoggingIn) {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            registerIntent.putExtra(RegisterActivity.EXTRA_KEY_EMAIL,
                    emailEdit.getText().toString());
            registerIntent.putExtra(RegisterActivity.EXTRA_KEY_PASSWORD,
                    passwordEdit.getText().toString());
            startActivity(registerIntent);
        }
    }

    private void submitBasicLogin(String email, String password){
        if (email != null && password != null)
            getAuthService().basic(email, password,
                    this::onLoginResponse, this::onErrorResponse, TAG);
        else
            onError("Missing email or password");
    }

    private void submitGoogleLogin(String googleCode){
        if (googleCode != null)
            getAuthService().google(googleCode, this::onLoginResponse,
                    this::onErrorResponse, TAG);
        else
            onError("Google login failed");
    }

    private void submitFacebookLogin(String facebookToken){
        if (facebookToken != null)
            getAuthService().facebook(facebookToken, this::onLoginResponse,
                    this::onErrorResponse, TAG);
        else
            onError("Facebook login failed");
    }

    private void submitJwtLogin(){
        getAuthService().jwt(this::onLoginResponse, this::onErrorResponse, TAG);
    }

    private void handleGoogleLoginResult(@NonNull Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null)
                submitGoogleLogin(account.getServerAuthCode());
            else if (completedTask.isCanceled())
                onError("Google login cancelled");
            else
                onError("Google login failed");
        } catch (ApiException e) {
            onException(e);
        }
    }

    private void onLoginResponse(User user, String token){
        progressBar.setVisibility(View.GONE);
        isLoggingIn = false;
        ((TheProjectApplication)getApplication()).login(user, token);
        finish();
    }

    private void onErrorResponse(String message){
        onError(message);
    }

    private void onException(Exception exception){
        Log.w(TAG, "WARNING: Login failed!", exception);
        Snackbar.make(loginButton, "Login failed", Snackbar.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
        isLoggingIn = false;
    }

    private void onError(String message){
        Log.w(TAG, "WARNING: Login failed!\n" + message);
        Snackbar.make(facebookButton, message, Snackbar.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
        isLoggingIn = false;
    }

    private class FacebookLoginCallback implements FacebookCallback<LoginResult> {
        @Override
        public void onSuccess(LoginResult loginResult){
            if (loginResult != null)
                submitFacebookLogin(loginResult.getAccessToken().getToken());
            else
                LoginActivity.this.onError("Facebook login failed");
        }

        @Override
        public void onCancel(){
            LoginActivity.this.onError("Facebook login cancelled");
        }

        @Override
        public void onError(FacebookException exception){
            onException(exception);
        }
    }
}
