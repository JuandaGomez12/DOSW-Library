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

class LoanServiceTest {

    private BookService bookService;
    private UserService userService;
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        bookService = new BookService(new BookValidator());
        userService = new UserService(new UserValidator());
        loanService = new LoanService(new LoanValidator(), bookService, userService);

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setId("B001");
        book.setAvailable(true);
        bookService.addBook(book, 1);

        User user = new User();
        user.setName("Juan");
        user.setId("U001");
        userService.registerUser(user);
    }

    private Loan buildLoan(String bookId, String userId) {
        Loan loan = new Loan();
        loan.setBook(bookId);
        loan.setUser(userId);
        loan.setLoanDate("2026-03-16");
        return loan;
    }

    @Test
    void createLoan_success() {
        Loan result = loanService.createLoan(buildLoan("B001", "U001"));
        assertEquals("ACTIVE", result.getStatus());
        assertFalse(bookService.getBookById("B001").isAvailable());
    }

    @Test
    void createLoan_userNotFound_throwsException() {
        assertThrows(UserNotFoundException.class, () -> loanService.createLoan(buildLoan("B001", "NOEXISTE")));
    }

    @Test
    void createLoan_bookNotAvailable_throwsException() {
        loanService.createLoan(buildLoan("B001", "U001"));

        User user2 = new User();
        user2.setName("Maria");
        user2.setId("U002");
        userService.registerUser(user2);

        assertThrows(BookNotAvailableException.class, () -> loanService.createLoan(buildLoan("B001", "U002")));
    }

    @Test
    void createLoan_userExceedsLimit_throwsException() {
        Book book2 = new Book();
        book2.setTitle("Refactoring");
        book2.setAuthor("Fowler");
        book2.setId("B002");
        book2.setAvailable(true);
        bookService.addBook(book2, 1);

        loanService.createLoan(buildLoan("B001", "U001"));

        assertThrows(LoanLimitExceededException.class, () -> loanService.createLoan(buildLoan("B002", "U001")));
    }

    @Test
    void createLoan_missingBookId_throwsException() {
        Loan loan = buildLoan("", "U001");
        assertThrows(IllegalArgumentException.class, () -> loanService.createLoan(loan));
    }

    @Test
    void createLoan_missingUserId_throwsException() {
        Loan loan = buildLoan("B001", "");
        assertThrows(IllegalArgumentException.class, () -> loanService.createLoan(loan));
    }
}