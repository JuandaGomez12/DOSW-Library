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
        book.setAvailable(dto.isAvailable());
        return book;
    }

    public BookDTO toDTO(Book book, int copies) {
        BookDTO dto = new BookDTO();
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setId(book.getId());
        dto.setAvailable(book.isAvailable());
        dto.setCopies(copies);
        return dto;
    }
}