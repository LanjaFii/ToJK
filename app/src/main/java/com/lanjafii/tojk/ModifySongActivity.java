package com.lanjafii.tojk;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ModifySongActivity extends AppCompatActivity {

    private EditText editNom, editGenre, editTonalite,
            editBatterie, editBass, editSolo, editClavier1, editClavier2;
    private Button btnModifier;

    private DatabaseHelper dbHelper;
    private int songId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_song);

        editNom = findViewById(R.id.editNom);
        editGenre = findViewById(R.id.editGenre);
        editTonalite = findViewById(R.id.editTonalite);
        editBatterie = findViewById(R.id.editBatterie);
        editBass = findViewById(R.id.editBass);
        editSolo = findViewById(R.id.editSolo);
        editClavier1 = findViewById(R.id.editClavier1);
        editClavier2 = findViewById(R.id.editClavier2);
        btnModifier = findViewById(R.id.btnModifier);

        dbHelper = new DatabaseHelper(this);

        // Récupérer les données passées
        songId = getIntent().getIntExtra("song_id", -1);

        Song song = dbHelper.getSongById(songId);

        if (song != null) {
            editNom.setText(song.nomChanson);
            editGenre.setText(song.genre);
            editTonalite.setText(song.tonalite);
            editBatterie.setText(song.posteBatterie);
            editBass.setText(song.posteBass);
            editSolo.setText(song.posteSolo);
            editClavier1.setText(song.posteClavier1);
            editClavier2.setText(song.posteClavier2);
        }

        btnModifier.setOnClickListener(v -> {
            boolean updated = dbHelper.updateSong(
                    songId,
                    editNom.getText().toString(),
                    editGenre.getText().toString(),
                    editTonalite.getText().toString(),
                    editBatterie.getText().toString(),
                    editBass.getText().toString(),
                    editSolo.getText().toString(),
                    editClavier1.getText().toString(),
                    editClavier2.getText().toString()
            );

            if (updated) {
                Toast.makeText(this, "🎶 Chanson modifiée avec succès !", Toast.LENGTH_SHORT).show();
                finish(); // Retour
            } else {
                Toast.makeText(this, "❌ Échec de la modification", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
