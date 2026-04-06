package edu.eci.dosw.DOSW_Library.persistence.relational.repository;

import edu.eci.dosw.DOSW_Library.persistence.relational.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaBookRepository extends JpaRepository<BookEntity, String> {
    List<BookEntity> findByAvailableCopiesGreaterThan(int copies);
    List<BookEntity> findByTitleContainingIgnoreCase(String title);
    List<BookEntity> findByAuthorContainingIgnoreCase(String author);
}
