package com.example.jeason.swipe.DummyData;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.jeason.swipe.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DatabaseTable {

    //The columns we'll include in the dictionary table
    public static final String COL_WORD = "WORD";
    public static final String COL_DEFINITION = "DEFINITION";
    private static final String TAG = "DictionaryDatabase";
    private static final String DATABASE_NAME = "DICTIONARY";
    private static final String FTS_VIRTUAL_TABLE = "FTS";
    private static final int DATABASE_VERSION = 1;
    private final DatabaseOpenHelper mDatabaseOpenHelper;
    private  Context context;

    public DatabaseTable(Context context) {
        mDatabaseOpenHelper = new DatabaseOpenHelper(context);
        this.context = context;
    }

    public Cursor getWordMatches(String query) {
        String selection = "docid AS _id," + COL_WORD + " MATCH ?";
        String[] selectionArgs = new String[]{query + "*"};
//        String[] columns = new String[]{"rowid", COL_WORD};//projection
//        return query(selection, selectionArgs, columns);


////////////////////below is my testing code///////////////
////        refer to https://stackoverflow.com/questions/29815248/full-text-search-example-in-android
        SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
        //refer to https://groups.google.com/forum/#!topic/sds_2012/Em73NZpEz1I,,the _id thing took me 2 days.

//      Cursor cursor = db.rawQuery("SELECT docid AS _id," + COL_WORD + "," + COL_DEFINITION + " FROM " + FTS_VIRTUAL_TABLE + " WHERE " + COL_WORD + " MATCH ?", selectionArgs);
//        sql rawQuery refer to http://www.sqlitetutorial.net/sqlite-full-text-search/
        Cursor cursor = db.rawQuery("SELECT docid AS _id," + "*"+ " FROM " + FTS_VIRTUAL_TABLE + " WHERE " + FTS_VIRTUAL_TABLE + " MATCH ?", selectionArgs);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            Toast.makeText(context,"No match were found",Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Invalid cursor");
            return null;
        }
        return cursor;
        //TODO, in next update,change db.rawQuery to dbHelper.query refer to https://developer.android.com/training/data-storage/sqlite#java
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE);
        Cursor cursor = builder.query(mDatabaseOpenHelper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);


        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    private static class DatabaseOpenHelper extends SQLiteOpenHelper {
        private static final String FTS_TABLE_CREATE = "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE + " USING fts4 (" + COL_WORD + ", " + COL_DEFINITION + ")";

        private final Context mHelperContext;
        private SQLiteDatabase mDatabase;

        DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            mDatabase.execSQL(FTS_TABLE_CREATE);
            loadDictionary();
        }

        private void loadDictionary() {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        loadWords();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }

        private void loadWords() throws IOException {
            Resources resources = mHelperContext.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.definitions);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] strings = TextUtils.split(line, "-");
                    if (strings.length < 2) continue;
                    long id = addWord(strings[0].trim(), strings[1].trim());
                    if (id < 0) {
                        Log.e(TAG, "can not add word: " + strings[0].trim());
                    }
                }
            } finally {
                reader.close();
            }
        }

        private long addWord(String word, String definition) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(COL_WORD, word);
            initialValues.put(COL_DEFINITION, definition);
            return mDatabase.insert(FTS_VIRTUAL_TABLE, null, initialValues);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(db);
        }
    }
}
