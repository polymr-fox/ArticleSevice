package com.mrfox.senyast4745.articleservice.repostory;


import com.mrfox.senyast4745.articleservice.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
    Iterable<UserModel> findByFullName(String fullName);
}
