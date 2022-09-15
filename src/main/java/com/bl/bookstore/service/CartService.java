package com.bl.bookstore.service;

import com.bl.bookstore.dto.CartDTO;
import com.bl.bookstore.exception.BookStoreException;
import com.bl.bookstore.model.Cart;
import com.bl.bookstore.model.UserData;
import com.bl.bookstore.repository.BookRepository;
import com.bl.bookstore.repository.CartRepository;
import com.bl.bookstore.repository.UserRepository;
import com.bl.bookstore.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    TokenUtility tokenUtility;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;

    public Cart addToCart(String token, CartDTO cartDTO) {
        Cart cart;
        List<Integer> bookIdList = cartDTO.getBookIdList();
        List<Integer> quantityList = cartDTO.getQuantity();
        long userId = tokenUtility.decodeToken(token);
        UserData userData = userRepository.findById(userId).orElseThrow(() -> new BookStoreException("User id " + userId + " not found"));

        for (int i = 0; i < bookIdList.size(); i++) {
            if (quantityList.get(i) > bookRepository.findBookById(bookIdList.get(i)).getQuantity())
                throw new BookStoreException("Book quantity exceeded for book id "+ bookIdList.get(i));
        }
        if (cartRepository.existsById(userId)) {
            cart=cartRepository.findById(userId).orElseThrow(() -> new BookStoreException("User id " + userId + " not found"));
            cart.setUserData(userData);
            cart.setBookIdList(cartDTO.getBookIdList());
            cart.setQuantity(cartDTO.getQuantity());
            return cartRepository.save(cart);
        } else {
            cart = new Cart(userId, userData, cartDTO.getBookIdList(), cartDTO.getQuantity());
            try {

                return cartRepository.save(cart);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

}
