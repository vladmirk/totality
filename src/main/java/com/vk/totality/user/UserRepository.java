package com.vk.totality.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserLogin(String userLogin);

    User findUserById(Long id);

    Boolean existsUserByUserLogin(String userName);
}
