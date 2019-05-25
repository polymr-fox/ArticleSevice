package com.mrfox.senyast4745.articleservice.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateAllForm {

    private Long id;
    private String articleName;
    private String text;
    private String[] tags;

    @JsonCreator
    public UpdateAllForm(@JsonProperty("id")Long id, @JsonProperty("articleName")String articleName,
                         @JsonProperty("text")String text,@JsonProperty("tags") String[] tags) {
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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
