package com.mrfox.senyast4745.articleservice.dao;


import com.mrfox.senyast4745.articleservice.model.ArticleModel;
import com.mrfox.senyast4745.articleservice.model.Role;
import com.mrfox.senyast4745.articleservice.model.TagModel;
import com.mrfox.senyast4745.articleservice.model.UserModel;
import com.mrfox.senyast4745.articleservice.repostory.ArticleRepository;
import com.mrfox.senyast4745.articleservice.repostory.TagsRepository;
import com.mrfox.senyast4745.articleservice.repostory.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

@Component
public class ArticlesDAO {

    @Value("${jwt.secretKey:hello}")
    private String secretKey;

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

    public ArticleModel create(String token, String articleName, String articleText, String[] tags, int rating, Date date) {
        if (tags.length == 0) {
            throw new IllegalArgumentException("No tags found");
        }
        Long creatorId = getUserIdFromToken(token);
        ArrayList<String> tmp = new ArrayList<>(Arrays.asList(tags));
        tmp.add(articleName);
        tmp.add(articleName);
        checkSymbols(tmp);
        checkingTags(tags);
        return articleRepository.save(new ArticleModel(creatorId, articleName, articleText, tags, rating, date));

    }

    public ArticleModel updateRating(String token, Long id, int rating) throws IllegalAccessException {
        return updateAll(token, id, null, null, null, rating);
    }

    public ArticleModel updateName(String token, Long id, String articleName) throws IllegalAccessException {
        return updateAll(token, id, articleName, null, null, 0);
    }

    public ArticleModel updateAll(String token, Long id, String articleName, String articleText, String[] tags, int rating) throws IllegalAccessException {

        checkAccess(id, getUserIdFromToken(token));
        ArticleModel articleModel = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Article with id " + id + " not exist."));
        ArrayList<String> tmp = new ArrayList<>();
        if (articleName != null) {
            tmp.add(articleName);
            checkSymbols(tmp);
            tmp.clear();
            articleModel.setArticleName(articleName);
        }
        if (articleText != null) {
            tmp.add(articleText);
            checkSymbols(tmp);
            tmp.clear();
            articleModel.setArticleText(articleText);
        }
        if (tags != null) {
            tmp.addAll(Arrays.asList(tags));
            checkSymbols(tmp);
            tmp.clear();
            checkingTags(tags);
            articleModel.setTags(tags);
        }
        int articleRating = articleModel.getRating();
        articleRating += rating;
        articleModel.setRating(articleRating);
        return articleRepository.save(articleModel);

    }

    private void checkingTags(String[] tags) {
        Iterable<TagModel> tmpTag = tagsRepository.findAll();
        for (String s : tags) {
            AtomicBoolean find = new AtomicBoolean(false);
            tmpTag.forEach((t) -> {
                if (t.getTagName().equals(s)) {
                    find.set(true);
                }
            });
            if (!find.get()) {
                tagsRepository.save(new TagModel(s));
            }
        }
    }

    public Iterable<ArticleModel> findAll() {
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
        ArrayList<ArticleModel> articleModels = new ArrayList<>();
        for (UserModel u : userModels) {
            findAllByCreatorId(u.getUserId()).forEach(articleModels::add);
        }
        if (articleModels.isEmpty()) {
            throw new IllegalArgumentException("Users with full " + fullName + " has not created an article yet.");
        }
        return articleModels;
    }

    public Iterable<ArticleModel> findAllByCreatorFullNameOrderByRating(String fullName) {
        Iterable<UserModel> userModels = userRepository.findByFullName(fullName);
        ArrayList<ArticleModel> articleModels = new ArrayList<>();
        for (UserModel u : userModels) {
            findAllByCreatorIdOrderByRating(u.getUserId()).forEach(articleModels::add);
        }
        if (articleModels.isEmpty()) {
            throw new IllegalArgumentException("Users with full " + fullName + " has not created an article yet.");
        }
        return articleModels;
    }

    public void deleteById(String token, Long id) throws IllegalAccessException {
        Long userId = getUserIdFromToken(token);
        checkAccess(id, userId);
        articleRepository.deleteById(id);
    }

    private void checkAccess(Long id, Long creatorId) throws IllegalAccessException {
        ArticleModel tmp = articleRepository.findById(id).orElse(null);
        UserModel tmpUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Incorrect user with user id " + id));
        if (tmp == null || (!tmp.getCreatorId().equals(creatorId) && tmpUser.getRole() != (Role.MODERATOR))) {
            throw new IllegalAccessException();
        }

    }

    private void checkSymbols(ArrayList<String> text) {
        String regex = "[};]";

        for (String s : text
        ) {
            if (Pattern.matches(regex, s)) {
                throw new IllegalArgumentException("incorrect symbols in " + s);
            }
        }

    }

    private Long getUserIdFromToken(String token) {
        Jws<Claims> claims = parseToken(token);
        return (Long) claims.getBody().get("userId");
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(resolveToken(token));

    }

    private String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        throw new IllegalArgumentException("Incorrect token");
    }

    /*public Iterable<ArticleModel> findAllByTag(String tags){

    }*/
}
