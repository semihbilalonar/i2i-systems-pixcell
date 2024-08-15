package com.i2i.intern.pixcell.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

public class ForgotPasswordScreen extends AppCompatActivity {

    EditText phoneNumber;
    EditText securityKey;
    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            phoneNumber = findViewById(R.id.editTextPhoneFP);
            securityKey = findViewById(R.id.editTextSecurityKeyFP);
            signInButton = findViewById(R.id.btnSignInFP);
            signInButton.setOnClickListener(vi -> {
                if (TextUtils.isEmpty(phoneNumber.getText().toString()) || TextUtils.isEmpty(securityKey.getText().toString())) {
                    Toast.makeText(ForgotPasswordScreen.this, "Phone Number or Security Key field is empty.", Toast.LENGTH_LONG).show();
                } else {
                    signIn();
                }
            });

            return insets;
        });
    }

    private void signIn() {
        String msisdn = phoneNumber.getText().toString();
        String security_key = securityKey.getText().toString();

        Call<ResponseBody> call = RetrofitClientInstance.getUser2().signInWithSecurityKey(msisdn, security_key);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseBody = response.body().string();
                        Log.d("SignInResponse", "Response Body: " + responseBody);
                        if (responseBody.contains("Successful login")) {
                            startActivity(new Intent(ForgotPasswordScreen.this, BalanceInfoScreen.class).putExtra("msisdn", msisdn));
                            finish();
                        } else {
                            Toast.makeText(ForgotPasswordScreen.this, "Login failed. Please check your credentials.", Toast.LENGTH_LONG).show();
                        }
                    } catch (IOException e) {
                        Toast.makeText(ForgotPasswordScreen.this, "An error occurred. Please try again.", Toast.LENGTH_LONG).show();
                        Log.e("SignInError", "Response body parsing error: " + e.getMessage(), e);
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("SignInError", "Error Body: " + errorBody);
                    } catch (IOException e) {
                        Log.e("SignInError", "Error Body parsing error: " + e.getMessage(), e);
                    }
                    Toast.makeText(ForgotPasswordScreen.this, "Login failed. Please check your credentials.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ForgotPasswordScreen.this, "An error occurred. Please try again.", Toast.LENGTH_LONG).show();
                Log.e("SignInError", "Network error: " + t.getMessage(), t);
            }
        });
    }
}
