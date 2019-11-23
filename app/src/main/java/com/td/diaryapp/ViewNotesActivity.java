package com.td.diaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.td.diaryapp.contentprovider_sqliteDB.Note;
import com.td.diaryapp.contentprovider_sqliteDB.NoteContentProvider;
import com.td.diaryapp.contentprovider_sqliteDB.NoteDBHelper;

import java.util.ArrayList;
import java.util.List;

public class ViewNotesActivity extends AppCompatActivity {

    private ListView lvNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        lvNotes = findViewById(R.id.lvNotes);

        //create ArrayAdapter
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getNotes());

        lvNotes.setAdapter(adapter);
    }

    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<>();

        //create an instance to contentResolver
        ContentResolver contentResolver = getContentResolver();

        ///create cursor
        Cursor cr = contentResolver.query(NoteContentProvider.CONTENT_URI,
                null, null, null, null);

        if (cr != null && cr.moveToFirst()) {
            do {

                //get the variables
                int id = cr.getInt(cr.getColumnIndex(NoteDBHelper.COLUMN_ID));
                String title = cr.getString(cr.getColumnIndex(NoteDBHelper.COLUMN_TITLE));
                String content = cr.getString(cr.getColumnIndex(NoteDBHelper.COLUMN_CONTENT));

                //create an instance of Note
                Note n = new Note();

                //set the properties
                n.setId(id);
                n.setTitle(title);
                n.setContent(content);

                //add note to list
                notes.add(n);

            } while (cr.moveToNext());
        }

        //close the cursor
        cr.close();

        return notes;
    }
}
