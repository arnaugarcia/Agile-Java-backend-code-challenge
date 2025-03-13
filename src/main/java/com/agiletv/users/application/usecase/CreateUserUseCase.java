package com.agiletv.users.application.usecase;


import com.agiletv.users.domain.model.User;
import com.agiletv.users.domain.repository.UserRepository;
import com.agiletv.users.infrastructure.api.dto.UserRequestDTO;

public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UserRequestDTO userRequest) {
        userRepository.findByUsername(userRequest.username()).ifPresent(user -> {
            throw new IllegalArgumentException("User already exists");
        });
        var user = User.builder()
            .username(userRequest.username())
            .name(userRequest.name())
            .email(userRequest.email())
            .gender(userRequest.gender())
            .picture(userRequest.picture())
            .country(userRequest.country())
            .state(userRequest.state())
            .city(userRequest.city())
            .build();
        userRepository.save(user);
    }
}
