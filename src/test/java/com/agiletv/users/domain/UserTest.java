package com.agiletv.users.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.agiletv.users.domain.model.Gender;
import com.agiletv.users.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("User")
class UserTest {

    private static final String USERNAME = "jdoe";
    private static final String NAME = "John Doe";
    private static final String EMAIL = "jdoe@example.com";
    private static final Gender GENDER = Gender.MALE;
    private static final String PICTURE = "http://example.com/pic.jpg";
    private static final String COUNTRY = "USA";
    private static final String STATE = "California";
    private static final String CITY = "Los Angeles";

    @Nested
    @DisplayName("create()")
    class CreateTests {

        @Test
        void shouldCreateUserSuccessfully() {
            User user = new User(USERNAME, NAME, EMAIL, GENDER, PICTURE, COUNTRY, STATE, CITY);

            assertAll(
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

        @Test
        void shouldThrowExceptionIfUsernameIsNullOrBlank() {
            assertThrows(IllegalArgumentException.class, () -> new User(null, NAME, EMAIL, GENDER, PICTURE, COUNTRY, STATE, CITY));
            assertThrows(IllegalArgumentException.class, () -> new User(" ", NAME, EMAIL, GENDER, PICTURE, COUNTRY, STATE, CITY));
        }

        @Test
        void shouldThrowExceptionIfEmailIsInvalid() {
            assertThrows(IllegalArgumentException.class, () -> new User(USERNAME, NAME, "invalid-email", GENDER, PICTURE, COUNTRY, STATE, CITY));
        }

        @Test
        void shouldThrowExceptionIfGenderIsNull() {
            assertThrows(IllegalArgumentException.class, () -> new User(USERNAME, NAME, EMAIL, null, PICTURE, COUNTRY, STATE, CITY));
        }

        @Test
        void shouldThrowExceptionIfPictureIsInvalid() {
            assertThrows(IllegalArgumentException.class, () -> new User(USERNAME, NAME, EMAIL, GENDER, "invalid-url", COUNTRY, STATE, CITY));
        }
    }

    @Nested
    @DisplayName("update()")
    class UpdateTests {
        @Test
        void shouldUpdateUserDetails() {
            User user = new User(USERNAME, NAME, EMAIL, GENDER, PICTURE, COUNTRY, STATE, CITY);
            user.update("http://newpic.com/pic.jpg", "Canada", "Ontario", "Toronto");

            assertAll(
                () -> assertEquals("http://newpic.com/pic.jpg", user.picture()),
                () -> assertEquals("Canada", user.country()),
                () -> assertEquals("Ontario", user.state()),
                () -> assertEquals("Toronto", user.city())
            );
        }
    }

    @Nested
    @DisplayName("builder()")
    class BuilderTest {
        @Test
        void shouldCreateUserUsingBuilder() {
            User user = User.builder()
                .username(USERNAME)
                .name(NAME)
                .email(EMAIL)
                .gender(GENDER)
                .picture(PICTURE)
                .country(COUNTRY)
                .state(STATE)
                .city(CITY)
                .build();

            assertAll(
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
}
