package com.lanjafii.tojk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;



public class DatabaseHelper extends SQLiteOpenHelper {

    // Nom et version de la base de données
    private static final String DATABASE_NAME = "tojk_db";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_SPEAKER = "porte_parole";
    public static final String COLUMN_THEME = "theme";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_ID = "id";
    public static final String TABLE_TEACHINGS = "teachings";
    public static final String TABLE_SONGS = "songs";




    // Constructeur
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Création des tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table pour les enseignements
        String CREATE_TABLE_TEACHINGS = "CREATE TABLE teachings (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "porte_parole TEXT, " +
                "theme TEXT, " +
                "date TEXT, " +
                "versets TEXT, " +
                "resume TEXT" +
                ");";

        // Table pour les chansons
        String CREATE_TABLE_SONGS = "CREATE TABLE songs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom_chanson TEXT, " +
                "genre TEXT, " +
                "tonalite TEXT, " +
                "poste_batterie TEXT, " +
                "poste_bass TEXT, " +
                "poste_solo TEXT, " +
                "poste_clavier1 TEXT, " +
                "poste_clavier2 TEXT" +
                ");";

        // Exécution des commandes de création des tables
        db.execSQL(CREATE_TABLE_TEACHINGS);
        db.execSQL(CREATE_TABLE_SONGS);
    }

    // Mise à jour de la base de données (au cas où la version change)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Supprimer les anciennes tables
        db.execSQL("DROP TABLE IF EXISTS teachings");
        db.execSQL("DROP TABLE IF EXISTS songs");

        // Recréer les tables
        onCreate(db);
    }


    public boolean insertSong(String songName, String genre, String tonalite,
                              String batterie, String basse, String solo,
                              String clavier1, String clavier2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom_chanson", songName);
        contentValues.put("genre", genre);
        contentValues.put("tonalite", tonalite);
        contentValues.put("poste_batterie", batterie);
        contentValues.put("poste_bass", basse);
        contentValues.put("poste_solo", solo);
        contentValues.put("poste_clavier1", clavier1);
        contentValues.put("poste_clavier2", clavier2);

        long result = db.insert("songs", null, contentValues);
        return result != -1; // Retourne true si l'insertion est réussie
    }


    public Cursor getAllSongs() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SONGS + " ORDER BY nom_chanson ASC", null);
    }

    public boolean deleteSongById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("songs", "id = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public Song getSongById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("songs", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
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
            cursor.close();
            return song;
        }
        return null;
    }

    public boolean updateSong(int id, String nom, String genre, String tonalite,
                              String batterie, String bass, String solo, String clavier1, String clavier2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom_chanson", nom);
        values.put("genre", genre);
        values.put("tonalite", tonalite);
        values.put("poste_batterie", batterie);
        values.put("poste_bass", bass);
        values.put("poste_solo", solo);
        values.put("poste_clavier1", clavier1);
        values.put("poste_clavier2", clavier2);

        int rows = db.update("songs", values, "id = ?", new String[]{String.valueOf(id)});
        return rows > 0;
    }




    public boolean insertTeaching(String porteParole, String theme, String date, String versets, String resume) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("porte_parole", porteParole);
        contentValues.put("theme", theme);
        contentValues.put("date", date);
        contentValues.put("versets", versets);
        contentValues.put("resume", resume);

        long result = db.insert("teachings", null, contentValues);
        return result != -1; // Retourne true si l'insertion est réussie
    }

    public Cursor getAllTeachings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT id AS _id, porte_parole, theme, date, versets, resume FROM "
                        + TABLE_TEACHINGS + " ORDER BY id DESC", null);
    }


    public Teaching getTeachingById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TEACHINGS, null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Teaching teaching = new Teaching(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")), // ici on utilise bien "id"
                    cursor.getString(cursor.getColumnIndexOrThrow("porte_parole")),
                    cursor.getString(cursor.getColumnIndexOrThrow("theme")),
                    cursor.getString(cursor.getColumnIndexOrThrow("date")),
                    cursor.getString(cursor.getColumnIndexOrThrow("versets")),
                    cursor.getString(cursor.getColumnIndexOrThrow("resume"))
            );
            cursor.close();
            return teaching;
        }
        return null;
    }


    public boolean updateTeaching(int id, String porteParole, String theme, String date, String versets, String resume) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("porte_parole", porteParole);
        values.put("theme", theme);
        values.put("date", date);
        values.put("versets", versets);
        values.put("resume", resume);
        int rows = db.update(TABLE_TEACHINGS, values, "id = ?", new String[]{String.valueOf(id)});
        return rows > 0;
    }


    public void deleteTeachingById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("teachings", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}
