package com.agiletv.users.domain.repository;

import com.agiletv.users.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findByUsername(String username);

    void save(User user);

    void deleteByUsername(String username);
}
