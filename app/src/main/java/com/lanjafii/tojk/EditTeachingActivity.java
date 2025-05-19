package com.lanjafii.tojk;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class EditTeachingActivity extends AppCompatActivity {

    private EditText editPerson, editTheme, editDate, editSummary;
    private LinearLayout versesContainer;
    private Button btnAddVerse, btnSaveTeaching;

    private int teachingId;
    private DatabaseHelper dbHelper;
    private String currentVersets = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teaching);

        // Récupérer l'ID depuis l'intent
        teachingId = getIntent().getIntExtra("TEACHING_ID", -1);

        // Initialisation
        dbHelper = new DatabaseHelper(this);
        editPerson = findViewById(R.id.editPerson);
        editTheme = findViewById(R.id.editTheme);
        editDate = findViewById(R.id.editDate);
        editSummary = findViewById(R.id.editSummary);
        versesContainer = findViewById(R.id.versesContainer);
        btnAddVerse = findViewById(R.id.btnAddVerse);
        btnSaveTeaching = findViewById(R.id.btnSaveTeaching);

        // Pré-remplir les données existantes
        Teaching teaching = dbHelper.getTeachingById(teachingId);
        if (teaching != null) {
            editPerson.setText(teaching.getPorteParole());
            editTheme.setText(teaching.getTheme());
            editDate.setText(teaching.getDate());
            editSummary.setText(teaching.getResume());
            currentVersets = teaching.getVersets();

            // Afficher les versets
            for (String verse : currentVersets.split("\n")) {
                addVerseField(verse);
            }
        }

        // Sélecteur de date
        editDate.setOnClickListener(view -> showDatePicker());

        // Ajouter un champ verset
        btnAddVerse.setOnClickListener(view -> addVerseField(""));

        // Sauvegarder les modifications
        btnSaveTeaching.setOnClickListener(view -> {
            String versets = collectVersets();

            boolean updated = dbHelper.updateTeaching(
                    teachingId,
                    editPerson.getText().toString(),
                    editTheme.getText().toString(),
                    editDate.getText().toString(),
                    versets,
                    editSummary.getText().toString()
            );

            if (updated) {
                showSuccessDialog("Modification réussie !");
            } else {
                Toast.makeText(this, "Échec de la modification", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addVerseField(String value) {
        EditText verseField = new EditText(this);
        verseField.setHint("Verset biblique");
        verseField.setText(value);
        verseField.setBackgroundResource(R.drawable.rounded_edittext);
        verseField.setPadding(10, 10, 10, 10);
        versesContainer.addView(verseField);
    }

    private String collectVersets() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < versesContainer.getChildCount(); i++) {
            View view = versesContainer.getChildAt(i);
            if (view instanceof EditText) {
                String text = ((EditText) view).getText().toString().trim();
                if (!text.isEmpty()) {
                    sb.append(text).append("\n");
                }
            }
        }
        return sb.toString().trim();
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
            editDate.setText(date);
        }, year, month, day).show();
    }

    private void showSuccessDialog(String message) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("✅ Succès")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

}
