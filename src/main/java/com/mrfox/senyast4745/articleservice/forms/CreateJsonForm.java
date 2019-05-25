package com.mrfox.senyast4745.articleservice.forms;

public class CreateJsonForm {

    private String articleName;
    private String text;
    private String tags;
    private long creatorId;

    public CreateJsonForm(String articleName, String text, String tags, long creatorId) {
        this.articleName = articleName;
        this.text = text;
        this.tags = tags;
        this.creatorId = creatorId;
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

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }
}
