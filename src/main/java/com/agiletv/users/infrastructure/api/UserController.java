package com.agiletv.users.infrastructure.api;

import com.agiletv.users.application.usecase.CreateUserUseCase;
import com.agiletv.users.application.usecase.DeleteUserUseCase;
import com.agiletv.users.application.usecase.GenerateUsersUseCase;
import com.agiletv.users.application.usecase.GetAllUsersUseCase;
import com.agiletv.users.application.usecase.GetUserByUsernameUseCase;
import com.agiletv.users.application.usecase.UpdateUserUseCase;
import com.agiletv.users.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserByUsernameUseCase getUserByUsernameUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GenerateUsersUseCase generateUsersUseCase;

    @GetMapping("/")
    public List<User> getAllUsers() {
        return getAllUsersUseCase.execute();
    }

    @GetMapping("/{username}")
    public Optional<User> getUser(@PathVariable String username) {
        return getUserByUsernameUseCase.execute(username);
    }

    @PostMapping("/")
    public void createUser(@RequestBody User user) {
        createUserUseCase.execute(user);
    }

    @PutMapping("/{username}")
    public void updateUser(@PathVariable String username, @RequestBody User user) {
        user.setUsername(username);
        updateUserUseCase.execute(user);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) {
        deleteUserUseCase.execute(username);
    }

    @GetMapping("/generate/{number}")
    public void generateUsers(@PathVariable int number) {
        generateUsersUseCase.execute(number);
    }
}
