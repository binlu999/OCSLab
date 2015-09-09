package com.blogspot.vozis.springvelocity.data;

/**
 * Feed
 * @author sergej.sizov
 */
public class Feed {

    private Integer id;
    private String title;

    public Feed(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}