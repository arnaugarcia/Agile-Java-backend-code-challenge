package com.agiletv.users.application.service;

import com.agiletv.users.infrastructure.api.dto.UserDTO;
import com.agiletv.users.infrastructure.api.dto.UserTreeDTO;
import com.agiletv.users.domain.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class UserTreeService {

    public List<UserTreeDTO> buildFrom(List<User> users) {
        Map<String, Map<String, Map<String, List<User>>>> groupedUsers = new HashMap<>();

        for (User user : users) {
            groupedUsers
                .computeIfAbsent(user.country(), k -> new HashMap<>())
                .computeIfAbsent(user.state(), k -> new HashMap<>())
                .computeIfAbsent(user.city(), k -> new ArrayList<>())
                .add(user);
        }

        List<UserTreeDTO> tree = new ArrayList<>();
        for (var countryEntry : groupedUsers.entrySet()) {
            List<UserTreeDTO.StateDTO> stateDTOs = new ArrayList<>();
            for (var stateEntry : countryEntry.getValue().entrySet()) {
                List<UserTreeDTO.CityDTO> cityDTOs = new ArrayList<>();
                for (var cityEntry : stateEntry.getValue().entrySet()) {
                    List<UserDTO> userDTOs = new ArrayList<>();
                    for (var user : cityEntry.getValue()) {
                        userDTOs.add(UserDTO.from(user));
                    }
                    cityDTOs.add(new UserTreeDTO.CityDTO(cityEntry.getKey(), userDTOs));
                }
                stateDTOs.add(new UserTreeDTO.StateDTO(stateEntry.getKey(), cityDTOs));
            }
            tree.add(new UserTreeDTO(countryEntry.getKey(), stateDTOs));
        }
        log.debug("Finished building user tree, countries: {}", tree.size());

        return tree;
    }
}
