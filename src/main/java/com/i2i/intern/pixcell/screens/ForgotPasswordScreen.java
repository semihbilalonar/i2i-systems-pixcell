package com.i2i.intern.pixcell.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(phoneNumber.getText().toString()) || TextUtils.isEmpty(securityKey.getText().toString())){
                        Toast.makeText(ForgotPasswordScreen.this,"Phone Number or Password field is empty.", Toast.LENGTH_LONG).show();
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
        String security_key = securityKey.getText().toString();

        Call<SignInResponse> signInResponseCall = RetrofitClientController.getUser().signInWithSecurityKey(phone_number,security_key);
        signInResponseCall.enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ForgotPasswordScreen.this,"Successfully signed in!", Toast.LENGTH_LONG).show();
                    // SignInResponse signInResponse = response.body();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(ForgotPasswordScreen.this, BalanceInfoScreen.class).putExtra("phone_number", phone_number));
                        }
                    },700);
                }else{
                    Toast.makeText(ForgotPasswordScreen.this,"Phone Number or Password is incorrect.", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                Toast.makeText(ForgotPasswordScreen.this,"Error!  "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }

}