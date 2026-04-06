package edu.eci.dosw.DOSW_Library.core.repository;

import edu.eci.dosw.DOSW_Library.core.model.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    Loan save(Loan loan);
    Optional<Loan> findById(String id);
    List<Loan> findAll();
    void delete(String id);
    List<Loan> findByUserId(String userId);
    Optional<Loan> findByBookIdAndStatus(String bookId, String status);
    long countByUserIdAndStatus(String userId, String status);
}
