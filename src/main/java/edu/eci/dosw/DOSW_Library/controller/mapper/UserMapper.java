package edu.eci.dosw.DOSW_Library.controller.mapper;

import edu.eci.dosw.DOSW_Library.controller.dto.UserDTO;
import edu.eci.dosw.DOSW_Library.core.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toModel(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setId(dto.getId());
        return user;
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setId(user.getId());
        return dto;
    }
}