package edu.eci.dosw.DOSW_Library.persistence.repository;

import edu.eci.dosw.DOSW_Library.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, String> {

    List<BookEntity> findByAvailableCopiesGreaterThan(int copies);

    List<BookEntity> findByTitleContainingIgnoreCase(String title);

    List<BookEntity> findByAuthorContainingIgnoreCase(String author);
}