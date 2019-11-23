package com.td.diaryapp.contentprovider_sqliteDB;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class NoteContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.td.olu.diaryapp.contentprovider";

    private static final String BASE_PATH = "note";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    private static final int NOTES = 1;
    private static final int NOTE_ID = 2;

    //create uri matcher
    public static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    //dbhelper
    private NoteDBHelper noteDBHelper;

    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, NOTES);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", NOTE_ID);
    }

    public NoteContentProvider() {
    }

    @Override
    public boolean onCreate() {

        noteDBHelper = new NoteDBHelper(getContext());

        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        //create sqlitequerybuilder
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        //set the table
        queryBuilder.setTables(NoteDBHelper.TABLE_NAME);

        //get the uri
        int uriType = sURIMatcher.match(uri);

        switch(uriType)
        {
            case NOTES:
                break;

            case NOTE_ID:
                //append to query
                queryBuilder.appendWhere(NoteDBHelper.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Query method has unknown URI: " + uri);
        }

        SQLiteDatabase db = noteDBHelper.getReadableDatabase();

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        //notify listeners
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int uriType = sURIMatcher.match(uri);

        SQLiteDatabase db = noteDBHelper.getWritableDatabase();

        long id = 0;

        switch (uriType){

            case NOTES:

                id = db.insert(NoteDBHelper.TABLE_NAME, null, values);

                break;

            default:
                throw new IllegalArgumentException("Insert method has unknown URI: " + uri);
        }


        //notify listeners
        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(BASE_PATH + "/" + id);

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);

        SQLiteDatabase db = noteDBHelper.getWritableDatabase();

        int id = 0;

        switch (uriType){

            case NOTES:

                id = db.update(NoteDBHelper.TABLE_NAME, values, selection, selectionArgs);

                break;

            case NOTE_ID:

                String path = uri.getLastPathSegment();

                if (TextUtils.isEmpty(selection)) {

                    //no where clause
                    id = db.update(NoteDBHelper.TABLE_NAME, values, NoteDBHelper.COLUMN_ID + "=" + path, selectionArgs);
                }
                else
                {
                    id = db.update(NoteDBHelper.TABLE_NAME, values, NoteDBHelper.COLUMN_ID + "=" + path + selection, selectionArgs);

                }

            default:
                throw new IllegalArgumentException("Update method has unknown URI: " + uri);
        }


        //notify listeners
        getContext().getContentResolver().notifyChange(uri, null);

        return id;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {


        int uriType = sURIMatcher.match(uri);

        SQLiteDatabase db = noteDBHelper.getWritableDatabase();

        int id = 0;

        switch (uriType){

            case NOTES:

                id = db.delete(NoteDBHelper.TABLE_NAME, selection, selectionArgs);

                break;

            case NOTE_ID:

                String path = uri.getLastPathSegment();

                if (TextUtils.isEmpty(selection)) {

                    //no where clause
                    id = db.delete(NoteDBHelper.TABLE_NAME, NoteDBHelper.COLUMN_ID + "=" + path, selectionArgs);
                }
                else
                {
                    id = db.delete(NoteDBHelper.TABLE_NAME, NoteDBHelper.COLUMN_ID + "=" + path + selection, selectionArgs);

                }

            default:
                throw new IllegalArgumentException("Update method has unknown URI: " + uri);
        }


        //notify listeners
        getContext().getContentResolver().notifyChange(uri, null);

        return id;
    }
}
