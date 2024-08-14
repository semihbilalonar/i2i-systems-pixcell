package com.i2i.intern.pixcell.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.i2i.intern.pixcell.R;

public class SignUpScreen extends AppCompatActivity {

    EditText name, lastname, email, msisdn, password, security_key;
    Button signUpButton;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            name = findViewById(R.id.editTextName);
            lastname = findViewById(R.id.editTextSurname);
            email = findViewById(R.id.editTextEmail);
            msisdn = findViewById(R.id.editTextPhoneNumberSU);
            password = findViewById(R.id.editTextPasswordSU);
            security_key = findViewById(R.id.editTextSecurityKey);
            signUpButton = findViewById(R.id.btnSignUpSU);

            spinner = (Spinner) findViewById(R.id.packageSpinner2);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.packages,
                    android.R.layout.simple_spinner_item
            );
            adapter = ArrayAdapter.createFromResource(this, R.array.packages, R.layout.spinner_item);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spinner.setAdapter(adapter);

            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(TextUtils.isEmpty(name.getText().toString())
                            || TextUtils.isEmpty(lastname.getText().toString())
                            || TextUtils.isEmpty(email.getText().toString())
                            || TextUtils.isEmpty(msisdn.getText().toString())
                            || TextUtils.isEmpty(password.getText().toString())
                            || TextUtils.isEmpty(security_key.getText().toString())
                    ){
                        Toast.makeText(SignUpScreen.this,"One or more fields are empty.", Toast.LENGTH_LONG).show();
                    }else{
                        signUp();
                    }
                }
            });

            return insets;
        });
    }

    public void signUp() {
        // Code blocks
        // Intent intent = new Intent(SignUpScreen.this, BalanceInfoScreen.class);
        // startActivity(intent);
    }
}