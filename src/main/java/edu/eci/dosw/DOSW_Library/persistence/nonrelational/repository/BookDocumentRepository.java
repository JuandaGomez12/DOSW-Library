package edu.eci.dosw.DOSW_Library.persistence.nonrelational.repository;

import edu.eci.dosw.DOSW_Library.persistence.nonrelational.document.BookDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookDocumentRepository extends MongoRepository<BookDocument, String> {

    @Query("{ 'title': '?0' }")
    Optional<BookDocument> findByTitle(String title);

    @Query("{ 'author': '?0' }")
    List<BookDocument> findByAuthor(String author);

    @Query("{ 'availability.available_copies': { $gt: 0 } }")
    List<BookDocument> findAvailableBooks();

    long count();
}
