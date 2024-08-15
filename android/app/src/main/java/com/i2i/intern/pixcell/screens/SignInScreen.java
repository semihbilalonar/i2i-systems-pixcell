package com.i2i.intern.pixcell.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.i2i.intern.pixcell.R;
import com.i2i.intern.pixcell.service.RetrofitClientInstance;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInScreen extends AppCompatActivity {

    Button signInButton;
    Button signUpButton;
    TextView forgotPassword;
    EditText phoneNumber, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            signInButton = findViewById(R.id.btnSingIn);
            signUpButton = findViewById(R.id.btnSignUpSI);
            forgotPassword = findViewById(R.id.txtForgotPassword);
            phoneNumber = findViewById(R.id.editTextPhoneSI);
            password = findViewById(R.id.editTextPasswordSI);

            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SignInScreen.this, SignUpScreen.class);
                    startActivity(intent);
                }
            });

            forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SignInScreen.this, ForgotPasswordScreen.class);
                    startActivity(intent);
                }
            });

            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(phoneNumber.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                        Toast.makeText(SignInScreen.this, "Phone Number or Password field is empty.", Toast.LENGTH_LONG).show();
                    } else {
                        signIn();
                    }
                }
            });

            return insets;
        });
    }

    private void signIn() {
        String msisdn = phoneNumber.getText().toString();
        String pass = password.getText().toString();

        Call<ResponseBody> call = RetrofitClientInstance.getUser().signInWithPassword(msisdn, pass);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseBody = response.body().string();
                        if (responseBody.contains("Successful login")) {
                            startActivity(new Intent(SignInScreen.this, BalanceInfoScreen.class).putExtra("msisdn", msisdn));
                            finish();
                        } else {
                            Toast.makeText(SignInScreen.this, "Login failed. Please check your credentials.", Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        Toast.makeText(SignInScreen.this, "An error occurred. Please try again.", Toast.LENGTH_LONG).show();
                        Log.e("SignInError", e.getMessage(), e);
                    }
                } else {
                    Toast.makeText(SignInScreen.this, "Login failed. Please check your credentials.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SignInScreen.this, "An error occurred. Please try again.", Toast.LENGTH_LONG).show();
                Log.e("SignInError", t.getMessage(), t);
            }
        });
    }

}