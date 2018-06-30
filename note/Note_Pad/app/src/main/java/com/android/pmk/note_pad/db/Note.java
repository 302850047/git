package com.android.pmk.note_pad.db;

/**
 *
 */

public class Note {

    private int id;
    private int color;
    private String title;
    private String content;
    private long modifyTime;
    private String type;

    public Note(){

    }
    public Note(int id, String title, String content, long modifyTime,String type) {
        this.id = id;
        this.color = 0;
        this.title = title;
        this.content = content;
        this.modifyTime = modifyTime;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
