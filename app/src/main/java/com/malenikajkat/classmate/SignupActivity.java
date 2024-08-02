package com.malenikajkat.classmate;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText signupName, signupEmail, signupMobile, signupUsername, signupPassword, signupConfirmPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupMobile = findViewById(R.id.signup_mobile);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        signupConfirmPassword = findViewById(R.id.signup_confirmPassword);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = signupName.getText().toString().trim();
                String email = signupEmail.getText().toString().trim();
                String mobile = signupMobile.getText().toString().trim();
                String username = signupUsername.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String confirmPassword = signupConfirmPassword.getText().toString().trim();

                if (validateName(name) && validateEmail(email) && validateMobile(mobile) && validateUsername(username)
                        && validatePassword(password) && validateConfirmPassword(password, confirmPassword)) {
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("Пользователи");

                    HelperClass helperClass = new HelperClass(name, email, mobile, username, password, confirmPassword);
                    reference.child(username).setValue(helperClass);

                    Toast.makeText(SignupActivity.this, "Вы Успешно Зарегистрировались!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignupActivity.this, "Пожалуйста, введите действительные учетные данные для регистрации",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateName(String name) {
        if ( name.length() <= 4 || name.matches(".*\\d.*")) {
            signupName.setError("Введите действительное имя (не менее 4 символов и без цифр).");
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        if ( !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmail.setError("Введите действительный адрес электронной почты.");
            return false;
        }
        return true;
    }

    private boolean validateMobile(String mobile) {
        if ( mobile.length() != 10 || !TextUtils.isDigitsOnly(mobile)) {
            signupMobile.setError("Введите действительный 10-значный номер мобильного телефона.");
            return false;
        }
        return true;
    }

    private boolean validateUsername(String username) {
        if ( username.length() < 4 || !username.matches("^[a-zA-Z0-9_]*$")) {
            signupUsername.setError("Введите действительное имя пользователя (не менее 4 символов, только буквы, цифры и символы подчеркивания).");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";
        if ( !password.matches(passwordPattern)) {
            signupPassword.setError("Введите действительный пароль (минимум 8 символов, по крайней мере один строчный, один прописной, одна цифра и один специальный символ).");
            return false;
        }
        return true;
    }

    private boolean validateConfirmPassword(String password, String confirmPassword) {
        if (!TextUtils.equals(password, confirmPassword)) {
            signupConfirmPassword.setError("Пароли не совпадают.");
            return false;
        }
        return true;
    }
}

