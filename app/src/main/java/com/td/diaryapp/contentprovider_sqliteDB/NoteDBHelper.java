package com.td.diaryapp.contentprovider_sqliteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NoteDBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "NoteDB";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "Note";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";

    public NoteDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //query
        String query =
                "Create Table " + TABLE_NAME +
                       "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_CONTENT + " TEXT)";

        //create the table
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //query
        String query = "Drop Table " + TABLE_NAME;

        //drop the table
        db.execSQL(query);

        //re-create
        onCreate(db);
    }

//    public void saveNote(Note n) throws SQLiteException{
//
//        //create an instance of content value
//        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_TITLE, n.getTitle());
//        cv.put(COLUMN_CONTENT, n.getContent());
//
//        //create a readable helper
//        SQLiteDatabase db = getWritableDatabase();
//
//        //save
//        db.insert(TABLE_NAME, null, cv);
//
//        //close the connection
//        db.close();
//
//    }
//
//    public List<Note> getNotes () throws SQLiteException{
//        List<Note> notes = new ArrayList<>();
//
//        //create an instance to read db
//        SQLiteDatabase db = getWritableDatabase();
//
//        //create a cursor
//        Cursor cr = db.query(TABLE_NAME, new String[] {COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT},
//                null, null, null, null, null);
//
//        if (cr != null){
//
//            if (cr.moveToFirst()){
//                do {
//
//                    //get the variables
//                    int id = cr.getInt(cr.getColumnIndex(COLUMN_ID));
//                    String title = cr.getString(cr.getColumnIndex(COLUMN_TITLE));
//                    String content = cr.getString(cr.getColumnIndex(COLUMN_CONTENT));
//
//                    //create an instance of Note
//                    Note n = new Note();
//
//                    //set the properties
//                    n.setId(id);
//                    n.setTitle(title);
//                    n.setContent(content);
//
//                    //add note to list
//                    notes.add(n);
//
//                } while(cr.moveToNext());
//            }
//
//            //close the cursor
//            cr.close();
//        }
//
//        //close the database
//        db.close();
//
//        return notes;
//    }
}
