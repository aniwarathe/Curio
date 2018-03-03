package com.curiolearning.curio.Model;

/**
 * Created by chin on 12/12/2017.
 */

public class Note {
    private String note;
    private String title;
    private String anim;
    private String image;
    private String type;


    public Note() {
    }

    public Note(String note,String title, String anim,String image,String type) {
        this.note = note;
        this.title=title;
        this.anim = anim;
        this.image=image;
        this.type=type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnim() {
        return anim;
    }

    public void setAnim(String anim) {
        this.anim = anim;
    }

    public String getImage(){return image;}

    public void setImage(){this.image=image;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
