package com.mrfox.senyast4745.articleservice.dao;


import com.mrfox.senyast4745.articleservice.model.ArticleModel;
import com.mrfox.senyast4745.articleservice.model.TagModel;
import com.mrfox.senyast4745.articleservice.model.UserModel;
import com.mrfox.senyast4745.articleservice.repostory.ArticleRepository;
import com.mrfox.senyast4745.articleservice.repostory.TagsRepository;
import com.mrfox.senyast4745.articleservice.repostory.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Date;

@Component
public class ArticlesDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticlesDAO.class);

    private final ArticleRepository articleRepository;
    private final TagsRepository tagsRepository;
    private final UserRepository userRepository;

    public ArticlesDAO(ArticleRepository articleRepository, TagsRepository tagsRepository,
                       UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.tagsRepository = tagsRepository;
        this.userRepository = userRepository;
    }

    public ArticleModel create(Long creatorId, String articleName, String articleText, String[] tags, int rating, Date date) {
        if (tags.length == 0) {
            throw new IllegalArgumentException("No tags found");
        }
        for (String tmp : tags) {
            Iterable<TagModel> tmpTag = tagsRepository.findAllByTagName(tmp);
            if (!tmpTag.iterator().hasNext()) {
                tagsRepository.save(new TagModel(tmp));
            }
        }
        ArticleModel tmp = new ArticleModel(creatorId, articleName, articleText, tags, rating, date);

        return articleRepository.save(tmp);

    }

    public ArticleModel updateRating(Long id, int rating) {
        return updateAll(id, null, null, null, rating);
    }

    public ArticleModel updateName(Long id, String articleName) {
        return updateAll(id, articleName, null, null, 0);
    }

    public ArticleModel updateAll(Long id,  String articleName, String articleText, String[] tags, int rating){
        ArticleModel tmp = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Article with id " + id + " not exist."));
        if(articleName != null){
            tmp.setArticleName(articleName);
        }
        if(articleText != null){
            tmp.setArticleText(articleText);
        }
        if(tags!= null){
            tmp.setTags(tags);
        }
        int articleRating = tmp.getRating();
        articleRating += rating;
        tmp.setRating(articleRating);
        return articleRepository.save(tmp);

    }

    public Iterable<ArticleModel> findAll(){
        Iterable<ArticleModel> articleModels = articleRepository.findAll();
        if (!articleModels.iterator().hasNext()) {
            throw new IllegalArgumentException("Articles not exist yet.");
        }
        return articleModels;
    }


    private Iterable<ArticleModel> findAllByCreatorId(Long creatorId) {
        Iterable<ArticleModel> articleModels = articleRepository.findAllByCreatorId(creatorId);
        if (!articleModels.iterator().hasNext()) {
            throw new IllegalArgumentException("User with id " + creatorId + " has not created an article yet.");
        }
        return articleModels;
    }
    private Iterable<ArticleModel> findAllByCreatorIdOrderByRating(Long creatorId) {
        Iterable<ArticleModel> articleModels = articleRepository.findAllByCreatorIdOrderByRating(creatorId);
        if (!articleModels.iterator().hasNext()) {
            throw new IllegalArgumentException("User with id " + creatorId + " has not created an article yet.");
        }
        return articleModels;
    }

    public Iterable<ArticleModel> findAllByRating(int rating) {
        Iterable<ArticleModel> articleModels = articleRepository.findAllByRating(rating);
        if (!articleModels.iterator().hasNext()) {
            throw new IllegalArgumentException("Articles with rating" + rating + " not exist.");
        }
        return articleModels;
    }

    public Iterable<ArticleModel> findAllByCreatorFullName(String fullName) {
        Iterable<UserModel> userModels = userRepository.findByFullName(fullName);
        ArrayList <ArticleModel> articleModels = new ArrayList<>();
        for (UserModel u : userModels) {
             findAllByCreatorId(u.getUserId()).forEach(articleModels::add);
        }
        if(articleModels.isEmpty()){
            throw new IllegalArgumentException("Users with full " + fullName + " has not created an article yet.");
        }
        return articleModels;
    }

    public Iterable<ArticleModel> findAllByCreatorFullNameOrderByRating(String fullName) {
        Iterable<UserModel> userModels = userRepository.findByFullName(fullName);
        ArrayList <ArticleModel> articleModels = new ArrayList<>();
        for (UserModel u : userModels) {
            findAllByCreatorIdOrderByRating(u.getUserId()).forEach(articleModels::add);
        }
        if(articleModels.isEmpty()){
            throw new IllegalArgumentException("Users with full " + fullName + " has not created an article yet.");
        }
        return articleModels;
    }

    public void deleteById(Long id){
        try {
            articleRepository.deleteById(id);
        } catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    /*public Iterable<ArticleModel> findAllByTag(String tags){

    }*/
}
