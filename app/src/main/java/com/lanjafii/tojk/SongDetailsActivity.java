package com.lanjafii.tojk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SongDetailsActivity extends AppCompatActivity {

    TextView titleTextView, genreTextView, tonaliteTextView, batterieTextView, bassTextView, soloTextView, clavier1TextView, clavier2TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);

        // Récupérer les vues
        titleTextView = findViewById(R.id.titleTextView);
        genreTextView = findViewById(R.id.genreTextView);
        tonaliteTextView = findViewById(R.id.tonaliteTextView);
        batterieTextView = findViewById(R.id.batterieTextView);
        bassTextView = findViewById(R.id.bassTextView);
        soloTextView = findViewById(R.id.soloTextView);
        clavier1TextView = findViewById(R.id.clavier1TextView);
        clavier2TextView = findViewById(R.id.clavier2TextView);

        // Récupérer les données de l'intent
        String nom = getIntent().getStringExtra("nom_chanson");
        String genre = getIntent().getStringExtra("genre");
        String tonalite = getIntent().getStringExtra("tonalite");
        String batterie = getIntent().getStringExtra("poste_batterie");
        String bass = getIntent().getStringExtra("poste_bass");
        String solo = getIntent().getStringExtra("poste_solo");
        String clavier1 = getIntent().getStringExtra("poste_clavier1");
        String clavier2 = getIntent().getStringExtra("poste_clavier2");

        // Mettre à jour les vues
        titleTextView.setText("🎶 " + nom);
        genreTextView.setText("🎼 Genre : " + genre);
        tonaliteTextView.setText("🔑 Tonalité : " + tonalite);
        batterieTextView.setText("🥁 Batterie : " + batterie);
        bassTextView.setText("🎸 Basse : " + bass);
        soloTextView.setText("🎸 Solo : " + solo);
        clavier1TextView.setText("🎹 Clavier 1 : " + clavier1);
        clavier2TextView.setText("🎹 Clavier 2 : " + clavier2);
    }
}
