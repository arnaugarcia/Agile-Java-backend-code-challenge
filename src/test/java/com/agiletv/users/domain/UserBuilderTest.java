package com.agiletv.users.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.agiletv.users.domain.model.Gender;
import com.agiletv.users.domain.model.User;
import com.agiletv.users.domain.model.UserBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("UserBuilder")
class UserBuilderTest {

    @Nested
    @DisplayName("build()")
    class BuildTests {

        @Test
        void shouldBuildUserSuccessfully() {
            User user = new UserBuilder()
                .username("jdoe")
                .name("John Doe")
                .email("jdoe@example.com")
                .gender(Gender.MALE)
                .picture("https://example.com/pic.jpg")
                .country("USA")
                .state("California")
                .city("Los Angeles")
                .build();

            assertAll(
                () -> assertEquals("jdoe", user.username()),
                () -> assertEquals("John Doe", user.name()),
                () -> assertEquals("jdoe@example.com", user.email()),
                () -> assertEquals(Gender.MALE, user.gender()),
                () -> assertEquals("https://example.com/pic.jpg", user.picture()),
                () -> assertEquals("USA", user.country()),
                () -> assertEquals("California", user.state()),
                () -> assertEquals("Los Angeles", user.city())
            );
        }

        @Test
        void shouldThrowExceptionIfMandatoryFieldsAreMissing() {
            assertThrows(IllegalArgumentException.class, () -> new UserBuilder()
                .name("John Doe")
                .email("jdoe@example.com")
                .gender(Gender.MALE)
                .picture("https://example.com/pic.jpg")
                .country("USA")
                .state("California")
                .city("Los Angeles")
                .build()); // Missing username

            assertThrows(IllegalArgumentException.class, () -> new UserBuilder()
                .username("jdoe")
                .email("jdoe@example.com")
                .gender(Gender.MALE)
                .picture("https://example.com/pic.jpg")
                .country("USA")
                .state("California")
                .city("Los Angeles")
                .build()); // Missing name
        }

        @Test
        void shouldConvertGenderFromString() {
            User user = new UserBuilder()
                .username("jdoe")
                .name("John Doe")
                .email("jdoe@example.com")
                .gender("male")
                .picture("http://example.com/pic.jpg")
                .country("USA")
                .state("California")
                .city("Los Angeles")
                .build();

            assertEquals(Gender.MALE, user.gender());
        }

        @Test
        void shouldThrowExceptionForInvalidGenderString() {
            assertThrows(IllegalArgumentException.class, () -> new UserBuilder()
                .username("jdoe")
                .name("John Doe")
                .email("jdoe@example.com")
                .gender("invalidGender")
                .picture("https://example.com/pic.jpg")
                .country("USA")
                .state("California")
                .city("Los Angeles")
                .build());
        }
    }
}

