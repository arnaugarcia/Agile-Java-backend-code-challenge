package com.agiletv.users.infraestructure.api;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.agiletv.users.domain.model.User;
import com.agiletv.users.infrastructure.api.dto.UserRequestDTO;
import com.agiletv.users.infrastructure.persistence.GenderEntity;
import com.agiletv.users.infrastructure.persistence.JpaUserRepository;
import com.agiletv.users.infrastructure.persistence.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserController")
class UserControllerTest {

    private static final String USERNAME = "jdoe";
    private static final String NAME = "John Doe";
    private static final String EMAIL = "johndoe@doe.com";
    private static final String CITY = "San Francisco";
    private static final String PICTURE_URL = "https://example.com/pic.jpg";
    private static final String COUNTRY = "USA";
    private static final String STATE = "California";
    private static final String UNKNOWN_USERNAME = "unknown";

    private static final String API_USERS = "/api/users";
    private static final String API_USERS_USERNAME = "/api/users/{username}";
    private static final String API_USERS_TREE = "/api/users/tree";
    private static final String API_USERS_GENERATE = "/api/users/generate/{number}";


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setUsername(USERNAME);
        userEntity.setName(NAME);
        userEntity.setEmail(EMAIL);
        userEntity.setGender(GenderEntity.MALE);
        userEntity.setCity(CITY);
        userEntity.setPicture(PICTURE_URL);
        userEntity.setCountry(COUNTRY);
        userEntity.setState(STATE);
    }

    @Nested
    @DisplayName("GET /api/users/{username}")
    class GetUserTests {

        @Test
        void shouldReturnUser() throws Exception {
            when(jpaUserRepository.findById(USERNAME)).thenReturn(Optional.of(userEntity));

            mockMvc.perform(get(API_USERS_USERNAME, USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.email").value(EMAIL))
                .andExpect(jsonPath("$.country").value(COUNTRY))
                .andExpect(jsonPath("$.state").value(STATE))
                .andExpect(jsonPath("$.city").value(CITY));
        }

        @Test
        void shouldReturn404IfUserNotFound() throws Exception {
            when(jpaUserRepository.findById(UNKNOWN_USERNAME)).thenReturn(Optional.empty());

            mockMvc.perform(get(API_USERS_USERNAME, UNKNOWN_USERNAME))
                .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("GET /api/users")
    class GetAllUsersTests {

        @Test
        void shouldReturnUserList() throws Exception {
            when(jpaUserRepository.findAll()).thenReturn(List.of(userEntity));

            mockMvc.perform(get(API_USERS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(USERNAME));
        }
    }

    @Nested
    @DisplayName("POST /api/users")
    class CreateUserTests {

        @Test
        void shouldCreateUser() throws Exception {
            String userJson = objectMapper.writeValueAsString(userEntity);
            when(jpaUserRepository.save(userEntity)).thenReturn(userEntity);

            mockMvc.perform(post(API_USERS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(userJson))
                .andExpect(status().isCreated());
        }

        @Test
        void shouldReturn400IfUserAlreadyExists() throws Exception {
            when(jpaUserRepository.findById(USERNAME)).thenReturn(Optional.ofNullable(userEntity));

            mockMvc.perform(post(API_USERS)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userEntity)))
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("DELETE /api/users/{username}")
    class DeleteUserTests {

        @Test
        void shouldDeleteUser() throws Exception {
            when(jpaUserRepository.existsById(USERNAME)).thenReturn(true);
            mockMvc.perform(delete(API_USERS_USERNAME, USERNAME))
                .andExpect(status().isNoContent());

            verify(jpaUserRepository).deleteById(USERNAME);
        }
    }

    @Nested
    @DisplayName("GET /api/users/tree")
    class GetUserTreeTests {

        @Test
        void shouldReturnUserTree() throws Exception {
            mockMvc.perform(get(API_USERS_TREE))
                .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("GET /api/users/generate/{number}")
    class GenerateUsersTests {

        @Test
        void shouldGenerateUsers() throws Exception {
            mockMvc.perform(get(API_USERS_GENERATE, 5))
                .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("PUT /api/users/{username}")
    class UpdateUserTests {

        @Test
        void shouldUpdateUser() throws Exception {
            when(jpaUserRepository.findById(USERNAME)).thenReturn(Optional.of(userEntity));

            UserRequestDTO updatedUser = getUpdatedUser();

            mockMvc.perform(put(API_USERS_USERNAME, USERNAME)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk());
        }

        @Test
        void shouldReturn404IfUserNotFound() throws Exception {
            when(jpaUserRepository.findById(UNKNOWN_USERNAME)).thenReturn(Optional.empty());

            mockMvc.perform(put(API_USERS_USERNAME, USERNAME)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(getUpdatedUser())))
                .andExpect(status().isNotFound());
        }

        @Test
        void shouldReturn400IfNoBody() throws Exception {
            mockMvc.perform(put(API_USERS_USERNAME, USERNAME)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        }

        @Test
        void shouldReturn400IfNoUsername() throws Exception {
            var updatedUser = """
                {
                  "username": null,
                  "name": "John Doe2",
                  "email": "johndoe@doe.com",
                  "gender": "female",
                  "picture": "https://example.com/pic2.jpg",
                  "country": "Europe",
                  "state": "Barcelona",
                  "city": "Spain"
                }
                """;

            mockMvc.perform(put(API_USERS_USERNAME, USERNAME)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedUser))
                .andExpect(status().isBadRequest());
        }

        private static UserRequestDTO getUpdatedUser() {
            return new UserRequestDTO(
                "jdoe2",
                "John Doe2",
                "johndoe2@doe.com",
                "female",
                "https://example.com/pic2.jpg",
                "Europe",
                "Barcelona",
                "Spain");
        }
    }
}
