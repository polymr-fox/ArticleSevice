package com.mrfox.senyast4745.articleservice.controller;

import com.mrfox.senyast4745.articleservice.dao.ArticlesDAO;
import com.mrfox.senyast4745.articleservice.forms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

import java.util.Date;

@Controller
public class MainController {

    private final
    ArticlesDAO articlesDAO;

    private Gson gson = new Gson();

    @Autowired
    public MainController(ArticlesDAO articlesDAO) {
        this.articlesDAO = articlesDAO;
    }

    @PreAuthorize("@securityService.hasPermission('ADMIN,TEACHER,STUDENT')")
    @RequestMapping(value = "/create" , method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity create(@RequestBody CreateForm jsonForm){
        try {
            return ResponseEntity.ok(articlesDAO.create(jsonForm.getCreatorId(), jsonForm.getArticleName(), jsonForm.getText()
                    , jsonForm.getTags(), 0, new Date()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(jsonForm), "/create" )));

        }
    }

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity readAll(){
        try {
            return ResponseEntity.ok(articlesDAO.findAll());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "DataBase is empty.", "/read" )));

        }
    }

    @RequestMapping(value = "/read/usr", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity readByFullName(@RequestParam FullNameForm form){
        try {
            return ResponseEntity.ok(articlesDAO.findAllByCreatorFullName(form.getFullName()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/read/usr" )));

        }
    }

    @RequestMapping(value = "/read/usr/rtg", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity readByFullNameOrderByRating(@RequestParam FullNameForm form){
        try {
            return ResponseEntity.ok(articlesDAO.findAllByCreatorFullNameOrderByRating(form.getFullName()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/read/usr/rtg" )));
        }
    }

    @RequestMapping(value = "/read/rtg", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity readByRating(@RequestParam RatingForm form){
        try {
            return ResponseEntity.ok(articlesDAO.findAllByRating(form.getRating()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/read/rtg" )));

        }
    }

    @PreAuthorize("@securityService.hasPermission('ADMIN,TEACHER,STUDENT')")
    @RequestMapping(value = "/upd/rtg", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity updateRating(@RequestParam UpdateRatingForm form){
        try {
            return ResponseEntity.ok(articlesDAO.updateRating(form.getId(), form.getRating()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/upd/rtg" )));

        }
    }

    @PreAuthorize("@securityService.hasPermission('ADMIN,TEACHER,STUDENT')")
    @RequestMapping(value = "/upd", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity updateAll(@RequestParam UpdateAllForm form){
        try {
            return ResponseEntity.ok(articlesDAO.updateAll(form.getId(), form.getArticleName(), form.getText(), form.getTags(), 0));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/upd" )));

        }
    }

    @PreAuthorize("@securityService.hasPermission('ADMIN,TEACHER,STUDENT')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity deleteById(@RequestParam MinimalForm form){
        try {
            articlesDAO.deleteById(form.getId());
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/delete" )));

        }
    }


}
