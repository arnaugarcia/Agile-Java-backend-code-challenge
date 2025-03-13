package com.agiletv.users.application.service;

import com.agiletv.users.application.dto.UserDTO;
import com.agiletv.users.application.dto.UserTreeDTO;
import com.agiletv.users.domain.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserTreeService {

    public List<UserTreeDTO> buildFrom(List<User> users) {
        Map<String, Map<String, Map<String, List<User>>>> groupedUsers = new HashMap<>();

        for (User user : users) {
            groupedUsers
                .computeIfAbsent(user.getCountry(), k -> new HashMap<>())
                .computeIfAbsent(user.getState(), k -> new HashMap<>())
                .computeIfAbsent(user.getCity(), k -> new ArrayList<>())
                .add(user);
        }

        List<UserTreeDTO> tree = new ArrayList<>();
        for (Map.Entry<String, Map<String, Map<String, List<User>>>> countryEntry : groupedUsers.entrySet()) {
            List<UserTreeDTO.StateDTO> stateDTOs = new ArrayList<>();
            for (Map.Entry<String, Map<String, List<User>>> stateEntry : countryEntry.getValue().entrySet()) {
                List<UserTreeDTO.CityDTO> cityDTOs = new ArrayList<>();
                for (Map.Entry<String, List<User>> cityEntry : stateEntry.getValue().entrySet()) {
                    List<UserDTO> userDTOs = new ArrayList<>();
                    for (User user : cityEntry.getValue()) {
                        userDTOs.add(new UserDTO(user.getUsername(), user.getName(), user.getEmail(), user.getGender(),
                            user.getPicture()));
                    }
                    cityDTOs.add(new UserTreeDTO.CityDTO(cityEntry.getKey(), userDTOs));
                }
                stateDTOs.add(new UserTreeDTO.StateDTO(stateEntry.getKey(), cityDTOs));
            }
            tree.add(new UserTreeDTO(countryEntry.getKey(), stateDTOs));
        }

        return tree;
    }
}
