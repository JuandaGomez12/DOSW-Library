package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.BookNotAvailableException;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.repository.BookRepository;
import edu.eci.dosw.DOSW_Library.core.validator.BookValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    public BookService(BookRepository bookRepository, BookValidator bookValidator) {
        this.bookRepository = bookRepository;
        this.bookValidator = bookValidator;
    }

    public Book addBook(Book book, int totalCopies) {
        bookValidator.validate(book);
        book.setTotalCopies(totalCopies);
        book.setAvailableCopies(totalCopies);
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + id));
    }

    public Book updateBook(String id, Book updated) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + id));
        if (updated.getTitle() != null && !updated.getTitle().isBlank())
            book.setTitle(updated.getTitle());
        if (updated.getAuthor() != null && !updated.getAuthor().isBlank())
            book.setAuthor(updated.getAuthor());
        return bookRepository.save(book);
    }

    public void deleteBook(String id) {
        if (!bookRepository.existsById(id))
            throw new IllegalArgumentException("Libro no encontrado con ID: " + id);
        bookRepository.delete(id);
    }

    public void checkAvailability(String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + bookId));
        if (book.getAvailableCopies() <= 0)
            throw new BookNotAvailableException(bookId);
    }

    public void decreaseCopies(String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + bookId));
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);
    }

    public void increaseCopies(String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + bookId));
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
    }
}
