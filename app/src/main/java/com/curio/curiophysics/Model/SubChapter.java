package com.curio.curiophysics.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chinthaka on 12/17/2017.
 */

public class SubChapter  {
    int id;
    String name;
    public SubChapter() {
    }

    public SubChapter(int id, String name) {
        this.id=id;
        this.name=name;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
