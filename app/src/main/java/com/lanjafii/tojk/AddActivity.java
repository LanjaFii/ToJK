package com.lanjafii.tojk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    private Button btnAddSong, btnAddTeaching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        TextView titleText = findViewById(R.id.titleText);
        // Initialisation des boutons
        btnAddSong = findViewById(R.id.btnAddSong);
        btnAddTeaching = findViewById(R.id.btnAddTeaching);

        // Événement pour ajouter une chanson
        btnAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, AddSongActivity.class);
                startActivity(intent);
            }
        });

        // Événement pour ajouter un enseignement spirituel
        btnAddTeaching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, AddTeachingActivity.class);
                startActivity(intent);
            }
        });

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        titleText.startAnimation(fadeIn);
        btnAddSong.startAnimation(fadeIn);
        btnAddTeaching.startAnimation(fadeIn);
    }
}
