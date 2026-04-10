package edu.eci.dosw.DOSW_Library.persistence.nonrelational.repository;

import edu.eci.dosw.DOSW_Library.persistence.nonrelational.document.LoanDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LoanDocumentRepository extends MongoRepository<LoanDocument, String> {

    @Query("{ 'user.id': '?0' }")
    List<LoanDocument> findByUserId(String userId);

    @Query("{ 'user.id': '?0', 'status_events': { $elemMatch: { 'status': 'ACTIVE' } } }")
    Optional<LoanDocument> findActiveLoanByUserId(String userId);

    @Query("{ 'book.$id': ?0 }")
    List<LoanDocument> findByBookId(String bookId);

    long count();
}
