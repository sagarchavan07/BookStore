package com.bl.bookstore.service;

import com.bl.bookstore.dto.CartDTO;
import com.bl.bookstore.exception.BookStoreException;
import com.bl.bookstore.model.Book;
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
        List<Long> bookIdList = cartDTO.getBookIdList();
        List<Long> quantityList = cartDTO.getQuantity();
        long userId = tokenUtility.decodeToken(token);
        UserData userData = userRepository.findById(userId).orElseThrow(() -> new BookStoreException("User id " + userId + " not found"));
        double totalCartPrice = 0;
        for (int i = 0; i < bookIdList.size(); i++) {
            Book book = bookRepository.findBookById(bookIdList.get(i));
            if (quantityList.get(i) > book.getQuantity())
                throw new BookStoreException("Book quantity exceeded for book id " + bookIdList.get(i));
            else {
                totalCartPrice +=book.getPrice() * quantityList.get(i);
            }
        }
        if (cartRepository.existsById(userId)) {
            cart = cartRepository.findById(userId).orElseThrow(() -> new BookStoreException("User id " + userId + " not found"));
            cart.setUserData(userData);
            cart.setBookIdList(cartDTO.getBookIdList());
            cart.setQuantities(cartDTO.getQuantity());
            cart.setTotalCartPrice(totalCartPrice);
            return cartRepository.save(cart);
        } else {
            cart = new Cart(userId, userData, cartDTO.getBookIdList(), cartDTO.getQuantity(),totalCartPrice);
            try {
                return cartRepository.save(cart);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Cart> getAllCarts(String token) {
        long userId = tokenUtility.decodeToken(token);
        UserData user = userRepository.findById(userId).orElseThrow(() -> new BookStoreException("User id " + userId + " not found"));
        if (!user.isAdmin()) throw new BookStoreException("User is not Admin");
        if (cartRepository.findAll().isEmpty()) throw new BookStoreException("No cart added yet");
        return cartRepository.findAll();
    }

    public Cart getCartById(String token, long id) {
        long userId = tokenUtility.decodeToken(token);
        if (userId != id) throw new BookStoreException("Token is not matching with user id " + id);
        UserData user = userRepository.findById(userId).orElseThrow(() -> new BookStoreException("User id " + userId + " not found"));
        if (!user.isAdmin()) throw new BookStoreException("User is not Admin");
        return cartRepository.findById(id).orElseThrow(() -> new BookStoreException("Cart og id " + userId + " not found"));
    }

    public String deleteCartById(String token, long id) {
        long userId = tokenUtility.decodeToken(token);
        if (userId != id) throw new BookStoreException("Token is not matching with user id " + id);
        if (cartRepository.existsById(id)) {
            cartRepository.deleteById(id);
        } else throw new BookStoreException("cart not found of id " + id);
        return "Cart deleted of id " + id;
    }

    public Cart UpdateCart(String token, CartDTO cartDTO, long cartId) {
        long userId = tokenUtility.decodeToken(token);
        if (userId == cartId) {
            return addToCart(token, cartDTO);
        } else throw new BookStoreException("cartId " + cartId + " not matching with token");
    }
}
