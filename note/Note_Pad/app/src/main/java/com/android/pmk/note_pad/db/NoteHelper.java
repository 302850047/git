package com.android.pmk.note_pad.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.pmk.note_pad.db.NoteSchema.NoteTable;

/**
 *
 */

public class NoteHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "noteDB.db";
    private Context mContenxt;
    public NoteHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
        this.mContenxt = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table "+ NoteTable.NAME + "("+
                "id integer primary key autoincrement," +
                NoteTable.Cols.COLOR +","+
                NoteTable.Cols.TITLE + ","+
                NoteTable.Cols.CONTENT + ","+
                NoteTable.Cols.MODIFYTIME + ","+
                NoteTable.Cols.TYPE +")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
