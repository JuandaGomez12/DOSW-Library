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
class LoanServiceTest {

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

    private Book savedBook;
    private User savedUser;

    @BeforeEach
    void setUp() {
        loanRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        book.setAvailable(true);
        savedBook = bookService.addBook(book, 1);

        User user = new User();
        user.setName("Juan");
        user.setId("U001");
        savedUser = userService.registerUser(user);
    }

    private Loan buildLoan() {
        Loan loan = new Loan();
        loan.setBook(savedBook.getId());
        loan.setUser(savedUser.getId());
        loan.setLoanDate(LocalDate.now());
        return loan;
    }

    @Test
    void createLoan_success() {
        Loan result = loanService.createLoan(buildLoan());
        assertEquals("ACTIVE", result.getStatus());
    }

    @Test
    void createLoan_userNotFound_throwsException() {
        Loan loan = buildLoan();
        loan.setUser("NOEXISTE");
        assertThrows(UserNotFoundException.class, () -> loanService.createLoan(loan));
    }

    @Test
    void createLoan_bookNotAvailable_throwsException() {
        loanService.createLoan(buildLoan());

        User user2 = new User();
        user2.setName("Maria");
        User savedUser2 = userService.registerUser(user2);

        Loan loan2 = buildLoan();
        loan2.setUser(savedUser2.getId());
        assertThrows(BookNotAvailableException.class, () -> loanService.createLoan(loan2));
    }

    @Test
    void createLoan_userExceedsLimit_throwsException() {
        Book book2 = new Book();
        book2.setTitle("Refactoring");
        book2.setAuthor("Fowler");
        book2.setAvailable(true);
        Book savedBook2 = bookService.addBook(book2, 1);

        loanService.createLoan(buildLoan());

        Loan loan2 = buildLoan();
        loan2.setBook(savedBook2.getId());
        assertThrows(LoanLimitExceededException.class, () -> loanService.createLoan(loan2));
    }

    @Test
    void returnBook_success() {
        loanService.createLoan(buildLoan());
        Loan returned = loanService.returnBook(savedBook.getId());
        assertEquals("RETURNED", returned.getStatus());
    }

    @Test
    void returnBook_noActiveLoan_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> loanService.returnBook(savedBook.getId()));
    }
}