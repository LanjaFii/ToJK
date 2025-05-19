package com.lanjafii.tojk;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTeachingActivity extends AppCompatActivity {

    private EditText editPerson, editTheme, editDate, editSummary;
    private LinearLayout versesContainer;
    private Button btnAddVerse, btnSaveTeaching;
    private DatabaseHelper databaseHelper;
    private List<EditText> verseFields = new ArrayList<>(); // Liste des versets ajoutés

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teaching);

        databaseHelper = new DatabaseHelper(this);

        editPerson = findViewById(R.id.editPerson);
        editTheme = findViewById(R.id.editTheme);
        editDate = findViewById(R.id.editDate);
        editSummary = findViewById(R.id.editSummary);
        versesContainer = findViewById(R.id.versesContainer);
        btnAddVerse = findViewById(R.id.btnAddVerse);
        btnSaveTeaching = findViewById(R.id.btnSaveTeaching);

        // Gestion de la sélection de date
        editDate.setOnClickListener(v -> showDatePicker());

        // Ajouter un champ de verset dynamiquement
        btnAddVerse.setOnClickListener(v -> addVerseField());

        // Sauvegarder l'enseignement
        btnSaveTeaching.setOnClickListener(v -> saveTeaching());
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> editDate.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                year, month, day);
        datePickerDialog.show();
    }

    private void addVerseField() {
        EditText newVerse = new EditText(this);
        newVerse.setHint("Ex: Jean 3:16");
        newVerse.setMinHeight(48);
        newVerse.setPadding(10, 10, 10, 10);
        newVerse.setBackgroundResource(R.drawable.rounded_edittext);
        versesContainer.addView(newVerse);
        verseFields.add(newVerse);
    }

    private void saveTeaching() {
        String porteParole = editPerson.getText().toString().trim();
        String theme = editTheme.getText().toString().trim();
        String date = editDate.getText().toString().trim();
        String resume = editSummary.getText().toString().trim();

        if (porteParole.isEmpty() || theme.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        // Récupérer les versets ajoutés
        StringBuilder versetsBuilder = new StringBuilder();
        for (EditText verseField : verseFields) {
            String verse = verseField.getText().toString().trim();
            if (!verse.isEmpty()) {
                if (versetsBuilder.length() > 0) versetsBuilder.append(", ");
                versetsBuilder.append(verse);
            }
        }

        String versets = versetsBuilder.toString();

        // Insérer dans la base de données
        boolean isInserted = databaseHelper.insertTeaching(porteParole, theme, date, versets, resume);

        if (isInserted) {
            Toast.makeText(this, "Enseignement ajouté avec succès !", Toast.LENGTH_SHORT).show();
            finish(); // Ferme l'activité après l'ajout réussi
        } else {
            Toast.makeText(this, "Erreur lors de l'ajout de l'enseignement", Toast.LENGTH_SHORT).show();
        }
    }
}
