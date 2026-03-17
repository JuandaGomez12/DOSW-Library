package edu.eci.dosw.DOSW_Library.core.validator;

import edu.eci.dosw.DOSW_Library.core.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookValidator {

    public void validate(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank())
            throw new IllegalArgumentException("El título del libro no puede estar vacío.");
        if (book.getAuthor() == null || book.getAuthor().isBlank())
            throw new IllegalArgumentException("El autor del libro no puede estar vacío.");
        if (book.getId() == null || book.getId().isBlank())
            throw new IllegalArgumentException("El ID del libro no puede estar vacío.");
    }
}