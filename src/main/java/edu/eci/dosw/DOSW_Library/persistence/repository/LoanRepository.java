package edu.eci.dosw.DOSW_Library.persistence.repository;

import edu.eci.dosw.DOSW_Library.persistence.entity.LoanEntity;
import edu.eci.dosw.DOSW_Library.persistence.entity.LoanStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<LoanEntity, String> {

    List<LoanEntity> findByUserId(String userId);

    List<LoanEntity> findByUserIdAndStatus(String userId, LoanStatusEnum status);

    Optional<LoanEntity> findByBookIdAndStatus(String bookId, LoanStatusEnum status);

    long countByUserIdAndStatus(String userId, LoanStatusEnum status);
}