package com.mrfox.senyast4745.articleservice.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MinimalForm {

    private Long id;

    @JsonCreator
    public MinimalForm(@JsonProperty("id")Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
