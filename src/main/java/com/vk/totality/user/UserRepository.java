package com.vk.totality.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserLogin(String userLogin);

    User findByUserLoginIgnoreCaseAndActiveTrue(String userLogin);

    User findUserById(Long id);

    User findUserByUserLogin(String login);

    Boolean existsUserByUserLogin(String userName);

    List<User> findAllBy();
}
