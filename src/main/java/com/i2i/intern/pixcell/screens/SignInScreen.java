package com.i2i.intern.pixcell.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import com.i2i.intern.pixcell.data_model.SignInResponse;
import com.i2i.intern.pixcell.service.RetrofitClientController;

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
                    if(TextUtils.isEmpty(phoneNumber.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                        Toast.makeText(SignInScreen.this,"Phone Number or Password field is empty.", Toast.LENGTH_LONG).show();
                    }else{
                        signIn();
                    }
                }
            });

            return insets;
        });
    }
    public void signIn(){
        String phone_number = phoneNumber.getText().toString();
        String pass_word = password.getText().toString();

        Call<SignInResponse> signInResponseCall = RetrofitClientController.getUser().signInWithPassword(phone_number,pass_word);
        signInResponseCall.enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SignInScreen.this,"Successfully signed in!", Toast.LENGTH_LONG).show();
                    // SignInResponse signInResponse = response.body();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SignInScreen.this, BalanceInfoScreen.class).putExtra("phone_number", phone_number));
                        }
                    },700);
                }else{
                    Toast.makeText(SignInScreen.this,"Phone Number or Password is incorrect.", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                Toast.makeText(SignInScreen.this,"Error!  "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }
}