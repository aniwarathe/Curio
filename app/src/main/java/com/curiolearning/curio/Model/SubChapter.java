package com.curiolearning.curio.Model;

/**
 * Created by Chinthaka on 12/17/2017.
 */

public class SubChapter  {
    private int id;
    private String subChapterId;
    private String name;
    public SubChapter() {
    }

    public SubChapter(int id,String subChapterId, String name) {
        this.id=id;
        this.subChapterId=subChapterId;
        this.name=name;
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
}
