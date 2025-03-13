package com.agiletv.users.application.usecase;

import com.agiletv.users.application.dto.UserTreeDTO;
import com.agiletv.users.application.service.UserTreeService;
import com.agiletv.users.domain.repository.UserRepository;
import java.util.List;

public class UserTreeUseCase {
    private final UserRepository userRepository;
    private final UserTreeService userTreeService;

    public UserTreeUseCase(UserRepository userRepository, UserTreeService userTreeService) {
        this.userRepository = userRepository;
        this.userTreeService = userTreeService;
    }

    public List<UserTreeDTO> execute() {
        var users = userRepository.findAll();
        return userTreeService.buildFrom(users);
    }
}
