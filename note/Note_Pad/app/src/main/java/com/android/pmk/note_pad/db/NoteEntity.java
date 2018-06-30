package com.android.pmk.note_pad.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derrick on 2018/6/30.
 */

public class NoteEntity {

    private List<Note> mNote = new ArrayList<>();
    private String type;

    public NoteEntity(){

    }


    public List<Note> getNote() {
        return mNote;
    }

    public void setNote(List<Note> note) {
        mNote = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
