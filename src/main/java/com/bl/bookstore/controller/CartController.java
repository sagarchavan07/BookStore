package com.bl.bookstore.controller;

import com.bl.bookstore.dto.CartDTO;
import com.bl.bookstore.dto.ResponseDTO;
import com.bl.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookstore/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping("/addtocart")
    public ResponseEntity<ResponseDTO> addToCart(@RequestHeader(name = "Authorization") String token, @RequestBody CartDTO cartDTO){
        ResponseDTO responseDTO = new ResponseDTO("Cart Added Successfully", cartService.addToCart(token, cartDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
