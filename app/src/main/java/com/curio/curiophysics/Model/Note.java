package com.curio.curiophysics.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by chin on 12/12/2017.
 */

public class Note {
    private String note;
    private String anim;


    public Note() {
    }

    public Note(String note, String anim) {
        this.note = note;
        this.anim = anim;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAnim() {
        return anim;
    }

    public void setAnim(String anim) {
        this.anim = anim;
    }
}
