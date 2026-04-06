package edu.eci.dosw.DOSW_Library.persistence.nonrelational.repository;

import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.core.repository.LoanRepository;
import edu.eci.dosw.DOSW_Library.persistence.nonrelational.document.BookDocument;
import edu.eci.dosw.DOSW_Library.persistence.nonrelational.mapper.LoanDocumentMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
public class LoanRepositoryImpl implements LoanRepository {

    private final LoanDocumentRepository repository;
    private final LoanDocumentMapper mapper;
    private final BookDocumentRepository bookDocumentRepository;

    public LoanRepositoryImpl(LoanDocumentRepository repository, LoanDocumentMapper mapper,
                               BookDocumentRepository bookDocumentRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.bookDocumentRepository = bookDocumentRepository;
    }

    @Override
    public Loan save(Loan loan) {
        var doc = mapper.toDocument(loan);
        if (loan.getBook() != null) {
            BookDocument bookDoc = bookDocumentRepository.findById(loan.getBook()).orElse(null);
            doc.setBook(bookDoc);
        }
        return mapper.toDomain(repository.save(doc));
    }

    @Override
    public Optional<Loan> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Loan> findAll() {
        return repository.findAll()
            .stream()
            .map(mapper::toDomain)
            .toList();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Loan> findByUserId(String userId) {
        return repository.findByUserId(userId)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }

    @Override
    public Optional<Loan> findByBookIdAndStatus(String bookId, String status) {
        return repository.findActiveLoanByUserId(bookId).map(mapper::toDomain);
    }

    @Override
    public long countByUserIdAndStatus(String userId, String status) {
        return repository.findByUserId(userId)
            .stream()
            .filter(doc -> doc.getStatusEvents() != null
                && !doc.getStatusEvents().isEmpty()
                && status.equals(doc.getStatusEvents().getLast().getStatus()))
            .count();
    }
}
