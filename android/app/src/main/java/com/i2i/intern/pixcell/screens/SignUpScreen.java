package com.i2i.intern.pixcell.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.i2i.intern.pixcell.R;
import com.i2i.intern.pixcell.data_model.PackageListRequest;
import com.i2i.intern.pixcell.service.RetrofitClientInstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpScreen extends AppCompatActivity {

    EditText msisdnText, emailText, nameText, surnameText, passwordText, securityKeyText;
    Button signUpButton;
    private int selectedPackageId = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        nameText = findViewById(R.id.editTextName);
        surnameText = findViewById(R.id.editTextSurname);
        emailText = findViewById(R.id.editTextEmail);
        msisdnText = findViewById(R.id.editTextPhoneNumberSU);
        passwordText = findViewById(R.id.editTextPasswordSU);
        securityKeyText = findViewById(R.id.editTextSecurityKey);
        signUpButton = findViewById(R.id.btnSignUpSU);

        Spinner spinner = findViewById(R.id.packageSpinner);
        List<String> packageNames = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_dropdown_item, packageNames);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        fetchPackage(1, packageNames, adapter);
        fetchPackage(2, packageNames, adapter);
        fetchPackage(3, packageNames, adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPackageId = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msisdn = msisdnText.getText().toString();
                String name = nameText.getText().toString();
                String surname = surnameText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String securityKey = securityKeyText.getText().toString();
                String packageId = String.valueOf(selectedPackageId);

                Map<String, String> jsonMap = new LinkedHashMap<>();
                jsonMap.put("MSISDN", msisdn);
                jsonMap.put("NAME", name);
                jsonMap.put("SURNAME", surname);
                jsonMap.put("EMAIL", email);
                jsonMap.put("PASSWORD", password);
                jsonMap.put("SECURITY_KEY", securityKey);
                jsonMap.put("PACKAGE_ID", packageId);

                Gson gson = new GsonBuilder().create();
                String jsonBody = gson.toJson(jsonMap);

                RequestBody requestBody = RequestBody.create(jsonBody, MediaType.parse("application/json"));

                Call<ResponseBody> call = RetrofitClientInstance.postUser().signUp(requestBody);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String responseBody = response.body().string();
                                Log.d("SignUpResponse", "Response: " + responseBody); // Yanıtı logla
                                if (responseBody.contains("Successful")) {
                                    Intent intent = new Intent(SignUpScreen.this, BalanceInfoScreen.class);
                                    intent.putExtra("msisdn", msisdn);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUpScreen.this, responseBody, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SignUpScreen.this, SignInScreen.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (IOException e) {
                                Toast.makeText(SignUpScreen.this, "An error occurred while reading the response.", Toast.LENGTH_LONG).show();
                                Log.e("SignUpError", e.getMessage(), e);
                            }
                        } else {
                            Toast.makeText(SignUpScreen.this, "Sign up failed. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    }



                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SignUpScreen.this, "An error occurred. Please try again.", Toast.LENGTH_LONG).show();
                        Log.e("SignUpError", t.getMessage(), t);
                    }
                });

            }
        });
    }

    private void fetchPackage(int packageId, List<String> packageNames, ArrayAdapter<String> adapter) {

        Call<PackageListRequest> call = RetrofitClientInstance.getPackageList().getPackageById(packageId);

        call.enqueue(new Callback<PackageListRequest>() {
            @Override
            public void onResponse(Call<PackageListRequest> call, Response<PackageListRequest> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PackageListRequest packageData = response.body();
                    packageNames.add(packageData.getPackageName());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SignUpScreen.this, "Couldn't fetch the data. " + packageId, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PackageListRequest> call, Throwable t) {
                Toast.makeText(SignUpScreen.this, "Can't call API." + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
