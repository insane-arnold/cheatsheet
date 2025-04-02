package com.learn.authentication.repo;

import com.learn.authentication.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByVerificationCode(String verificationCode);

    Optional<User> findByUsername(String username);

}
