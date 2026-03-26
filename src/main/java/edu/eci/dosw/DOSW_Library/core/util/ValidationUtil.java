package edu.eci.dosw.DOSW_Library.core.util;

import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.core.model.User;

public class ValidationUtil {

    private ValidationUtil() {}

    public static void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank())
            throw new IllegalArgumentException("El título del libro no puede estar vacío.");
        if (book.getAuthor() == null || book.getAuthor().isBlank())
            throw new IllegalArgumentException("El autor del libro no puede estar vacío.");
        if (book.getId() == null || book.getId().isBlank())
            throw new IllegalArgumentException("El ID del libro no puede estar vacío.");
    }

    public static void validateUser(User user) {
        if (user.getName() == null || user.getName().isBlank())
            throw new IllegalArgumentException("El nombre del usuario no puede estar vacío.");
        if (user.getId() == null || user.getId().isBlank())
            throw new IllegalArgumentException("El ID del usuario no puede estar vacío.");
    }

    public static void validateLoan(Loan loan) {
        if (loan.getBook() == null || loan.getBook().isBlank())
            throw new IllegalArgumentException("El ID del libro en el préstamo no puede estar vacío.");
        if (loan.getUser() == null || loan.getUser().isBlank())
            throw new IllegalArgumentException("El ID del usuario en el préstamo no puede estar vacío.");
        if (loan.getLoanDate() == null)
            throw new IllegalArgumentException("La fecha del préstamo no puede estar vacía.");
    }
}