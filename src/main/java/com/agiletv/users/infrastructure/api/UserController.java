package com.agiletv.users.infrastructure.api;

import com.agiletv.users.infrastructure.api.dto.UserDTO;
import com.agiletv.users.infrastructure.api.dto.UserRequestDTO;
import com.agiletv.users.infrastructure.api.dto.UserTreeDTO;
import com.agiletv.users.application.usecase.CreateUserUseCase;
import com.agiletv.users.application.usecase.DeleteUserUseCase;
import com.agiletv.users.application.usecase.GenerateUsersUseCase;
import com.agiletv.users.application.usecase.GetAllUsersUseCase;
import com.agiletv.users.application.usecase.GetUserByUsernameUseCase;
import com.agiletv.users.application.usecase.UpdateUserUseCase;
import com.agiletv.users.application.usecase.UserTreeUseCase;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
class UserController {
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserByUsernameUseCase getUserByUsernameUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GenerateUsersUseCase generateUsersUseCase;
    private final UserTreeUseCase userTreeUseCase;

    @GetMapping("/")
    public List<UserDTO> getAllUsers() {
        log.debug("REST request to get all users");
        return getAllUsersUseCase.execute();
    }

    @GetMapping("/{username}")
    public Optional<UserDTO> getUser(@PathVariable String username) {
        log.debug("REST request to find a user by username: {}", username);
        return getUserByUsernameUseCase.execute(username);
    }

    @PostMapping("/")
    public void createUser(@RequestBody UserRequestDTO userRequest) {
        log.debug("REST request to create a new user: {}", userRequest);
        createUserUseCase.execute(userRequest);
    }

    @PutMapping("/{username}")
    public void updateUser(@PathVariable String username, @RequestBody UserRequestDTO userRequest) {
        log.debug("REST request to update user: {}", userRequest);
        updateUserUseCase.execute(username, userRequest);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) {
        log.debug("REST request to delete user: {}", username);
        deleteUserUseCase.execute(username);
    }

    @GetMapping("/generate/{number}")
    public void generateUsers(@PathVariable int number) {
        log.debug("REST request to generate users: {}", number);
        generateUsersUseCase.execute(number);
    }

    @GetMapping("/tree")
    public List<UserTreeDTO> getUserTree() {
        log.debug("REST request to get user tree");
        return userTreeUseCase.execute();
    }
}
