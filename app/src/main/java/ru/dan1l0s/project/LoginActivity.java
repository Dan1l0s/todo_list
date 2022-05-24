package ru.dan1l0s.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPass;
    private ImageView imageView;
    private TextView textViewRegLink;
    private Button buttonLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();

        editTextEmail = findViewById(R.id.etLoginEmail);
        editTextPass = findViewById(R.id.etLoginPass);
        textViewRegLink = findViewById(R.id.tvRegisterHere);
        buttonLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(v -> {loginUser();});
        textViewRegLink.setOnClickListener(v -> {startActivity(new Intent(LoginActivity.this, SignUpActivity.class));});
    }

    private void loginUser()
    {
        String email = editTextEmail.getText().toString();
        String password = editTextPass.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            editTextEmail.setError(getString(R.string.reg_email_error));
            editTextEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password))
        {
            imageView.setVisibility(View.VISIBLE);
            editTextPass.setError(getString(R.string.reg_pass_error));
            editTextPass.requestFocus();
        }
        else
        {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        if (!mAuth.getCurrentUser().isEmailVerified())
                        {
                            Toast.makeText(LoginActivity.this, getString(R.string.email_auth_error), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, getString(R.string.login_succ), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_error) + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
