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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.models.User;

import java.util.Arrays;

import static com.roninn_creations.theproject.TheProjectApplication.getAuthService;
import static com.roninn_creations.theproject.TheProjectApplication.getRequestHandler;
import static com.roninn_creations.theproject.TheProjectApplication.setUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();
    // Google API request code for getting token
    private static final int RC_GET_TOKEN = 9002;
    private static final String PROFILE = "public_profile";
    private static final String EMAIL = "email";

    private ProgressBar progressBar;
    private EditText emailEdit;
    private EditText passwordEdit;
    private Button loginButton;
    private Button googleButton;
    private Button facebookButton;
    private Button registerButton;
    CallbackManager callbackManager;
    private boolean isSigningIn;

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
        googleButton.setOnClickListener(this::onGoogleButtonClick);
        facebookButton.setOnClickListener(this::onFacebookButtonClick);
        registerButton.setOnClickListener(this::onRegisterButtonClick);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult){
                String token = loginResult.getAccessToken().getToken();
                Log.i(TAG, "TOKEN: " + token);
                Snackbar.make(facebookButton, token, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(){
                Snackbar.make(facebookButton, "Login cancelled", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception){
                Log.w(TAG, "WARNING: Login failed!", exception);
                Snackbar.make(facebookButton, "Login failed", Snackbar.LENGTH_LONG).show();
            }
        });


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

    private void onGoogleButtonClick(View view){
        GoogleSignInOptions options = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PROFILE), new Scope(Scopes.EMAIL))
                .requestServerAuthCode(getString(R.string.google_app_id))
                .build();
        GoogleSignInClient client = GoogleSignIn.getClient(this, options);
        if (!isSigningIn){
            isSigningIn = true;
            progressBar.setVisibility(View.VISIBLE);
            Intent signInIntent = client.getSignInIntent();
            startActivityForResult(signInIntent, RC_GET_TOKEN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        // On result form the Google account picker
        if (requestCode == RC_GET_TOKEN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String accessToken = account.getServerAuthCode();
            getAuthService().google(accessToken, this::onLoginResponse, this::onErrorResponse, TAG);
        } catch (Exception e) {
            onException(e);
        }
    }

    private void onFacebookButtonClick(View view){
        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList(EMAIL));
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

    private void onException(Exception exception){
        progressBar.setVisibility(View.GONE);
        Log.w(TAG, "WARNING: Login failed!", exception);
        Snackbar.make(loginButton, "Login failed", Snackbar.LENGTH_LONG).show();
    }
}
