package ru.dan1l0s.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
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
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    TextInputEditText editTextEmail;
    TextInputEditText editTextPass;
    TextView textViewRegLink;
    Button buttonLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.etLoginEmail);
        editTextPass = findViewById(R.id.etLoginPass);
        textViewRegLink = findViewById(R.id.tvRegisterHere);
        buttonLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(v -> {
            loginUser();
        });
        textViewRegLink.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }

    private void loginUser()
    {
        String email = editTextEmail.getText().toString();
        String password = editTextPass.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            editTextEmail.setError("Email не может быть пустым");
            editTextEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password))
        {
            editTextPass.setError("Пароль не может быть пустым");
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
                            Toast.makeText(LoginActivity.this, "Необходимо подтверждение почты", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Вход выполнен успешно", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Ошибка при входе: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
