package com.example.music_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.music_application.databinding.ActivityLoginBinding;
import com.example.music_application.entity.Customer;
import com.example.music_application.viewmodel.CustomerViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private CustomerViewModel CustomerViewModel;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.music_application.databinding.ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        // button functions
        binding.button1.setOnClickListener(v -> {

            binding.emailAddress.getText();
            boolean emailFlag = false;
            binding.password.getText();
            boolean pdFlag = false;

            //Email check
            if (Objects.requireNonNull(binding.emailAddress.getText()).toString().isEmpty()){binding.emailAddressCheck.setError("Please enter your Email!");}
            else if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailAddress.getText().toString()).matches()){binding.emailAddressCheck.setError("Invalid Format! Please enter again.");}
            else {binding.emailAddressCheck.setErrorEnabled(false); emailFlag = true;}

            //Password check
            if (Objects.requireNonNull(binding.password.getText()).toString().isEmpty()){binding.passwordCheck.setError("Please enter your password!");}
            else if (binding.password.getText().length() < 8){binding.passwordCheck.setError("Password should longer than 8 characters!");}
            else if (binding.password.getText().length() > 16){binding.passwordCheck.setError("Password should less than 16 characters!");}
            else if (!checkValidPassword(binding.password.getText().toString())){binding.passwordCheck.setError("Password should combine with upper and lower case numbers and special symbols!");}
            else {binding.passwordCheck.setErrorEnabled(false); pdFlag = true;}


            if (emailFlag){
                if(pdFlag){
                        mAuth.signInWithEmailAndPassword(binding.emailAddress.getText().toString(), binding.password.getText().toString())
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    Bundle loginBundle = new Bundle();
                                    loginBundle.putString("0", binding.emailAddress.getText().toString());

                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(LoginActivity.this, "Successfully Login!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                    intent.putExtras(loginBundle);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    }}
        });

        binding.button2.setOnClickListener(v -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("message");

            myRef.setValue("Hello, World!");
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


    }

    public static boolean checkValidPassword(final String password) {

        Pattern password_pattern;
        Matcher password_matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        password_pattern = Pattern.compile(PASSWORD_PATTERN);
        password_matcher = password_pattern.matcher(password);

        return password_matcher.matches();
    }
}
