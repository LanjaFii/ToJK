package com.lanjafii.tojk;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;


import androidx.appcompat.app.AppCompatActivity;

public class TeachingListActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelper dbHelper;
    private TeachingAdapter teachingAdapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teaching_list);

        // Initialisation de la base de données
        dbHelper = new DatabaseHelper(this);

        // Initialisation de la ListView
        listView = findViewById(R.id.teachingListView);

        // Charger les enseignements depuis la base de données
        loadTeachings();

        // Gérer le clic sur un élément de la liste
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Récupérer l'enseignement sélectionné
                Cursor cursor = (Cursor) teachingAdapter.getItem(position);
                if (cursor != null && cursor.moveToPosition(position)) { // Vérification
                    int teachingId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

                    // Ouvrir une activité de détails pour cet enseignement
                    Intent intent = new Intent(TeachingListActivity.this, TeachingDetailActivity.class);
                    intent.putExtra("TEACHING_ID", teachingId);
                    startActivity(intent);
                } else {
                    Toast.makeText(TeachingListActivity.this, "Erreur de sélection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) teachingAdapter.getItem(position);
                if (cursor != null && cursor.moveToPosition(position)) {
                    int teachingId = cursor.getInt(cursor.getColumnIndexOrThrow("_id")); // très important ici

                    // Affiche un menu contextuel
                    showOptionsDialog(teachingId);
                }
                return true;
            }
        });

    }

    // Méthode pour charger les enseignements dans le ListView
    private void loadTeachings() {
        // Fermer le curseur précédent s'il existe
        if (cursor != null) {
            cursor.close();
        }

        // Récupérer les enseignements à partir de la base de données
        cursor = dbHelper.getAllTeachings();

        // Vérifier si des données existent
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(this, "Aucun enseignement trouvé", Toast.LENGTH_SHORT).show();
            return;
        }

        // Créer un adapter personnalisé
        teachingAdapter = new TeachingAdapter(this, cursor);

        // Lier l'adapter au ListView
        listView.setAdapter(teachingAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Fermer le curseur pour éviter les fuites de mémoire
        if (cursor != null) {
            cursor.close();
        }
    }

    private void showOptionsDialog(int teachingId) {
        new AlertDialog.Builder(this)
                .setTitle("Choisir une action")
                .setItems(new String[]{"Modifier", "Supprimer"}, (dialog, which) -> {
                    if (which == 0) {
                        // Modifier
                        Intent intent = new Intent(TeachingListActivity.this, EditTeachingActivity.class);
                        intent.putExtra("TEACHING_ID", teachingId);
                        startActivity(intent);
                    } else if (which == 1) {
                        // Supprimer
                        dbHelper.deleteTeachingById(teachingId);
                        showSuccessPopup("Suppression réussie");
                        refreshList();
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void showSuccessPopup(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Succès")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void refreshList() {
        Cursor newCursor = dbHelper.getAllTeachings();
        teachingAdapter.changeCursor(newCursor);
    }

}
