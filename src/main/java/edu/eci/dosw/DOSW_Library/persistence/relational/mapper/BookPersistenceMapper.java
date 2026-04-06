package edu.eci.dosw.DOSW_Library.persistence.relational.mapper;

import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class BookPersistenceMapper {

    public BookEntity toEntity(Book book) {
        BookEntity entity = new BookEntity();
        entity.setId(book.getId());
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setTotalCopies(book.getTotalCopies());
        entity.setAvailableCopies(book.getAvailableCopies());
        return entity;
    }

    public Book toDomain(BookEntity entity) {
        Book book = new Book();
        book.setId(entity.getId());
        book.setTitle(entity.getTitle());
        book.setAuthor(entity.getAuthor());
        book.setTotalCopies(entity.getTotalCopies());
        book.setAvailableCopies(entity.getAvailableCopies());
        return book;
    }
}
