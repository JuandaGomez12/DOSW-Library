package edu.eci.dosw.DOSW_Library.persistence.nonrelational.repository;

import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.core.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
public class UserRepositoryImpl implements UserRepository {

    @Override
    public User save(User user) {
        throw new UnsupportedOperationException("Users are managed via relational DB only.");
    }

    @Override
    public Optional<User> findById(String id) {
        throw new UnsupportedOperationException("Users are managed via relational DB only.");
    }

    @Override
    public List<User> findAll() {
        throw new UnsupportedOperationException("Users are managed via relational DB only.");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Users are managed via relational DB only.");
    }

    @Override
    public boolean existsById(String id) {
        throw new UnsupportedOperationException("Users are managed via relational DB only.");
    }

    @Override
    public Optional<User> findByUsername(String username) {
        throw new UnsupportedOperationException("Users are managed via relational DB only.");
    }
}
