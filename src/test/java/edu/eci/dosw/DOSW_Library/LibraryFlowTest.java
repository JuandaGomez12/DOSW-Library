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
import edu.eci.dosw.DOSW_Library.core.repository.BookRepository;
import edu.eci.dosw.DOSW_Library.core.repository.LoanRepository;
import edu.eci.dosw.DOSW_Library.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LibraryFlowTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @BeforeEach
    void setUp() {
        loanRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    private Book buildBook() {
        Book book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setAvailable(true);
        return book;
    }

    private User buildUser() {
        User user = new User();
        user.setName("Juan");
        return user;
    }

    private Loan buildLoan(String bookId, String userId) {
        Loan loan = new Loan();
        loan.setBook(bookId);
        loan.setUser(userId);
        loan.setLoanDate(LocalDate.now());
        return loan;
    }

    @Test
    void flujoCompleto_prestarYDevolverLibro() {
        User user = userService.registerUser(buildUser());
        Book book = bookService.addBook(buildBook(), 1);

        Loan loan = loanService.createLoan(buildLoan(book.getId(), user.getId()));
        assertEquals("ACTIVE", loan.getStatus());

        Loan returned = loanService.returnBook(book.getId());
        assertEquals("RETURNED", returned.getStatus());
    }

    @Test
    void flujoLibros_agregarActualizarEliminar() {
        Book book = bookService.addBook(buildBook(), 2);
        assertEquals(1, bookService.getAllBooks().size());

        Book updated = new Book();
        updated.setTitle("Nuevo Titulo");
        bookService.updateBook(book.getId(), updated);
        assertEquals("Nuevo Titulo", bookService.getBookById(book.getId()).getTitle());

        bookService.deleteBook(book.getId());
        assertThrows(IllegalArgumentException.class, () -> bookService.getBookById(book.getId()));
    }

    @Test
    void flujoUsuarios_registrarEliminar() {
        User user = userService.registerUser(buildUser());
        assertEquals(1, userService.getAllUsers().size());

        userService.deleteUser(user.getId());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(user.getId()));
    }

    @Test
    void flujoPrestamo_libroNoDisponible_lanzaExcepcion() {
        User user1 = userService.registerUser(buildUser());
        User user2 = userService.registerUser(buildUser());
        Book book = bookService.addBook(buildBook(), 1);

        loanService.createLoan(buildLoan(book.getId(), user1.getId()));
        assertThrows(BookNotAvailableException.class, () -> loanService.createLoan(buildLoan(book.getId(), user2.getId())));
    }

    @Test
    void flujoPrestamo_limiteExcedido_lanzaExcepcion() {
        User user = userService.registerUser(buildUser());
        Book book1 = bookService.addBook(buildBook(), 1);
        Book book2 = bookService.addBook(buildBook(), 1);

        loanService.createLoan(buildLoan(book1.getId(), user.getId()));
        assertThrows(LoanLimitExceededException.class, () -> loanService.createLoan(buildLoan(book2.getId(), user.getId())));
    }
}