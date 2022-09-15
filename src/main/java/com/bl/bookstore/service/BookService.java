package com.bl.bookstore.service;

import com.bl.bookstore.dto.BookDTO;
import com.bl.bookstore.email.EmailService;
import com.bl.bookstore.exception.BookStoreException;
import com.bl.bookstore.model.Book;
import com.bl.bookstore.model.UserData;
import com.bl.bookstore.repository.BookRepository;
import com.bl.bookstore.repository.UserRepository;
import com.bl.bookstore.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService{

    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenUtility tokenUtility;
    @Autowired
    EmailService emailService;

    public Book insertBook(String token, BookDTO bookDTO) {
        long userId = tokenUtility.decodeToken(token);
        UserData user = userRepository.findById(userId).orElseThrow(() -> new BookStoreException("tokens do not match the user"));
        if (user.isAdmin()) {
            Book book =  bookRepository.save(new Book(bookDTO));
            emailService.sendEmail(user.getEmail(), "new book added", "new book if id "+ book.getBookId()+" is added to book Store by "+ user.getFirstName()+" "+user.getLastName() +". \nBook Details:\n"+book.toJson());
            return book;
        } else throw new BookStoreException("User is not an Admin");
    }

    public List<Book> getAllBooks() {
        if (!bookRepository.findAll().isEmpty()) {
            return bookRepository.findAll();
        } else throw new BookStoreException("Books Table is Empty!");
    }

    public List<Book> sortBooksAscending() {
        if (!bookRepository.findAll().isEmpty()) {
            return bookRepository.sortBooksAscending();
        } else throw new BookStoreException("Books Table is Empty!");
    }
    public List<Book> sortBooksDescending() {
        if (!bookRepository.findAll().isEmpty()) {
            return bookRepository.sortBooksDescending();
        } else throw new BookStoreException("Books Table is Empty!");
    }

    public Book getBookById(String token, Long bookId) {
        long userId = tokenUtility.decodeToken(token);
        userRepository.findById(userId).orElseThrow(() -> new BookStoreException("tokens do not match the user"));
        return bookRepository.findById(bookId).orElseThrow(() -> new BookStoreException("Books id " + bookId + " not found!"));
    }

    public String deleteBookById(String token, Long bookId) {
        long userId = tokenUtility.decodeToken(token);
        UserData user = userRepository.findById(userId).orElseThrow(() -> new BookStoreException("tokens do not match the user"));
        bookRepository.findById(bookId).orElseThrow(() -> new BookStoreException("Books id " + bookId + " not found!"));
        if (user.isAdmin()) {
            bookRepository.deleteById(bookId);
            return "Book id: " + bookId;
        } else throw new BookStoreException("User is not Admin");
    }

    public Book updateBookById(String token, Long bookId, BookDTO bookDTO) {
        long userId = tokenUtility.decodeToken(token);
        userRepository.findById(userId).orElseThrow(() -> new BookStoreException("tokens do not match the user"));
        bookRepository.findById(bookId).orElseThrow(() -> new BookStoreException("Books id " + bookId + " not found!"));
        Book book = new Book(bookDTO);
        book.setBookId(bookId);
        return bookRepository.save(book);
    }

    public List<Book> getBookByName(String token, String name) {
        long userId = tokenUtility.decodeToken(token);
        userRepository.findById(userId).orElseThrow(() -> new BookStoreException("tokens do not match the user"));
        if (!bookRepository.findBookByName(name).isEmpty()) {
            return bookRepository.findBookByName(name);
        } else throw new BookStoreException("Books name " + name + " not found!");
    }

    public Book updateQuantity(String token, long bookId, long quantity) {
        long userId = tokenUtility.decodeToken(token);
        userRepository.findById(userId).orElseThrow(() -> new BookStoreException("tokens do not match to the user"));
        bookRepository.findById(bookId).orElseThrow(() -> new BookStoreException("Books id " + bookId + " not found!"));
        Book book = bookRepository.findBookById(bookId);
        book.setQuantity(quantity);
        return bookRepository.save(book);
    }
}
