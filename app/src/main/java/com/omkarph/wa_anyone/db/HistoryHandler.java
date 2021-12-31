package com.omkarph.wa_anyone.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends SQLiteOpenHelper {
    public HistoryHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDB = "CREATE TABLE " + Params.TABLE_NAME + "( "
                + Params.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Params.DATE_TIME + " TEXT, "
                + Params.PHONE + " TEXT" + " )";
        Log.d("historyDB", createDB);
        db.execSQL(createDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addEntry(Entry entry){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.DATE_TIME,entry.getDATE_TIME());
        values.put(Params.PHONE,entry.getPHONE());

        db.insert(Params.TABLE_NAME, null, values);
        Log.d("historyDB", "Successfully inserted new entry");
        db.close();
    }

    public List<Entry> getEntries(){
        List<Entry> history = new ArrayList<Entry>();
        SQLiteDatabase db = this.getReadableDatabase();

        String fetchQuery = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(fetchQuery, null);

        if(!cursor.moveToFirst())
            return history;
        do{
            history.add(new Entry(cursor.getString(2), cursor.getString(1)));
        }while(cursor.moveToNext());

        return history;
    }


}
