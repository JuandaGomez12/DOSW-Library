package edu.eci.dosw.DOSW_Library.persistence.relational.mapper;

import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.RoleEnum;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserPersistenceMapper {

    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setUsername(user.getUsername() != null
                ? user.getUsername()
                : user.getName().toLowerCase().replace(" ", "_") + "_" + UUID.randomUUID().toString().substring(0, 4));
        entity.setPassword("{noop}default");
        entity.setRole(user.getRole() != null
                ? RoleEnum.valueOf(user.getRole())
                : RoleEnum.MEMBER);
        return entity;
    }

    public User toDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setUsername(entity.getUsername());
        user.setRole(entity.getRole().name());
        return user;
    }
}
