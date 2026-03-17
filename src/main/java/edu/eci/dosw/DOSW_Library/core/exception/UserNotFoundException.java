package edu.eci.dosw.DOSW_Library.core.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("Usuario no encontrado con ID: " + userId);
    }
}