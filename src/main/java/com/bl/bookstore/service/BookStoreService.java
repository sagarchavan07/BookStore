package com.bl.bookstore.service;

import com.bl.bookstore.dto.BookDTO;
import com.bl.bookstore.dto.ResponseDTO;
import com.bl.bookstore.exception.BookStoreException;
import com.bl.bookstore.model.Book;
import com.bl.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookStoreService {

    @Autowired
    BookRepository bookRepository;

    public ResponseEntity<ResponseDTO> welcomeMessage() {
        ResponseDTO responseDTO = new ResponseDTO("GET call success", "Welcome to Book Store Application");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    public ResponseEntity<ResponseDTO> insertBook(BookDTO bookDTO) {
        ResponseDTO responseDTO = new ResponseDTO("Inserted new Book", bookRepository.save(new Book(bookDTO)));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseDTO> getAllBooks() {
        if (!bookRepository.findAll().isEmpty()) {
            ResponseDTO responseDTO = new ResponseDTO("GET call Success", bookRepository.findAll());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else
            throw new BookStoreException("Books Table is Empty!");
    }

    public ResponseEntity<ResponseDTO> getBookById(Long id) {
        if (bookRepository.existsById(id)) {
            ResponseDTO responseDTO = new ResponseDTO("GET call Success", bookRepository.findById(id));
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else
            throw new BookStoreException("Books id " + id + " not found!");
    }

    public ResponseEntity<ResponseDTO> deleteBookById(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            ResponseDTO responseDTO = new ResponseDTO("Book deleted", "Book id: " + id);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else
            throw new BookStoreException("Books id " + id + " not found!");
    }

    public ResponseEntity<ResponseDTO> updateBookById(Long id, BookDTO bookDTO) {
        if (bookRepository.existsById(id)) {
            Book book = new Book(bookDTO);
            book.setBookId(id);
            ResponseDTO responseDTO = new ResponseDTO("Book Updated", bookRepository.save(book));
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else
            throw new BookStoreException("Books id " + id + " not found!");
    }

    public ResponseEntity<ResponseDTO> getBookByName(String name) {
        if (!bookRepository.findBookByName(name).isEmpty()){
            ResponseDTO responseDTO = new ResponseDTO("GET call Success", bookRepository.findBookByName(name));
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else
            throw new BookStoreException("Books name " + name + " not found!");
    }

    public ResponseEntity<ResponseDTO> updateQuantity(Long id, long quantity) {
        if (bookRepository.existsById(id)) {
            Book book = bookRepository.findBookById(id);
            book.setQuantity(quantity);
            ResponseDTO responseDTO = new ResponseDTO("Book quantity updated", bookRepository.save(book));
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else
            throw new BookStoreException("Books id " + id + " not found!");
    }
}
