package com.lanjafii.tojk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SongsListActivity extends AppCompatActivity {

    private ListView songsListView;
    private ArrayList<Song> songsList;
    private DatabaseHelper dbHelper;
    private SongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);

        songsListView = findViewById(R.id.songsListView);
        dbHelper = new DatabaseHelper(this);

        loadSongs();

        // Clic pour voir les détails
        songsListView.setOnItemClickListener((parent, view, position, id) -> {
            Song selectedSong = songsList.get(position);

            Intent intent = new Intent(SongsListActivity.this, SongDetailsActivity.class);
            intent.putExtra("nom_chanson", selectedSong.nomChanson);
            intent.putExtra("genre", selectedSong.genre);
            intent.putExtra("tonalite", selectedSong.tonalite);
            intent.putExtra("poste_batterie", selectedSong.posteBatterie);
            intent.putExtra("poste_bass", selectedSong.posteBass);
            intent.putExtra("poste_solo", selectedSong.posteSolo);
            intent.putExtra("poste_clavier1", selectedSong.posteClavier1);
            intent.putExtra("poste_clavier2", selectedSong.posteClavier2);

            startActivity(intent);
        });

        // Clic long pour afficher un menu contextuel
        songsListView.setOnItemLongClickListener((parent, view, position, id) -> {
            Song selectedSong = songsList.get(position);

            PopupMenu popupMenu = new PopupMenu(SongsListActivity.this, view);
            popupMenu.getMenuInflater().inflate(R.menu.song_context_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.modify_song) {
                    Intent intent = new Intent(SongsListActivity.this, ModifySongActivity.class);
                    intent.putExtra("song_id", selectedSong.id);
                    startActivity(intent);
                    return true;

                } else if (item.getItemId() == R.id.delete_song) {
                    showDeleteConfirmation(selectedSong);
                    return true;
                }
                return false;
            });

            popupMenu.show();
            return true;
        });
    }

    private void loadSongs() {
        songsList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllSongs();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Song song = new Song(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nom_chanson")),
                        cursor.getString(cursor.getColumnIndexOrThrow("genre")),
                        cursor.getString(cursor.getColumnIndexOrThrow("tonalite")),
                        cursor.getString(cursor.getColumnIndexOrThrow("poste_batterie")),
                        cursor.getString(cursor.getColumnIndexOrThrow("poste_bass")),
                        cursor.getString(cursor.getColumnIndexOrThrow("poste_solo")),
                        cursor.getString(cursor.getColumnIndexOrThrow("poste_clavier1")),
                        cursor.getString(cursor.getColumnIndexOrThrow("poste_clavier2"))
                );
                songsList.add(song);
            } while (cursor.moveToNext());

            cursor.close();
        }

        adapter = new SongAdapter(this, songsList);
        songsListView.setAdapter(adapter);
    }

    private void showDeleteConfirmation(Song song) {
        new AlertDialog.Builder(this)
                .setTitle("Supprimer la chanson ?")
                .setMessage("🗑️ Tu veux vraiment supprimer \"" + song.nomChanson + "\" ?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    boolean deleted = dbHelper.deleteSongById(song.id);

                    if (deleted) {
                        Toast.makeText(this, "Chanson supprimée 🧹", Toast.LENGTH_SHORT).show();
                        loadSongs(); // refresh
                    } else {
                        Toast.makeText(this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }
}
