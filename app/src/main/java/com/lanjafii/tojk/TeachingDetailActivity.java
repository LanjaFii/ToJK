package com.lanjafii.tojk;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TeachingDetailActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView teachingTitle, teachingSpeaker, teachingDate, teachingTheme, teachingVersets, teachingSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_detail);

        // Initialiser les TextViews
        teachingTitle = findViewById(R.id.teachingTitle);
        teachingSpeaker = findViewById(R.id.teachingSpeaker);
        teachingDate = findViewById(R.id.teachingDate);
        teachingTheme = findViewById(R.id.teachingTheme);
        teachingVersets = findViewById(R.id.teachingVersets);
        teachingSummary = findViewById(R.id.teachingSummary);

        // Initialiser la base de données
        dbHelper = new DatabaseHelper(this);

        // Récupérer l'ID de l'enseignement passé via l'intent
        int teachingId = getIntent().getIntExtra("TEACHING_ID", -1);

        if (teachingId != -1) {
            // Charger les détails de l'enseignement
            loadTeachingDetails(teachingId);
        } else {
            Toast.makeText(this, "Erreur : ID d'enseignement non valide", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadTeachingDetails(int teachingId) {
        Cursor cursor = dbHelper.getAllTeachings();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id")); // <-- ici le changement

                if (id == teachingId) {
                    teachingTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_THEME)));
                    teachingSpeaker.setText("Porte-parole : " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SPEAKER)));
                    teachingDate.setText("Date : " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE)));
                    teachingTheme.setText("Thème : " + cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_THEME)));
                    teachingVersets.setText("Versets : " + cursor.getString(cursor.getColumnIndexOrThrow("versets")));
                    teachingSummary.setText("Résumé : " + cursor.getString(cursor.getColumnIndexOrThrow("resume")));
                    break;
                }
            }
            cursor.close();
        }
    }

}
