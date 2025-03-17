package com.agiletv.users.infrastructure.persistence;

import com.agiletv.users.domain.model.User;
import com.agiletv.users.domain.model.UserBuilder;
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
        return new UserBuilder()
            .username(entity.getUsername())
            .name(entity.getName())
            .email(entity.getEmail())
            .gender(entity.getGender().toDomain())
            .picture(entity.getPicture())
            .country(entity.getCountry())
            .state(entity.getState())
            .city(entity.getCity())
            .build();
    }

    private UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setUsername(user.username());
        entity.setName(user.name());
        entity.setEmail(user.email());
        entity.setGender(GenderEntity.fromDomain(user.gender()));
        entity.setPicture(user.picture());
        entity.setCountry(user.country());
        entity.setState(user.state());
        entity.setCity(user.city());
        return entity;
    }
}
