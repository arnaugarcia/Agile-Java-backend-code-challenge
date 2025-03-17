package com.agiletv.users.infraestructure.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agiletv.users.domain.model.Gender;
import com.agiletv.users.domain.model.User;
import com.agiletv.users.infrastructure.persistence.GenderEntity;
import com.agiletv.users.infrastructure.persistence.JpaUserRepository;
import com.agiletv.users.infrastructure.persistence.UserEntity;
import com.agiletv.users.infrastructure.persistence.UserRepositoryAdapter;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserRepositoryAdapter")
class UserRepositoryAdapterTest {

    @Mock
    private JpaUserRepository jpaUserRepository;

    @InjectMocks
    private UserRepositoryAdapter userRepositoryAdapter;

    private static final String USERNAME = "jdoe";
    private static final String NAME = "John Doe";
    private static final String EMAIL = "jdoe@example.com";
    private static final Gender GENDER = Gender.MALE;
    private static final String PICTURE = "https://example.com/pic.jpg";
    private static final String COUNTRY = "USA";
    private static final String STATE = "California";
    private static final String CITY = "Los Angeles";

    @Nested
    @DisplayName("findAll()")
    class FindAllTests {

        @Test
        void shouldReturnAllUsers() {
            UserEntity userEntity = createUserEntity();
            when(jpaUserRepository.findAll()).thenReturn(List.of(userEntity));

            List<User> users = userRepositoryAdapter.findAll();

            var user = users.getFirst();
            assertAll(
                () -> assertEquals(1, users.size()),
                () -> assertEquals(USERNAME, user.username()),
                () -> assertEquals(NAME, user.name()),
                () -> assertEquals(EMAIL, user.email()),
                () -> assertEquals(GENDER, user.gender()),
                () -> assertEquals(PICTURE, user.picture()),
                () -> assertEquals(COUNTRY, user.country()),
                () -> assertEquals(STATE, user.state()),
                () -> assertEquals(CITY, user.city())
            );
        }
    }

    @Nested
    @DisplayName("findByUsername()")
    class FindByUsernameTests {

        @Test
        void shouldReturnUserWhenUsernameExists() {
            UserEntity userEntity = createUserEntity();
            when(jpaUserRepository.findById(USERNAME)).thenReturn(Optional.of(userEntity));

            Optional<User> user = userRepositoryAdapter.findByUsername(USERNAME);

            assertTrue(user.isPresent());
            User result = user.get();
            assertAll(
                () -> assertEquals(USERNAME, result.username()),
                () -> assertEquals(NAME, result.name()),
                () -> assertEquals(EMAIL, result.email()),
                () -> assertEquals(GENDER, result.gender()),
                () -> assertEquals(PICTURE, result.picture()),
                () -> assertEquals(COUNTRY, result.country()),
                () -> assertEquals(STATE, result.state()),
                () -> assertEquals(CITY, result.city())
            );
        }

        @Test
        void shouldReturnEmptyWhenUsernameDoesNotExist() {
            when(jpaUserRepository.findById(USERNAME)).thenReturn(Optional.empty());

            Optional<User> user = userRepositoryAdapter.findByUsername(USERNAME);

            assertTrue(user.isEmpty());
        }
    }

    @Nested
    @DisplayName("save()")
    class SaveTests {

        @Test
        void shouldSaveUser() {
            User user = createUser();
            UserEntity userEntity = createUserEntity();
            when(jpaUserRepository.save(any(UserEntity.class))).thenReturn(userEntity);

            userRepositoryAdapter.save(user);

            verify(jpaUserRepository, times(1)).save(any(UserEntity.class));
        }

    }

    @Nested
    @DisplayName("deleteByUsername()")
    class DeleteByUsernameTests {

        @Test
        void shouldDeleteUserByUsername() {
            userRepositoryAdapter.deleteByUsername(USERNAME);

            verify(jpaUserRepository, times(1)).deleteById(USERNAME);
        }
    }

    private User createUser() {
        return new User(USERNAME, NAME, EMAIL, GENDER, PICTURE, COUNTRY, STATE, CITY);
    }

    private UserEntity createUserEntity() {
        UserEntity entity = new UserEntity();
        entity.setUsername(USERNAME);
        entity.setName(NAME);
        entity.setEmail(EMAIL);
        entity.setGender(GenderEntity.MALE);
        entity.setPicture(PICTURE);
        entity.setCountry(COUNTRY);
        entity.setState(STATE);
        entity.setCity(CITY);
        return entity;
    }
}
