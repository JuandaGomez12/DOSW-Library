package edu.eci.dosw.DOSW_Library.core.exception;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String bookId) {
        super("El libro con ID " + bookId + " no está disponible para préstamo.");
    }
}