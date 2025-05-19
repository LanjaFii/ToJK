package com.lanjafii.tojk;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AddSongActivity extends AppCompatActivity {

    private EditText editSongName, editTonalite;
    private Spinner spinnerGenre;
    private EditText editBatterie, editBasse, editSolo, editClavier1, editClavier2;
    private Button btnSaveSong;

    private DatabaseHelper databaseHelper; // Instance de la base de données

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        // Initialisation des vues
        editSongName = findViewById(R.id.editSongName);
        editTonalite = findViewById(R.id.editTonalite);
        spinnerGenre = findViewById(R.id.spinnerGenre);
        editBatterie = findViewById(R.id.editBatterie);
        editBasse = findViewById(R.id.editBasse);
        editSolo = findViewById(R.id.editSolo);
        editClavier1 = findViewById(R.id.editClavier1);
        editClavier2 = findViewById(R.id.editClavier2);
        btnSaveSong = findViewById(R.id.btnSaveSong);

        // Initialisation de la base de données
        databaseHelper = new DatabaseHelper(this);

        // Remplir le Spinner avec les genres
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, new String[]{"Worship", "Louange"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);

        // Action du bouton Sauvegarder
        btnSaveSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSong();
            }
        });

        LinearLayout container = findViewById(R.id.addSongContainer);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        container.startAnimation(slideUp);
    }

    private void saveSong() {
        String songName = editSongName.getText().toString().trim();
        String tonalite = editTonalite.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();
        String batterie = editBatterie.getText().toString().trim();
        String basse = editBasse.getText().toString().trim();
        String solo = editSolo.getText().toString().trim();
        String clavier1 = editClavier1.getText().toString().trim();
        String clavier2 = editClavier2.getText().toString().trim();

        if (songName.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer le nom de la chanson", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insertion dans la base de données
        boolean isInserted = databaseHelper.insertSong(songName, genre, tonalite, batterie, basse, solo, clavier1, clavier2);

        if (isInserted) {
            Toast.makeText(this, "Chanson ajoutée avec succès !", Toast.LENGTH_SHORT).show();
            finish(); // Ferme l'activité après l'ajout réussi
        } else {
            Toast.makeText(this, "Erreur lors de l'ajout de la chanson", Toast.LENGTH_SHORT).show();
        }
    }

}
