package com.bl.bookstore.controller;

import com.bl.bookstore.dto.BookDTO;
import com.bl.bookstore.dto.ResponseDTO;
import com.bl.bookstore.service.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookstore/")
public class BookController {

    @Autowired
    BookStoreService bookStoreService;

    @RequestMapping(value = {"", "/"})
    public ResponseEntity<ResponseDTO> hello(){
        return bookStoreService.welcomeMessage();
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> insertBook(@RequestBody BookDTO bookDTO){
        return bookStoreService.insertBook(bookDTO);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ResponseDTO> getAllBooks(){
        return bookStoreService.getAllBooks();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO> getBookById(@PathVariable long id){
        return bookStoreService.getBookById(id);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseDTO> searchBookByName(@RequestParam String name){
        return bookStoreService.getBookByName(name);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> deleteBookById(@PathVariable long id){
        return bookStoreService.deleteBookById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateBookById(@PathVariable long id, @RequestBody BookDTO bookDTO){
        return bookStoreService.updateBookById(id, bookDTO);
    }

    @PutMapping("/updatequantity")
    public ResponseEntity<ResponseDTO> updateQuantity(@RequestParam long id, long quantity){
        return bookStoreService.updateQuantity(id, quantity);
    }
}
