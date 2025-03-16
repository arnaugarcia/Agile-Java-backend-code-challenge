package com.agiletv.users.infrastructure.api;

import com.agiletv.users.application.usecase.CreateUserUseCase;
import com.agiletv.users.application.usecase.DeleteUserUseCase;
import com.agiletv.users.application.usecase.GenerateUsersUseCase;
import com.agiletv.users.application.usecase.GetAllUsersUseCase;
import com.agiletv.users.application.usecase.GetUserByUsernameUseCase;
import com.agiletv.users.application.usecase.UpdateUserUseCase;
import com.agiletv.users.application.usecase.UserTreeUseCase;
import com.agiletv.users.infrastructure.api.dto.UserDTO;
import com.agiletv.users.infrastructure.api.dto.UserRequestDTO;
import com.agiletv.users.infrastructure.api.dto.UserTreeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User API", description = "API for managing users")
class UserController {
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserByUsernameUseCase getUserByUsernameUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GenerateUsersUseCase generateUsersUseCase;
    private final UserTreeUseCase userTreeUseCase;

    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/")
    public List<UserDTO> getAllUsers() {
        log.debug("REST request to get all users");
        return getAllUsersUseCase.execute();
    }

    @Operation(summary = "Get user by username", description = "Retrieve a user by their username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{username}")
    public Optional<UserDTO> getUser(
        @Parameter(description = "Username of the user to be retrieved") @PathVariable String username) {
        log.debug("REST request to find a user by username: {}", username);
        return getUserByUsernameUseCase.execute(username);
    }

    @Operation(summary = "Create a new user", description = "Create a new user with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/")
    public void createUser(
        @Parameter(description = "Details of the user to be created") @RequestBody UserRequestDTO userRequest) {
        log.debug("REST request to create a new user: {}", userRequest);
        createUserUseCase.execute(userRequest);
    }

    @Operation(summary = "Update a user", description = "Update an existing user with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{username}")
    public void updateUser(
        @Parameter(description = "Username of the user to be updated") @PathVariable String username,
        @Parameter(description = "Updated details of the user") @RequestBody UserRequestDTO userRequest) {
        log.debug("REST request to update user: {}", userRequest);
        updateUserUseCase.execute(username, userRequest);
    }

    @Operation(summary = "Delete a user", description = "Delete a user by their username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{username}")
    public void deleteUser(
        @Parameter(description = "Username of the user to be deleted") @PathVariable String username) {
        log.debug("REST request to delete user: {}", username);
        deleteUserUseCase.execute(username);
    }

    @Operation(summary = "Generate users", description = "Generate a specified number of users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users generated successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/generate/{number}")
    public void generateUsers(@Parameter(description = "Number of users to be generated") @PathVariable int number) {
        log.debug("REST request to generate users: {}", number);
        generateUsersUseCase.execute(number);
    }

    @Operation(summary = "Get user tree",
        description = "Retrieve a hierarchical tree of users grouped by country, state, and city")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user tree"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/tree")
    public List<UserTreeDTO> getUserTree() {
        log.debug("REST request to get user tree");
        return userTreeUseCase.execute();
    }
}
