package com.mrfox.senyast4745.articleservice.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateForm {

    private String articleName;
    private String text;
    private String[] tags;

    @JsonCreator
    public CreateForm(@JsonProperty("articleName") String articleName, @JsonProperty("text") String text,
                      @JsonProperty("tags") String[] tags) {
        this.articleName = articleName;
        this.text = text;
        this.tags = tags;
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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
