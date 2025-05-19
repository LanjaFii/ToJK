package com.lanjafii.tojk;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TeachingAdapter extends CursorAdapter {

    public TeachingAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Créer une nouvelle vue à partir du fichier XML de l'élément de la liste
        return LayoutInflater.from(context).inflate(R.layout.list_item_teaching, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Lier les données de la base de données aux éléments de la vue
        TextView speakerTextView = view.findViewById(R.id.speakerTextView);
        TextView themeTextView = view.findViewById(R.id.themeTextView);
        TextView dateTextView = view.findViewById(R.id.dateTextView);

        // Extraire les informations de la base de données avec sécurité
        String speaker = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SPEAKER));
        String theme = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_THEME));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));

        // Afficher les informations dans les TextViews
        speakerTextView.setText(speaker);
        themeTextView.setText(theme);
        dateTextView.setText(date);
    }
}
