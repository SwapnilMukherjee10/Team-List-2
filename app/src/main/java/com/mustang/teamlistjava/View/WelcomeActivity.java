package com.mustang.teamlistjava.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mustang.teamlistjava.R;
import com.mustang.teamlistjava.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding animBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animBinding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        View view = animBinding.getRoot();
        setContentView(view);

        // Welcome screen animation
        Animation alphaAnimation = AnimationUtils.loadAnimation(this,R.anim.anim);
        animBinding.imageViewLogo.startAnimation(alphaAnimation);

        Handler handler = new Handler(Looper.getMainLooper());

        final Runnable r = new Runnable(){
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };

        handler.postDelayed(r, 3000);


    }
}