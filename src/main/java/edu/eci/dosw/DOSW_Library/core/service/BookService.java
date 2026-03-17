package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.BookNotAvailableException;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.validator.BookValidator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService {

    private final Map<Book, Integer> books = new HashMap<>();
    private final BookValidator bookValidator;

    public BookService(BookValidator bookValidator) {
        this.bookValidator = bookValidator;
    }

    public Book addBook(Book book, int copies) {
        bookValidator.validate(book);
        books.put(book, copies);
        return book;
    }

    public List<Book> getAllBooks() {
        return List.copyOf(books.keySet());
    }

    public Book getBookById(String id) {
        return books.keySet().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + id));
    }

    public Book updateAvailability(String id, boolean available) {
        Book book = getBookById(id);
        book.setAvailable(available);
        return book;
    }

    public int getCopies(String id) {
        Book book = getBookById(id);
        return books.get(book);
    }

    public void checkAvailability(String bookId) {
        Book book = getBookById(bookId);
        if (!book.isAvailable()) {
            throw new BookNotAvailableException(bookId);
        }
    }
}