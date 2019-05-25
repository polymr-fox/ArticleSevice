package com.mrfox.senyast4745.articleservice.model;


import com.mrfox.senyast4745.articleservice.converters.SimpleConverter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "articles")
public class ArticleModel {

    @Id
    @GeneratedValue
    @Column(name = "article_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(name = "article_name", nullable = false)
    private String articleName;

    @Column(name = "article_text", nullable = false, unique = true)
    private String articleText;

    @Column(name = "tags", nullable = false)
    @Convert(converter = SimpleConverter.class)
    private String[] tags;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;


    public ArticleModel() {
        super();
    }

    public ArticleModel(Long creatorId, String articleName, String articleText, String[] tags, int rating, Date date) {
        this.creatorId = creatorId;
        this.articleName = articleName;
        this.articleText = articleText;
        this.tags = tags;
        this.rating = rating;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
