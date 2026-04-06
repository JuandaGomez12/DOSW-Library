package edu.eci.dosw.DOSW_Library.persistence.nonrelational.repository;

import edu.eci.dosw.DOSW_Library.persistence.nonrelational.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserDocumentRepository extends MongoRepository<UserDocument, String> {

    @Query("{ 'username': '?0' }")
    Optional<UserDocument> findByUsername(String username);

    long count();
}
