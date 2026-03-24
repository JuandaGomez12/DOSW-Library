package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.LoanLimitExceededException;
import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.core.validator.LoanValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    private static final int MAX_LOANS_PER_USER = 1;

    private final List<Loan> loans = new ArrayList<>();
    private final LoanValidator loanValidator;
    private final BookService bookService;
    private final UserService userService;

    public LoanService(LoanValidator loanValidator, BookService bookService, UserService userService) {
        this.loanValidator = loanValidator;
        this.bookService = bookService;
        this.userService = userService;
    }

    public Loan createLoan(Loan loan) {
        loanValidator.validate(loan);

        userService.getUserById(loan.getUser());

        long activeLoans = loans.stream()
                .filter(l -> l.getUser().equals(loan.getUser()) && "ACTIVE".equals(l.getStatus()))
                .count();
        if (activeLoans >= MAX_LOANS_PER_USER) {
            throw new LoanLimitExceededException(loan.getUser());
        }

        bookService.checkAvailability(loan.getBook());
        bookService.updateAvailability(loan.getBook(), false);

        loan.setStatus("ACTIVE");
        loans.add(loan);
        return loan;
    }

    public Loan returnBook(String bookId) {
        Loan loan = loans.stream()
                .filter(l -> l.getBook().equals(bookId) && "ACTIVE".equals(l.getStatus()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No hay préstamo activo para el libro: " + bookId));

        loan.setStatus("RETURNED");
        bookService.updateAvailability(bookId, true);
        return loan;
    }

    public List<Loan> getAllLoans() {
        return new ArrayList<>(loans);
    }
}