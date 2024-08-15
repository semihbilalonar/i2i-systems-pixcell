package com.i2i.intern.pixcell.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.i2i.intern.pixcell.R;
import com.i2i.intern.pixcell.data_model.BalanceRemainingRequest;
import com.i2i.intern.pixcell.data_model.CustomerInfoRequest;
import com.i2i.intern.pixcell.data_model.PackageInfoRequest;
import com.i2i.intern.pixcell.service.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

import java.util.List;

public class BalanceInfoScreen extends AppCompatActivity {

    TextView welcomeText, packageInfoText;
    TextView mbText, smsText, minText, mbInfoText, smsInfoText, minInfoText;
    Button signOutButton;
    ProgressBar intBar, smsBar, minBar;

    private Handler handler;
    private Runnable runnable;
    private static final int UPDATE_INTERVAL = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_balance_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            welcomeText = findViewById(R.id.txtWelcome);
            getCustomerName();
            packageInfoText = findViewById(R.id.txtPackageInfo);
            mbInfoText = findViewById(R.id.txtMbInfo);
            smsInfoText = findViewById(R.id.txtSmsInfo);
            minInfoText = findViewById(R.id.txtMinuteInfo);

            mbText = findViewById(R.id.txtMb);
            smsText = findViewById(R.id.txtSmsCount);
            minText = findViewById(R.id.txtMinutes);

            intBar = findViewById(R.id.progressBarData);
            smsBar = findViewById(R.id.progressBarSms);
            minBar = findViewById(R.id.progressBarMinute);

            handler = new Handler(Looper.getMainLooper());
            runnable = new Runnable() {
                @Override
                public void run() {
                    updateBalanceInfo();
                    handler.postDelayed(this, UPDATE_INTERVAL);
                }
            };
            handler.post(runnable);

            getPackageInfo();

            signOutButton = findViewById(R.id.btnSignOut);
            signOutButton.setOnClickListener(v1 -> {
                Intent intent = new Intent(BalanceInfoScreen.this, SignInScreen.class);
                startActivity(intent);
            });

            return insets;
        });
    }

    private void updateBalanceInfo() {
        String msisdn = getIntent().getStringExtra("msisdn");
        Call<BalanceRemainingRequest> packageBalanceRemainingCall = RetrofitClientInstance.getRemainingInfo().getPackageBalanceRemaining(msisdn);
        packageBalanceRemainingCall.enqueue(new Callback<BalanceRemainingRequest>() {
            @Override
            public void onResponse(Call<BalanceRemainingRequest> call, Response<BalanceRemainingRequest> response) {
                if (response.isSuccessful()) {
                    BalanceRemainingRequest packageBalanceRemaining = response.body();
                    int intRemaining = packageBalanceRemaining.getBalanceData();
                    int smsRemaining = packageBalanceRemaining.getBalanceSms();
                    int minRemaining = packageBalanceRemaining.getBalanceMinutes();

                    mbText.setText(intRemaining + "\nMB");
                    smsText.setText(smsRemaining + "\nSMS");
                    minText.setText(minRemaining + "\nMin.");

                    intBar.setProgress(intRemaining);
                    smsBar.setProgress(smsRemaining);
                    minBar.setProgress(minRemaining);
                }
            }

            @Override
            public void onFailure(Call<BalanceRemainingRequest> call, Throwable t) {
                Toast.makeText(BalanceInfoScreen.this, "Error! " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    public void getCustomerName() {
        String msisdn = getIntent().getStringExtra("msisdn");
        Call<List<CustomerInfoRequest>> customerName = RetrofitClientInstance.getCustomerInfo().getCustomerById(msisdn);
        customerName.enqueue(new Callback<List<CustomerInfoRequest>>() {
            @Override
            public void onResponse(Call<List<CustomerInfoRequest>> call, Response<List<CustomerInfoRequest>> response) {
                if (response.isSuccessful()) {
                    List<CustomerInfoRequest> responseBody = response.body();
                    if (responseBody != null && !responseBody.isEmpty()) {
                        String name = responseBody.get(0).getName();
                        welcomeText.setText("Welcome " + name);
                    } else {
                        Log.e("BalanceInfoScreen", "Response body is null or empty");
                        Toast.makeText(BalanceInfoScreen.this, "Failed to retrieve customer information", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("BalanceInfoScreen", "API response was not successful: " + response.message());
                    Toast.makeText(BalanceInfoScreen.this, "API response was not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CustomerInfoRequest>> call, Throwable throwable) {
                Log.e("BalanceInfoScreen", "API call failed", throwable);
                Toast.makeText(BalanceInfoScreen.this, "Error! " + throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getPackageInfo() {
        String msisdn = getIntent().getStringExtra("msisdn");
        Call<List<PackageInfoRequest>> packageInfoRequestCall = RetrofitClientInstance.getPackageInfo().getPackageByMsisdn(msisdn);
        packageInfoRequestCall.enqueue(new Callback<List<PackageInfoRequest>>() {
            @Override
            public void onResponse(Call<List<PackageInfoRequest>> call, Response<List<PackageInfoRequest>> response) {
                if (response.isSuccessful()) {
                    List<PackageInfoRequest> responseBody = response.body();
                    if (responseBody != null && !responseBody.isEmpty()) {
                        String name = responseBody.get(0).getPackageName();
                        int period = responseBody.get(0).getPeriod();
                        int price = responseBody.get(0).getPrice();
                        packageInfoText.setText(name + "\nDuration: " + period + "\nPrice: " + price);
                        int data = responseBody.get(0).getAmountData();
                        int sms = responseBody.get(0).getAmountSms();
                        int min = responseBody.get(0).getAmountMinutes();
                        mbInfoText.setText(data + " MB");
                        smsInfoText.setText(sms + " SMS");
                        minInfoText.setText(min + " Min.");
                        intBar.setMax(data);
                        smsBar.setMax(sms);
                        minBar.setMax(min);
                    } else {
                        Log.e("BalanceInfoScreen", "Response body is null or empty");
                        Log.d("BalanceInfoScreen", "Raw response: " + response.raw().toString());
                        Toast.makeText(BalanceInfoScreen.this, "Failed to retrieve customer information", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("BalanceInfoScreen", "API response was not successful: " + response.message());
                    Log.d("BalanceInfoScreen", "Error body: " + response.errorBody().toString());
                    Toast.makeText(BalanceInfoScreen.this, "API response was not successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PackageInfoRequest>> call, Throwable throwable) {
                Log.e("PackageInfo", "API call failed", throwable);
            }
        });

    }
}
