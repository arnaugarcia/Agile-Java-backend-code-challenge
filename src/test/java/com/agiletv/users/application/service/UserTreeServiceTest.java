package com.agiletv.users.application.service;

import static org.junit.jupiter.api.Assertions.*;

import com.agiletv.users.domain.model.Gender;
import com.agiletv.users.domain.model.User;
import com.agiletv.users.infrastructure.api.dto.UserDTO;
import com.agiletv.users.infrastructure.api.dto.UserTreeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("UserTreeService")
class UserTreeServiceTest {

    private static final String USERNAME = "jdoe";
    private static final String NAME = "John Doe";
    private static final String EMAIL = "jdoe@example.com";
    private static final Gender GENDER = Gender.MALE;
    private static final String PICTURE = "https://example.com/pic.jpg";
    private static final String COUNTRY = "USA";
    private static final String STATE = "California";
    private static final String CITY = "Los Angeles";

    private final UserTreeService userTreeService = new UserTreeService();

    @Nested
    @DisplayName("buildFrom()")
    class BuildFromTests {

        @Test
        void shouldBuildUserTreeSuccessfully() {
            User user = new User(USERNAME, NAME, EMAIL, GENDER, PICTURE, COUNTRY, STATE, CITY);
            List<UserTreeDTO> userTree = userTreeService.buildFrom(List.of(user));

            var userTreeDTO = userTree.getFirst();
            var stateDTO = userTreeDTO.states().getFirst();
            var cityDTO = stateDTO.cities().getFirst();
            var userDTO = cityDTO.users().getFirst();

            assertAll(
                () -> assertEquals(1, userTree.size()),
                () -> assertEquals(COUNTRY, userTreeDTO.country()),
                () -> assertEquals(1, userTreeDTO.states().size()),
                () -> assertEquals(STATE, stateDTO.state()),
                () -> assertEquals(1, stateDTO.cities().size()),
                () -> assertEquals(CITY, cityDTO.city()),
                () -> assertEquals(1, cityDTO.users().size()),
                () -> assertEquals(UserDTO.from(user), userDTO)
            );
        }

        @Test
        void shouldHandleEmptyUserList() {
            var userTree = userTreeService.buildFrom(List.of());

            assertTrue(userTree.isEmpty());
        }
    }
}
