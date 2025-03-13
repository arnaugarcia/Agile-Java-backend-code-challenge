package com.agiletv.users.infrastructure.persistence;

import com.agiletv.users.domain.model.User;
import com.agiletv.users.domain.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Transforms UserEntity (JPA) in User (domain) and viceversa.
 */
@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll()
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findById(username).map(this::toDomain);
    }

    @Override
    public void save(User user) {
        jpaUserRepository.save(toEntity(user));
    }

    @Override
    public void deleteByUsername(String username) {
        jpaUserRepository.deleteById(username);
    }

    private User toDomain(UserEntity entity) {
        return new User(entity.getUsername(), entity.getName(), entity.getEmail(),
            entity.getGender(), entity.getPicture(), entity.getCountry(),
            entity.getState(), entity.getCity());
    }

    private UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setGender(user.getGender());
        entity.setPicture(user.getPicture());
        entity.setCountry(user.getCountry());
        entity.setState(user.getState());
        entity.setCity(user.getCity());
        return entity;
    }
}
