package com.example.music_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.music_application.databinding.ActivityRegisterBinding;
import com.example.music_application.entity.Customer;
import com.example.music_application.viewmodel.CustomerViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private CustomerViewModel customerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        customerViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CustomerViewModel.class);
        mAuth = FirebaseAuth.getInstance();

        // button functions
        binding.button1.setOnClickListener(v -> {

            binding.userName.getText();
            boolean usernameFlag = false;
            binding.address.getText();
            boolean addressFlag = false;
            binding.emailAddress.getText();
            boolean emailFlag = false;
            binding.password.getText();
            boolean pdFlag = false;
            binding.password1.getText();
            boolean pd1Flag = false;

            //Username check
            if (Objects.requireNonNull(binding.userName.getText()).toString().isEmpty()){binding.userNameCheck.setError("Please enter your username!");}
            else {binding.userNameCheck.setErrorEnabled(false); usernameFlag = true;}

            //address check
            if (Objects.requireNonNull(binding.address.getText()).toString().isEmpty()){binding.address.setError("Please enter your address!");}
            else {binding.addressCheck.setErrorEnabled(false); addressFlag = true;}

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

            //Repeated Password check
            if (Objects.requireNonNull(binding.password1.getText()).toString().isEmpty()){binding.password1Check.setError("Please enter your password!");}
            else if (!binding.password1.getText().toString().equals(binding.password.getText().toString())){binding.password1Check.setError("Please enter the same password!");}
            else {binding.password1Check.setErrorEnabled(false); pd1Flag = true;}

            if(usernameFlag){
                if(addressFlag){
                    if (emailFlag){
                        if(pdFlag){
                            if(pd1Flag){
                                mAuth.createUserWithEmailAndPassword(binding.emailAddress.getText().toString(), binding.password.getText().toString())
                                        .addOnCompleteListener(this, task -> {
                                            if (task.isSuccessful()) {
                                                Customer customer = new Customer(binding.userName.getText().toString(), binding.emailAddress.getText().toString(), binding.address.getText().toString());
                                                customerViewModel.insert(customer);
                                                // Sign in success, update UI with the signed-in user's information
                                                Toast.makeText(RegisterActivity.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent( RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                    });
                        }}}}}
        });

        binding.button2.setOnClickListener(v -> {
            Intent intent = new Intent( RegisterActivity.this, LoginActivity.class);
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

