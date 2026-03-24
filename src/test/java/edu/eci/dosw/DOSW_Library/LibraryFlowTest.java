package edu.eci.dosw.DOSW_Library;

import edu.eci.dosw.DOSW_Library.core.exception.BookNotAvailableException;
import edu.eci.dosw.DOSW_Library.core.exception.LoanLimitExceededException;
import edu.eci.dosw.DOSW_Library.core.exception.UserNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.core.service.BookService;
import edu.eci.dosw.DOSW_Library.core.service.LoanService;
import edu.eci.dosw.DOSW_Library.core.service.UserService;
import edu.eci.dosw.DOSW_Library.core.validator.BookValidator;
import edu.eci.dosw.DOSW_Library.core.validator.LoanValidator;
import edu.eci.dosw.DOSW_Library.core.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryFlowTest {

    private BookService bookService;
    private UserService userService;
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        bookService = new BookService(new BookValidator());
        userService = new UserService(new UserValidator());
        loanService = new LoanService(new LoanValidator(), bookService, userService);
    }

    private Book buildBook(String id) {
        Book book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setId(id);
        book.setAvailable(true);
        return book;
    }

    private User buildUser(String id) {
        User user = new User();
        user.setName("Juan");
        user.setId(id);
        return user;
    }

    private Loan buildLoan(String bookId, String userId) {
        Loan loan = new Loan();
        loan.setBook(bookId);
        loan.setUser(userId);
        loan.setLoanDate("2026-03-21");
        return loan;
    }

    @Test
    void flujoCompleto_prestarYDevolverLibro() {
        userService.registerUser(buildUser("U001"));
        bookService.addBook(buildBook("B001"), 1);

        Loan loan = loanService.createLoan(buildLoan("B001", "U001"));
        assertEquals("ACTIVE", loan.getStatus());
        assertFalse(bookService.getBookById("B001").isAvailable());

        Loan returned = loanService.returnBook("B001");
        assertEquals("RETURNED", returned.getStatus());
        assertTrue(bookService.getBookById("B001").isAvailable());
    }

    @Test
    void flujoLibros_agregarActualizarEliminar() {
        bookService.addBook(buildBook("B001"), 2);
        assertEquals(1, bookService.getAllBooks().size());

        Book updated = new Book();
        updated.setTitle("Nuevo Titulo");
        bookService.updateBook("B001", updated);
        assertEquals("Nuevo Titulo", bookService.getBookById("B001").getTitle());

        bookService.deleteBook("B001");
        assertThrows(IllegalArgumentException.class, () -> bookService.getBookById("B001"));
    }

    @Test
    void flujoUsuarios_registrarEliminar() {
        userService.registerUser(buildUser("U001"));
        assertEquals(1, userService.getAllUsers().size());

        userService.deleteUser("U001");
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("U001"));
    }

    @Test
    void flujoPrestamo_libroNoDisponible_lanzaExcepcion() {
        userService.registerUser(buildUser("U001"));
        userService.registerUser(buildUser("U002"));
        bookService.addBook(buildBook("B001"), 1);

        loanService.createLoan(buildLoan("B001", "U001"));
        assertThrows(BookNotAvailableException.class, () -> loanService.createLoan(buildLoan("B001", "U002")));
    }

    @Test
    void flujoPrestamo_usuarioNoExiste_lanzaExcepcion() {
        bookService.addBook(buildBook("B001"), 1);
        assertThrows(UserNotFoundException.class, () -> loanService.createLoan(buildLoan("B001", "NOEXISTE")));
    }

    @Test
    void flujoPrestamo_limiteExcedido_lanzaExcepcion() {
        userService.registerUser(buildUser("U001"));
        bookService.addBook(buildBook("B001"), 1);
        bookService.addBook(buildBook("B002"), 1);

        loanService.createLoan(buildLoan("B001", "U001"));
        assertThrows(LoanLimitExceededException.class, () -> loanService.createLoan(buildLoan("B002", "U001")));
    }
}