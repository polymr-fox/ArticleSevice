package com.mrfox.senyast4745.articleservice.dao;

import com.mrfox.senyast4745.articleservice.repostory.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UsersDAO {

    private final UserRepository userRepository;

    public UsersDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*public Long findUserByLogin(String login){

    }*/
}
