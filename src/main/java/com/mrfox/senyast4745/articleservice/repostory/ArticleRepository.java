package com.mrfox.senyast4745.articleservice.repostory;

import com.mrfox.senyast4745.articleservice.model.ArticleModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleModel, Long> {
    // todo Iterable<ArticleModel> findAllByTags(String tag);
    Iterable< ArticleModel> findAllByCreatorId(Long id);
    Iterable<ArticleModel> findByArticleName(String name);
    Iterable<ArticleModel> findAllByDate(Date date);
    Iterable<ArticleModel> findAllByRating(Integer rating);
    Iterable<ArticleModel> findAllByRatingOrderByRating(Integer rating);
    Iterable<ArticleModel> findByArticleNameOrderByRating(String name);
    Iterable<ArticleModel> findByArticleNameOrderByDate(String name);
}
