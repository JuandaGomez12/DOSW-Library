package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.BookNotAvailableException;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.validator.BookValidator;
import edu.eci.dosw.DOSW_Library.persistence.entity.BookEntity;
import edu.eci.dosw.DOSW_Library.persistence.mapper.BookPersistenceMapper;
import edu.eci.dosw.DOSW_Library.persistence.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookPersistenceMapper bookMapper;
    private final BookValidator bookValidator;

    public BookService(BookRepository bookRepository, BookPersistenceMapper bookMapper, BookValidator bookValidator) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.bookValidator = bookValidator;
    }

    public Book addBook(Book book, int totalCopies) {
        bookValidator.validate(book);
        BookEntity entity = bookMapper.toEntity(book);
        entity.setTotalCopies(totalCopies);
        entity.setAvailableCopies(totalCopies);
        return bookMapper.toModel(bookRepository.save(entity));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toModel)
                .collect(Collectors.toList());
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id)
                .map(bookMapper::toModel)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + id));
    }

    public Book updateBook(String id, Book updated) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + id));
        if (updated.getTitle() != null && !updated.getTitle().isBlank())
            entity.setTitle(updated.getTitle());
        if (updated.getAuthor() != null && !updated.getAuthor().isBlank())
            entity.setAuthor(updated.getAuthor());
        return bookMapper.toModel(bookRepository.save(entity));
    }

    public Book updateAvailability(String id, boolean available) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + id));
        entity.setAvailableCopies(available ? entity.getTotalCopies() : 0);
        return bookMapper.toModel(bookRepository.save(entity));
    }

    public void deleteBook(String id) {
        if (!bookRepository.existsById(id))
            throw new IllegalArgumentException("Libro no encontrado con ID: " + id);
        bookRepository.deleteById(id);
    }

    public int getCopies(String id) {
        return bookRepository.findById(id)
                .map(BookEntity::getAvailableCopies)
                .orElse(0);
    }

    public void checkAvailability(String bookId) {
        BookEntity entity = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + bookId));
        if (entity.getAvailableCopies() <= 0)
            throw new BookNotAvailableException(bookId);
    }

    public void decreaseCopies(String bookId) {
        BookEntity entity = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + bookId));
        entity.setAvailableCopies(entity.getAvailableCopies() - 1);
        bookRepository.save(entity);
    }

    public void increaseCopies(String bookId) {
        BookEntity entity = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con ID: " + bookId));
        entity.setAvailableCopies(entity.getAvailableCopies() + 1);
        bookRepository.save(entity);
    }
}