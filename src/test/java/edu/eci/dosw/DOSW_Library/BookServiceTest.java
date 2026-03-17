package edu.eci.dosw.DOSW_Library;

import edu.eci.dosw.DOSW_Library.core.exception.BookNotAvailableException;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.service.BookService;
import edu.eci.dosw.DOSW_Library.core.validator.BookValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService(new BookValidator());
    }

    private Book buildBook(String id) {
        Book book = new Book();
        book.setTitle("Titulo");
        book.setAuthor("Autor");
        book.setId(id);
        book.setAvailable(true);
        return book;
    }

    @Test
    void addBook_success() {
        Book book = buildBook("B001");
        Book result = bookService.addBook(book, 3);
        assertEquals("B001", result.getId());
        assertEquals(3, bookService.getCopies("B001"));
    }

    @Test
    void addBook_missingTitle_throwsException() {
        Book book = new Book();
        book.setAuthor("Autor");
        book.setId("B002");
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook(book, 1));
    }

    @Test
    void getAllBooks_returnsAllAdded() {
        bookService.addBook(buildBook("B001"), 1);
        bookService.addBook(buildBook("B002"), 2);
        assertEquals(2, bookService.getAllBooks().size());
    }

    @Test
    void getBookById_success() {
        bookService.addBook(buildBook("B001"), 1);
        Book found = bookService.getBookById("B001");
        assertEquals("B001", found.getId());
    }

    @Test
    void getBookById_notFound_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> bookService.getBookById("NOEXISTE"));
    }

    @Test
    void updateAvailability_success() {
        bookService.addBook(buildBook("B001"), 1);
        Book updated = bookService.updateAvailability("B001", false);
        assertFalse(updated.isAvailable());
    }

    @Test
    void checkAvailability_notAvailable_throwsException() {
        Book book = buildBook("B001");
        book.setAvailable(false);
        bookService.addBook(book, 1);
        assertThrows(BookNotAvailableException.class, () -> bookService.checkAvailability("B001"));
    }

    @Test
    void checkAvailability_available_doesNotThrow() {
        bookService.addBook(buildBook("B001"), 1);
        assertDoesNotThrow(() -> bookService.checkAvailability("B001"));
    }
}