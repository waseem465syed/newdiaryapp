package com.td.diaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.td.diaryapp.MyLibraries.CommonRoutines;
import com.td.diaryapp.contentprovider_sqliteDB.NoteContentProvider;
import com.td.diaryapp.contentprovider_sqliteDB.NoteDBHelper;


public class NewNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtTitle;
    private EditText edtContent;

    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        edtTitle = findViewById(R.id.edtTitle);

        edtContent = findViewById(R.id.edtContent);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        if (v == btnSave)
        {
            //create contentvaslues
            ContentValues cv = new ContentValues();

            cv.put(NoteDBHelper.COLUMN_TITLE, edtTitle.getText().toString());
            cv.put(NoteDBHelper.COLUMN_CONTENT, edtContent.getText().toString());

            //save the note
            Uri uri = getContentResolver().insert(NoteContentProvider.CONTENT_URI, cv);

            //get the result
            String insertResult = uri.getLastPathSegment();

            if (insertResult != "-1") {

                //saved successfully
                CommonRoutines.displayMessage(this, null, "Note saved successfully", 0, Toast.LENGTH_LONG);

                //reset
                edtTitle.setText("");
                edtContent.setText("");
            } else {
                CommonRoutines.displayMessage(this, null, "Error, note not saved. Pls contact IT Support", 0, Toast.LENGTH_LONG);

            }
        }
    }
}
