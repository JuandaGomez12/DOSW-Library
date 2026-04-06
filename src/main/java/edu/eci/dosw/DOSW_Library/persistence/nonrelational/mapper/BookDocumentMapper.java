package edu.eci.dosw.DOSW_Library.persistence.nonrelational.mapper;

import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.persistence.nonrelational.document.Availability;
import edu.eci.dosw.DOSW_Library.persistence.nonrelational.document.BookDocument;
import org.springframework.stereotype.Component;

@Component
public class BookDocumentMapper {

    public BookDocument toDocument(Book book) {
        BookDocument doc = new BookDocument();
        doc.setId(book.getId());
        doc.setTitle(book.getTitle());
        doc.setAuthor(book.getAuthor());
        Availability availability = new Availability();
        availability.setTotalCopies(book.getTotalCopies());
        availability.setAvailableCopies(book.getAvailableCopies());
        doc.setAvailability(availability);
        return doc;
    }

    public Book toDomain(BookDocument doc) {
        Book book = new Book();
        book.setId(doc.getId());
        book.setTitle(doc.getTitle());
        book.setAuthor(doc.getAuthor());
        if (doc.getAvailability() != null) {
            book.setTotalCopies(doc.getAvailability().getTotalCopies());
            book.setAvailableCopies(doc.getAvailability().getAvailableCopies());
        }
        return book;
    }
}
