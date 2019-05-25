package com.mrfox.senyast4745.articleservice.forms;

public class UpdateAllForm {

    private Long id;
    private String articleName;
    private String text;
    private String tags;

    public UpdateAllForm(Long id, String articleName, String text, String tags) {
        this.id = id;
        this.articleName = articleName;
        this.text = text;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
