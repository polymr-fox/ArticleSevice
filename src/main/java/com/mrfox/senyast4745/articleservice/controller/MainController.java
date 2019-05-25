package com.mrfox.senyast4745.articleservice.controller;

import com.mrfox.senyast4745.articleservice.dao.ArticlesDAO;
import com.mrfox.senyast4745.articleservice.forms.CreateJsonForm;
import com.mrfox.senyast4745.articleservice.forms.ExceptionModel;
import com.mrfox.senyast4745.articleservice.forms.FullNameForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/create" , method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity create(@RequestBody CreateJsonForm jsonForm){
        try {
            return ResponseEntity.ok(articlesDAO.create(jsonForm.getCreatorId(), jsonForm.getArticleName(), jsonForm.getText()
                    , jsonForm.getTags(), 0, new Date()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(jsonForm), "/create" )));

        }

    }

    @RequestMapping(value = "/read_by_usr", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity readByFullName(@RequestParam FullNameForm form){
        try {
            return ResponseEntity.ok(articlesDAO.findAllByCreatorFullName(form.getFullName()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/read_by_usr" )));

        }

    }


}
