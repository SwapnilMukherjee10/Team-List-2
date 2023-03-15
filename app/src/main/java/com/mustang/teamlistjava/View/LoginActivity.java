package com.mustang.teamlistjava.View;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mustang.teamlistjava.R;
import com.mustang.teamlistjava.databinding.ActivityLoginBinding;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = loginBinding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        loginBinding.buttonSubmit.setOnClickListener(v -> {

            if (loginBinding.editTextLoginNumber.getText().toString().trim().isEmpty()) {
                Toast.makeText(LoginActivity.this, "Invalid phone number", Toast.LENGTH_SHORT)
                        .show();
            } else if (loginBinding.editTextLoginNumber.getText().toString().trim().length() != 10) {
                Toast.makeText(LoginActivity.this, "Type valid phone number", Toast.LENGTH_SHORT)
                        .show();
            } else {
                otpSend();
            }

        });

    }

    private void otpSend() {

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                Intent intent = new Intent(LoginActivity.this,OTPActivity.class);
                intent.putExtra("phone",loginBinding.editTextLoginNumber.getText().toString().trim());
                intent.putExtra("verificationId",verificationId);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "OTP sent to your number", Toast.LENGTH_LONG).show();
            }

            ;

        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + loginBinding.editTextLoginNumber.getText().toString().trim())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userPhone = user.getPhoneNumber().toString().trim();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            Toast.makeText(this, "Welcome " + userPhone, Toast.LENGTH_LONG).show();
            startActivity(intent);

        }

    }
}