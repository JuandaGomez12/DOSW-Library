package edu.eci.dosw.DOSW_Library;

import edu.eci.dosw.DOSW_Library.core.exception.UserNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.core.service.UserService;
import edu.eci.dosw.DOSW_Library.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("relational")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    private User buildUser() {
        User user = new User();
        user.setName("Juan");
        user.setId("U001");
        return user;
    }

    @Test
    void registerUser_success() {
        User result = userService.registerUser(buildUser());
        assertNotNull(result.getId());
        assertEquals("Juan", result.getName());
    }

    @Test
    void registerUser_missingName_throwsException() {
        User user = new User();
        user.setId("U001");
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
    }

    @Test
    void getAllUsers_returnsAllRegistered() {
        userService.registerUser(buildUser());
        assertEquals(1, userService.getAllUsers().size());
    }

    @Test
    void getUserById_success() {
        User saved = userService.registerUser(buildUser());
        User found = userService.getUserById(saved.getId());
        assertEquals(saved.getId(), found.getId());
    }

    @Test
    void getUserById_notFound_throwsException() {
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("NOEXISTE"));
    }

    @Test
    void deleteUser_success() {
        User saved = userService.registerUser(buildUser());
        userService.deleteUser(saved.getId());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(saved.getId()));
    }
}