package com.lanjafii.tojk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = findViewById(R.id.btnAdd);
        ImageButton btnSongs = findViewById(R.id.btnSongs);
        ImageButton btnTeaching = findViewById(R.id.btnTeaching);

        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        btnSongs.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SongsListActivity.class);
            startActivity(intent);
        });

        btnTeaching.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TeachingListActivity.class);
            startActivity(intent);
        });

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        findViewById(R.id.logoImageView).startAnimation(fadeIn);
        findViewById(R.id.btnAdd).startAnimation(fadeIn);
        findViewById(R.id.btnSongs).startAnimation(fadeIn);
        findViewById(R.id.btnTeaching).startAnimation(fadeIn);
    }
}
