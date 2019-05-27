package com.mrfox.senyast4745.articleservice.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateRatingForm {

    private Long id;
    private int rating;

    @JsonCreator
    public UpdateRatingForm(@JsonProperty("id") Long id, @JsonProperty("rating") int rating) {

        this.id = id;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
