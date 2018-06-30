package com.android.pmk.note_pad.db;

/**
 *
 */

public class NoteSchema {

    public static final class NoteTable{

        public static final String NAME = "notes";

        public static final class Cols{
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String COLOR = "color";
            public static final String CONTENT = "content";
            public static final String MODIFYTIME = "modifytime";
            public static final String TYPE = "type";

        }

    }
}
