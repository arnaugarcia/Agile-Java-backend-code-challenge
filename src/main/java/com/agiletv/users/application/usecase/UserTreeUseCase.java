package com.agiletv.users.application.usecase;

import com.agiletv.users.infrastructure.api.dto.UserTreeDTO;
import com.agiletv.users.application.service.UserTreeService;
import com.agiletv.users.domain.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserTreeUseCase {
    private final UserRepository userRepository;
    private final UserTreeService userTreeService;

    public UserTreeUseCase(UserRepository userRepository, UserTreeService userTreeService) {
        this.userRepository = userRepository;
        this.userTreeService = userTreeService;
    }

    public List<UserTreeDTO> execute() {
        var users = userRepository.findAll();

        log.debug("UserTreeUseCase users size: {}", users.size());

        return userTreeService.buildFrom(users);
    }
}
