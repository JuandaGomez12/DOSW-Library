package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.LoanLimitExceededException;
import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.core.validator.LoanValidator;
import edu.eci.dosw.DOSW_Library.persistence.entity.LoanEntity;
import edu.eci.dosw.DOSW_Library.persistence.entity.LoanStatusEnum;
import edu.eci.dosw.DOSW_Library.persistence.mapper.LoanPersistenceMapper;
import edu.eci.dosw.DOSW_Library.persistence.repository.BookRepository;
import edu.eci.dosw.DOSW_Library.persistence.repository.LoanRepository;
import edu.eci.dosw.DOSW_Library.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private static final int MAX_LOANS_PER_USER = 1;

    private final LoanRepository loanRepository;
    private final LoanPersistenceMapper loanMapper;
    private final LoanValidator loanValidator;
    private final BookService bookService;
    private final UserService userService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, LoanPersistenceMapper loanMapper,
                       LoanValidator loanValidator, BookService bookService,
                       UserService userService, BookRepository bookRepository,
                       UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.loanValidator = loanValidator;
        this.bookService = bookService;
        this.userService = userService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Loan createLoan(Loan loan) {
        loanValidator.validate(loan);

        userService.getUserById(loan.getUser());

        long activeLoans = loanRepository.countByUserIdAndStatus(loan.getUser(), LoanStatusEnum.ACTIVE);
        if (activeLoans >= MAX_LOANS_PER_USER)
            throw new LoanLimitExceededException(loan.getUser());

        bookService.checkAvailability(loan.getBook());
        bookService.decreaseCopies(loan.getBook());

        LoanEntity entity = loanMapper.toEntity(loan);
        entity.setUser(userRepository.findById(loan.getUser()).orElseThrow());
        entity.setBook(bookRepository.findById(loan.getBook()).orElseThrow());
        entity.setStatus(LoanStatusEnum.ACTIVE);

        return loanMapper.toModel(loanRepository.save(entity));
    }

    public Loan returnBook(String bookId) {
        LoanEntity entity = loanRepository.findByBookIdAndStatus(bookId, LoanStatusEnum.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException("No hay préstamo activo para el libro: " + bookId));

        entity.setStatus(LoanStatusEnum.RETURNED);
        bookService.increaseCopies(bookId);

        return loanMapper.toModel(loanRepository.save(entity));
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll().stream()
                .map(loanMapper::toModel)
                .collect(Collectors.toList());
    }

    public List<Loan> getLoansByUser(String userId) {
        return loanRepository.findByUserId(userId).stream()
                .map(loanMapper::toModel)
                .collect(Collectors.toList());
    }
}