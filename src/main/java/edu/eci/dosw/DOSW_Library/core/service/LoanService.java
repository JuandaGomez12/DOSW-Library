package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.LoanLimitExceededException;
import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.core.repository.LoanRepository;
import edu.eci.dosw.DOSW_Library.core.validator.LoanValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private static final int MAX_LOANS_PER_USER = 1;

    private final LoanRepository loanRepository;
    private final LoanValidator loanValidator;
    private final BookService bookService;
    private final UserService userService;

    public LoanService(LoanRepository loanRepository, LoanValidator loanValidator,
                       BookService bookService, UserService userService) {
        this.loanRepository = loanRepository;
        this.loanValidator = loanValidator;
        this.bookService = bookService;
        this.userService = userService;
    }

    public Loan createLoan(Loan loan) {
        loanValidator.validate(loan);
        userService.getUserById(loan.getUser());

        long activeLoans = loanRepository.countByUserIdAndStatus(loan.getUser(), "ACTIVE");
        if (activeLoans >= MAX_LOANS_PER_USER)
            throw new LoanLimitExceededException(loan.getUser());

        bookService.checkAvailability(loan.getBook());
        bookService.decreaseCopies(loan.getBook());

        loan.setStatus("ACTIVE");
        return loanRepository.save(loan);
    }

    public Loan returnBook(String bookId) {
        Loan loan = loanRepository.findByBookIdAndStatus(bookId, "ACTIVE")
                .orElseThrow(() -> new IllegalArgumentException("No hay préstamo activo para el libro: " + bookId));

        loan.setStatus("RETURNED");
        bookService.increaseCopies(bookId);
        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public List<Loan> getLoansByUser(String userId) {
        return loanRepository.findByUserId(userId);
    }
}
