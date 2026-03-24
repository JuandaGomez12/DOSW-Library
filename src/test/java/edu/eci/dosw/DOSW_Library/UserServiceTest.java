package edu.eci.dosw.DOSW_Library;

import edu.eci.dosw.DOSW_Library.core.exception.UserNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.core.service.UserService;
import edu.eci.dosw.DOSW_Library.core.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(new UserValidator());
    }

    private User buildUser(String id) {
        User user = new User();
        user.setName("Juan");
        user.setId(id);
        return user;
    }

    @Test
    void registerUser_success() {
        assertEquals("U001", userService.registerUser(buildUser("U001")).getId());
    }

    @Test
    void registerUser_missingName_throwsException() {
        User user = new User();
        user.setId("U001");
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
    }

    @Test
    void registerUser_missingId_throwsException() {
        User user = new User();
        user.setName("Juan");
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(user));
    }

    @Test
    void getAllUsers_returnsAllRegistered() {
        userService.registerUser(buildUser("U001"));
        userService.registerUser(buildUser("U002"));
        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    void getUserById_success() {
        userService.registerUser(buildUser("U001"));
        assertEquals("U001", userService.getUserById("U001").getId());
    }

    @Test
    void getUserById_notFound_throwsException() {
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("NOEXISTE"));
    }

    @Test
    void deleteUser_success() {
        userService.registerUser(buildUser("U001"));
        userService.deleteUser("U001");
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("U001"));
    }

    @Test
    void deleteUser_notFound_throwsException() {
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser("NOEXISTE"));
    }
}