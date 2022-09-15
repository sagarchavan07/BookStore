package com.bl.bookstore.controller;

import com.bl.bookstore.dto.BookDTO;
import com.bl.bookstore.dto.CartDTO;
import com.bl.bookstore.dto.ResponseDTO;
import com.bl.bookstore.service.CartService;
import com.bl.bookstore.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookstore/")
public class BookController {

    @Autowired
    IBookService bookService;

    @RequestMapping(value = {"", "/"})
    public ResponseEntity<ResponseDTO> hello(){
        ResponseDTO responseDTO = new ResponseDTO("GET call success", "Welcome to Book Store Application");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> insertBook(@RequestHeader(name = "Authorization") String token, @RequestBody BookDTO bookDTO){
        ResponseDTO responseDTO = new ResponseDTO("Inserted new Book", bookService.insertBook(token, bookDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ResponseDTO> getAllBooks(){
        ResponseDTO responseDTO = new ResponseDTO("GET call Success", bookService.getAllBooks());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/get/asce")
    public ResponseEntity<ResponseDTO> sortBooksAscending(){
        ResponseDTO responseDTO = new ResponseDTO("GET call Success", bookService.sortBooksAscending());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get/desc")
    public ResponseEntity<ResponseDTO> sortBooksDescending(){
        ResponseDTO responseDTO = new ResponseDTO("GET call Success", bookService.sortBooksDescending());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getBookById(@RequestHeader(name = "Authorization") String token, @PathVariable long id){
        ResponseDTO responseDTO = new ResponseDTO("GET call Success", bookService.getBookById(token, id));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseDTO> searchBookByName(@RequestHeader(name = "Authorization") String token, @RequestParam String bookName){
        ResponseDTO responseDTO = new ResponseDTO("GET call Success", bookService.getBookByName(token, bookName));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteBookById(@RequestHeader(name = "Authorization") String token, @PathVariable long id){
        ResponseDTO responseDTO = new ResponseDTO("Book deleted", bookService.deleteBookById(token, id));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateBookById(@RequestHeader(name = "Authorization") String token, @PathVariable long id, @RequestBody BookDTO bookDTO){
        ResponseDTO responseDTO = new ResponseDTO("Book Updated", bookService.updateBookById(token, id, bookDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/updatequantity")
    public ResponseEntity<ResponseDTO> updateQuantity(@RequestHeader(name = "Authorization") String token, @RequestParam long id, long quantity){
        ResponseDTO responseDTO = new ResponseDTO("Book quantity updated", bookService.updateQuantity(token, id, quantity));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
