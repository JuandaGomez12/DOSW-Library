package edu.eci.dosw.DOSW_Library.core.validator;

import edu.eci.dosw.DOSW_Library.core.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validate(User user) {
        if (user.getName() == null || user.getName().isBlank())
            throw new IllegalArgumentException("El nombre del usuario no puede estar vacío.");
    }
}