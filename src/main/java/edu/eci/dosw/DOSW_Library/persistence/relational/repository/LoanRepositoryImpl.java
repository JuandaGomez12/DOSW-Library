package edu.eci.dosw.DOSW_Library.persistence.relational.repository;

import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.core.repository.LoanRepository;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.LoanStatusEnum;
import edu.eci.dosw.DOSW_Library.persistence.relational.mapper.LoanPersistenceMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("relational")
public class LoanRepositoryImpl implements LoanRepository {

    private final JpaLoanRepository repository;
    private final LoanPersistenceMapper mapper;
    private final JpaBookRepository jpaBookRepository;
    private final JpaUserRepository jpaUserRepository;

    public LoanRepositoryImpl(JpaLoanRepository repository, LoanPersistenceMapper mapper,
                               JpaBookRepository jpaBookRepository, JpaUserRepository jpaUserRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.jpaBookRepository = jpaBookRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Loan save(Loan loan) {
        var entity = mapper.toEntity(loan);
        entity.setBook(jpaBookRepository.findById(loan.getBook()).orElseThrow());
        entity.setUser(jpaUserRepository.findById(loan.getUser()).orElseThrow());
        return mapper.toDomain(repository.save(entity));
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
        return repository.findByBookIdAndStatus(bookId, LoanStatusEnum.valueOf(status))
            .map(mapper::toDomain);
    }

    @Override
    public long countByUserIdAndStatus(String userId, String status) {
        return repository.countByUserIdAndStatus(userId, LoanStatusEnum.valueOf(status));
    }
}
