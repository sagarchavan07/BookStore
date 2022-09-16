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
    public ResponseEntity<ResponseDTO> addToCart(@RequestHeader(name = "Authorization") String token, @RequestBody CartDTO cartDTO) {
        ResponseDTO responseDTO = new ResponseDTO("Cart Added Successfully", cartService.addToCart(token, cartDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{cartId}")
    public ResponseEntity<ResponseDTO> updateCart(@RequestHeader(name = "Authorization") String token, @RequestBody CartDTO cartDTO, @PathVariable long cartId) {
        ResponseDTO responseDTO = new ResponseDTO("Cart Updated Successfully", cartService.UpdateCart(token, cartDTO, cartId));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping(value = {"/getall", "/"})
    public ResponseEntity<ResponseDTO> getAllCarts(@RequestHeader(name = "Authorization") String token) {
        ResponseDTO responseDTO = new ResponseDTO("Get call Successful", cartService.getAllCarts(token));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ResponseDTO> getCartById(@RequestHeader(name = "Authorization") String token, @PathVariable Long cartId) {
        ResponseDTO responseDTO = new ResponseDTO("Get call Successful", cartService.getCartById(token, cartId));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<ResponseDTO> deleteCartById(@RequestHeader(name = "Authorization") String token, @PathVariable Long cartId) {
        ResponseDTO responseDTO = new ResponseDTO("Cart Deleted Successfully", cartService.deleteCartById(token, cartId));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
