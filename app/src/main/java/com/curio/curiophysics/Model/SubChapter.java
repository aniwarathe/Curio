package com.curio.curiophysics.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chinthaka on 12/17/2017.
 */

public class SubChapter  {
    private int id;
    private String subChapterId;
    private String name;
    private String color;
    public SubChapter() {
    }

    public SubChapter(int id,String subChapterId, String name,String color) {
        this.id=id;
        this.subChapterId=subChapterId;
        this.name=name;
        this.color=color;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubChapterId() {
        return subChapterId;
    }

    public void setSubChapterId(String subChapterId) {
        this.subChapterId = subChapterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
