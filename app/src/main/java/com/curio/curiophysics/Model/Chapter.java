package com.curio.curiophysics.Model;

import com.curio.curiophysics.NewChapterActivity;
import java.util.List;

/**
 * Created by chin on 12/9/2017.
 */

public class Chapter  {
    public List<SubChapter> subChapters;
    int id;
    String name;
    String description;
    String color;

    public Chapter() {
    }

    public Chapter(List<SubChapter> subChapters, int id, String name, String description, String color) {
        this.subChapters = subChapters;
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public List<SubChapter> getsubChapters() {
        return subChapters;
    }

    public void setsubChapters(List<SubChapter> subChapters) {
        this.subChapters = subChapters;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}