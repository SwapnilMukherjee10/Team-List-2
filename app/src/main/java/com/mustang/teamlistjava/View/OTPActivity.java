package com.mustang.teamlistjava.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mustang.teamlistjava.R;
import com.mustang.teamlistjava.databinding.ActivityOtpactivityBinding;

public class OTPActivity extends AppCompatActivity {

    private ActivityOtpactivityBinding otpBinding;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpBinding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        View view = otpBinding.getRoot();
        setContentView(view);

        verificationId = getIntent().getStringExtra("verificationId");

        otpBinding.buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (otpBinding.editTextOTP.toString().trim().isEmpty()) {
                    Toast.makeText(OTPActivity.this, "OTP is not valid", Toast.LENGTH_SHORT).show();
                } else if (verificationId != null){
                    String code = otpBinding.editTextOTP.getText().toString().trim();

                    String userPhone = getIntent().getStringExtra("phone");
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                    FirebaseAuth.getInstance()
                            .signInWithCredential(credential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(OTPActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                Toast.makeText(OTPActivity.this, "Welcome " + userPhone, Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            } else  {
                                Toast.makeText(OTPActivity.this, "OTP is invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }

}