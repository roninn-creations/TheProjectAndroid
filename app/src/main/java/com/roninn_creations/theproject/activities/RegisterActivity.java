package com.roninn_creations.theproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.roninn_creations.theproject.R;

import static com.roninn_creations.theproject.TheProjectApplication.getRequestHandler;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getName();
    public static final String EXTRA_KEY_EMAIL = "EXTRA_KEY_EMAIL";
    public static final String EXTRA_KEY_PASSWORD = "EXTRA_KEY_PASSWORD";

    private ProgressBar progressBar;
    private EditText emailEdit;
    private EditText passwordEdit;
    private EditText password2Edit;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = findViewById(R.id.progress_bar);
        emailEdit = findViewById(R.id.edit_email);
        passwordEdit = findViewById(R.id.edit_password);
        password2Edit = findViewById(R.id.edit_password2);
        registerButton = findViewById(R.id.button_register);

        emailEdit.setText(getIntent().getStringExtra(EXTRA_KEY_EMAIL));
        passwordEdit.setText(getIntent().getStringExtra(EXTRA_KEY_PASSWORD));
        registerButton.setOnClickListener(this::onRegisterButtonClick);
    }

    @Override
    public void onStop(){
        super.onStop();
        getRequestHandler().cancelRequests(TAG);
    }

    private void onRegisterButtonClick(View view){
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String password2 = password2Edit.getTransitionName().toString();

    }
}
