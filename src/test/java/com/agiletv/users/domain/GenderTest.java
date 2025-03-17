package com.agiletv.users.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.agiletv.users.domain.model.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Gender")
class GenderTest {

    @Nested
    @DisplayName("fromString()")
    class FromStringTests {

        @Test
        void shouldReturnGenderForValidString() {
            assertAll(
                () -> assertEquals(Gender.MALE, Gender.fromString("male")),
                () -> assertEquals(Gender.FEMALE, Gender.fromString("female")),
                () -> assertEquals(Gender.OTHER, Gender.fromString("other"))
            );
        }

        @Test
        void shouldBeCaseInsensitive() {
            assertAll(
                () -> assertEquals(Gender.MALE, Gender.fromString("MALE")),
                () -> assertEquals(Gender.FEMALE, Gender.fromString("FEMALE")),
                () -> assertEquals(Gender.OTHER, Gender.fromString("OTHER")),
                () -> assertEquals(Gender.MALE, Gender.fromString("MaLe")),
                () -> assertEquals(Gender.FEMALE, Gender.fromString("FeMaLe")),
                () -> assertEquals(Gender.OTHER, Gender.fromString("OtHeR"))
            );
        }

        @Test
        void shouldThrowExceptionForNullValue() {
            assertThrows(IllegalArgumentException.class, () -> Gender.fromString(null));
        }

        @Test
        void shouldThrowExceptionForInvalidValue() {
            assertThrows(IllegalArgumentException.class, () -> Gender.fromString("invalid"));
        }
    }
}
