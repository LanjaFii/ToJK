package com.lanjafii.tojk;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.logoImage);
        TextView text = findViewById(R.id.splashText);

        Animation zoom = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        logo.startAnimation(zoom);
        text.startAnimation(fade);

        // Transition vers MainActivity après 3s
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 5000);
    }
}
