package com.agiletv.users.application.usecase;

import com.agiletv.users.infrastructure.api.dto.UserDTO;
import com.agiletv.users.domain.repository.UserRepository;
import java.util.List;

public class GetAllUsersUseCase {

    private final UserRepository userRepository;

    public GetAllUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> execute() {
        return userRepository.findAll()
            .stream()
            .map(UserDTO::from)
            .toList();
    }
}
