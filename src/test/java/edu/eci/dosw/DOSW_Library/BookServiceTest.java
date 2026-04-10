package edu.eci.dosw.DOSW_Library;

import edu.eci.dosw.DOSW_Library.core.exception.BookNotAvailableException;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.service.BookService;
import edu.eci.dosw.DOSW_Library.core.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("relational")
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    private Book buildBook() {
        Book book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert Martin");
        
        return book;
    }

    @Test
    void addBook_success() {
        Book result = bookService.addBook(buildBook(), 3);
        assertNotNull(result.getId());
        assertEquals(3, bookService.getCopies(result.getId()));
    }

    @Test
    void addBook_missingTitle_throwsException() {
        Book book = new Book();
        book.setAuthor("Autor");
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook(book, 1));
    }

    @Test
    void getAllBooks_returnsAllAdded() {
        bookService.addBook(buildBook(), 1);
        bookService.addBook(buildBook(), 2);
        assertEquals(2, bookService.getAllBooks().size());
    }

    @Test
    void getBookById_success() {
        Book saved = bookService.addBook(buildBook(), 1);
        Book found = bookService.getBookById(saved.getId());
        assertEquals(saved.getId(), found.getId());
    }

    @Test
    void getBookById_notFound_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> bookService.getBookById("NOEXISTE"));
    }

    @Test
    void updateBook_success() {
        Book saved = bookService.addBook(buildBook(), 1);
        Book updated = new Book();
        updated.setTitle("Nuevo Titulo");
        Book result = bookService.updateBook(saved.getId(), updated);
        assertEquals("Nuevo Titulo", result.getTitle());
    }

    @Test
    void deleteBook_success() {
        Book saved = bookService.addBook(buildBook(), 1);
        bookService.deleteBook(saved.getId());
        assertThrows(IllegalArgumentException.class, () -> bookService.getBookById(saved.getId()));
    }

    @Test
    void checkAvailability_notAvailable_throwsException() {
        Book saved = bookService.addBook(buildBook(), 0);
        assertThrows(BookNotAvailableException.class, () -> bookService.checkAvailability(saved.getId()));
    }
}