package com.darknet.bvw.model;

public class PushModel {

    private String title;
    private String content;

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

    @Override
    public String toString() {
        return "PushModel{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
