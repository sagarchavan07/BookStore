package com.bl.bookstore.service;

import com.bl.bookstore.dto.BookDTO;
import com.bl.bookstore.model.Book;

import java.util.List;

public interface IBookService {
    Book insertBook(String token, BookDTO bookDTO);
    List<Book> getAllBooks();
    Book getBookById(String token, Long bookId);
    String deleteBookById(String token, Long bookId);
    Book updateBookById(String token, Long bookId, BookDTO bookDTO);
    List<Book> getBookByName(String token, String name);
    Book updateQuantity(String token, long bookId, long quantity);
    List<Book> sortBooksAscending();
    List<Book> sortBooksDescending();
}
