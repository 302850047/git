package com.android.pmk.note_pad.db;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Created by Derrick on 2018/6/30.
 */

public class NoteCursorWrapper extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote(){
        int id = getInt(getColumnIndex(NoteSchema.NoteTable.Cols.ID));
        int color = getInt(getColumnIndex(NoteSchema.NoteTable.Cols.COLOR));
        String title = getString(getColumnIndex(NoteSchema.NoteTable.Cols.TITLE));
        String content = getString(getColumnIndex(NoteSchema.NoteTable.Cols.CONTENT));
        long modifytime = getLong(getColumnIndex(NoteSchema.NoteTable.Cols.MODIFYTIME));
        String type = getString(getColumnIndex(NoteSchema.NoteTable.Cols.TYPE));

        Note book = new Note(id,title,content,modifytime,type);
        book.setColor(color);
        return book;
    }
}
