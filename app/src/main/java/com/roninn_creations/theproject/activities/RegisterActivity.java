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
import com.roninn_creations.theproject.models.RegisterModel;
import com.roninn_creations.theproject.models.User;

import static com.roninn_creations.theproject.TheProjectApplication.getAuthService;
import static com.roninn_creations.theproject.TheProjectApplication.getRequestHandler;
import static com.roninn_creations.theproject.TheProjectApplication.setUser;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getName();
    public static final String EXTRA_KEY_EMAIL = "EXTRA_KEY_EMAIL";
    public static final String EXTRA_KEY_PASSWORD = "EXTRA_KEY_PASSWORD";

    private ProgressBar progressBar;
    private EditText emailEdit;
    private EditText nameEdit;
    private EditText passwordEdit;
    private EditText password2Edit;
    private Button registerButton;
    private boolean isSubmitting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = findViewById(R.id.progress_bar);
        emailEdit = findViewById(R.id.edit_email);
        nameEdit = findViewById(R.id.edit_name);
        passwordEdit = findViewById(R.id.edit_password);
        password2Edit = findViewById(R.id.edit_password2);
        registerButton = findViewById(R.id.button_register);

        emailEdit.setText(getIntent().getStringExtra(EXTRA_KEY_EMAIL));
        passwordEdit.setText(getIntent().getStringExtra(EXTRA_KEY_PASSWORD));
        password2Edit.setOnEditorActionListener(this::onPassword2EditorSend);
        registerButton.setOnClickListener(this::onRegisterButtonClick);
    }

    @Override
    public void onStop(){
        super.onStop();
        getRequestHandler().cancelRequests(TAG);
    }

    private boolean onPassword2EditorSend(TextView view, int actionId, KeyEvent event){
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_SEND){
            submitRegistration();
            handled = true;
        }
        return handled;
    }

    private void onRegisterButtonClick(View view){
        submitRegistration();
    }

    private void submitRegistration(){
        if (!isSubmitting){
            isSubmitting = true;
            String password = passwordEdit.getText().toString();
            String password2 = password2Edit.getText().toString();
            if (!password.equals(password2)){
                Snackbar.make(registerButton, "Passwords do not match",
                        Snackbar.LENGTH_LONG).show();
                isSubmitting = false;
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            String email = emailEdit.getText().toString();
            String name = nameEdit.getText().toString();
            RegisterModel model = new RegisterModel(email, name, password);
            getAuthService().register(model,
                    this::onRegisterResponse, this::onErrorResponse, TAG);
        }
    }

    private void onRegisterResponse(User user, String token){
        setUser(user);
        getRequestHandler().setToken(token);
        Intent placesIntent = new Intent(this, PlacesActivity.class);
        progressBar.setVisibility(View.GONE);
        isSubmitting = false;
        startActivity(placesIntent);
        finish();
    }

    private void onErrorResponse(String message){
        Snackbar.make(registerButton, message, Snackbar.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
        isSubmitting = false;
    }
}
