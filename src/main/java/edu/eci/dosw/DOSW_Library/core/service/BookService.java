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

    private final Map<String, Book> books = new HashMap<>();
    private final Map<String, Integer> copies = new HashMap<>();
    private final BookValidator bookValidator;

    public BookService(BookValidator bookValidator) {
        this.bookValidator = bookValidator;
    }

    public Book addBook(Book book, int numCopies) {
        bookValidator.validate(book);
        books.put(book.getId(), book);
        copies.put(book.getId(), numCopies);
        return book;
    }

    public List<Book> getAllBooks() {
        return List.copyOf(books.values());
    }

    public Book getBookById(String id) {
        Book book = books.get(id);
        if (book == null) {
            throw new IllegalArgumentException("Libro no encontrado con ID: " + id);
        }
        return book;
    }

    public Book updateBook(String id, Book updated) {
        Book book = getBookById(id);
        if (updated.getTitle() != null && !updated.getTitle().isBlank())
            book.setTitle(updated.getTitle());
        if (updated.getAuthor() != null && !updated.getAuthor().isBlank())
            book.setAuthor(updated.getAuthor());
        return book;
    }

    public Book updateAvailability(String id, boolean available) {
        Book book = getBookById(id);
        book.setAvailable(available);
        return book;
    }

    public void deleteBook(String id) {
        getBookById(id);
        books.remove(id);
        copies.remove(id);
    }

    public int getCopies(String id) {
        return copies.getOrDefault(id, 0);
    }

    public void checkAvailability(String bookId) {
        Book book = getBookById(bookId);
        if (!book.isAvailable()) {
            throw new BookNotAvailableException(bookId);
        }
    }
}