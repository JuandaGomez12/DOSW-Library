package edu.eci.dosw.DOSW_Library.controller.mapper;

import edu.eci.dosw.DOSW_Library.controller.dto.BookDTO;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toModel(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setId(dto.getId());
        return book;
    }

    public BookDTO toDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setId(book.getId());
        dto.setAvailable(book.getAvailableCopies() > 0);
        dto.setCopies(book.getAvailableCopies());
        return dto;
    }
}
