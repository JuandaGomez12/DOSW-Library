package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.UserNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.core.validator.UserValidator;
import edu.eci.dosw.DOSW_Library.persistence.mapper.UserPersistenceMapper;
import edu.eci.dosw.DOSW_Library.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserPersistenceMapper userMapper;
    private final UserValidator userValidator;

    public UserService(UserRepository userRepository, UserPersistenceMapper userMapper, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }

    public User registerUser(User user) {
        userValidator.validate(user);
        return userMapper.toModel(userRepository.save(userMapper.toEntity(user)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .map(userMapper::toModel)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException(id);
        userRepository.deleteById(id);
    }
}