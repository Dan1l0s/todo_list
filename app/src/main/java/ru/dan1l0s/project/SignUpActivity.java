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

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText RegEmail;
    TextInputEditText RegPass;
    TextView LoginMessage;
    Button buttonReg;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        RegEmail = findViewById(R.id.etRegEmail);
        RegPass = findViewById(R.id.etRegPass);
        LoginMessage = findViewById(R.id.tvLoginHere);
        buttonReg = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        buttonReg.setOnClickListener(view -> {
            createUser();
        });

        LoginMessage.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        });
    }

    private void createUser(){
        String email = RegEmail.getText().toString();
        String password = RegPass.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            RegEmail.setError("Email не может быть пустым");
            RegEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password))
        {
            RegPass.setError("Пароль не может быть пустым");
            RegPass.requestFocus();
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(SignUpActivity.this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    }
                    else
                    {
                        Toast.makeText(SignUpActivity.this, "Ошибка при регистрации: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
