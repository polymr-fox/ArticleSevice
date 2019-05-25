package com.mrfox.senyast4745.articleservice.forms;

import com.mrfox.senyast4745.articleservice.model.ArticleModel;

public class ResponseJsonForm {

    private Iterable<ArticleModel> articles;

    public ResponseJsonForm(Iterable<ArticleModel> articles) {
        this.articles = articles;
    }

    public Iterable<ArticleModel> getArticles() {
        return articles;
    }

    public void setArticles(Iterable<ArticleModel> articles) {
        this.articles = articles;
    }
}
